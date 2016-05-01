package cui.litang.cuimarket.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Intent;
import android.net.Uri;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.bean.DownloadInfo;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.http.HttpHelper.HttpResult;
import cui.litang.cuimarket.utils.IOUtils;
import cui.litang.cuimarket.utils.LogUtils;
import cui.litang.cuimarket.utils.UIUtils;


public class DownloadManager {
	// 默认的状态
	public static final int STATE_NONE = 0;
	// 等待
	public static final int STATE_WAITING = 1;
	// 下载中
	public static final int STATE_DLOWNLOADING = 2;
	// 暂停
	public static final int STATE_PAUSED = 3;
	// 下载完成
	public static final int STATE_DOWNLOADED = 4;
	// 下载失败
	public static final int STATE_ERROR = 5;

	public static DownloadManager mInstance;

	public static DownloadManager getInstance() {
		if (mInstance == null) {
			mInstance = new DownloadManager();
		}
		return mInstance;
	}

	// 存放下载的基本信息
	private static Map<Long, DownloadInfo> mDownloadInfoMap = new ConcurrentHashMap<Long, DownloadInfo>();
	// 存放下载任务
	private static Map<Long, DownloadTask> mDownloadTaskMap = new ConcurrentHashMap<Long, DownloadTask>();
	// 存放下载观察者的集合
	private static List<DownloadObserver> mDownloadObserverLists = new ArrayList<DownloadObserver>();

	/**
	 * 
	 * 下载
	 * 
	 * @param appInfo
	 */
	public synchronized void download(AppInfo appInfo) {
		// 先判断是否有这个app的下载信息
		DownloadInfo info = mDownloadInfoMap.get(appInfo.getId());
		if (info == null) {// 如果没有，则根据appInfo创建一个新的下载信息
			info = DownloadInfo.clone(appInfo);
			mDownloadInfoMap.put(appInfo.getId(), info);
		}
		// 判断状态是否为STATE_NONE、STATE_PAUSED、STATE_ERROR。只有这3种状态才能进行下载，其他状态不予处理
		if (info.getDownloadState() == STATE_NONE
				|| info.getDownloadState() == STATE_PAUSED
				|| info.getDownloadState() == STATE_ERROR) {
			// 下载之前，把状态设置为STATE_WAITING，因为此时并没有产开始下载，只是把任务放入了线程池中，当任务真正开始执行时，才会改为STATE_DOWNLOADING
			info.setDownloadState(STATE_WAITING);
			notifyDownloadStateChanged(info);// 每次状态发生改变，都需要回调该方法通知所有观察者
			DownloadTask task = new DownloadTask(info);// 创建一个下载任务，放入线程池
			mDownloadTaskMap.put(info.getId(), task);
			ThreadManager.getDownloadPool().execute(task);
		}
		
	}

	/**
	 * 注册一个下载状态
	 */
	public void registerDownloadStateChanged(DownloadObserver observer) {
		synchronized (mDownloadObserverLists) {
			if (!mDownloadObserverLists.contains(observer)) {
				mDownloadObserverLists.add(observer);
			}
		}
	}

	/**
	 * 反注册
	 */
	public void unRegisterDownloadStateChanged(DownloadObserver observer) {
		synchronized (mDownloadObserverLists) {
			if (mDownloadObserverLists.contains(observer)) {
				mDownloadObserverLists.remove(observer);
			}
		}
	}

	/**
	 * 下载的观察者
	 */
	public interface DownloadObserver {
		// 注册一个观察者
		public void onRegisterDownloadStateChanged(DownloadInfo info);

		// 反注册一个观察
		public void onRegisterDownloadProgressed(DownloadInfo info);
	}

	/**
	 * 刷新状态
	 * 
	 * @param info
	 */
	public void notifyDownloadStateChanged(DownloadInfo info) {
		synchronized (mDownloadObserverLists) {
			for (DownloadObserver observer : mDownloadObserverLists) {
				observer.onRegisterDownloadStateChanged(info);
			}
		}
	}

	/**
	 * 刷新进度
	 * 
	 * @param info
	 */
	public void notifyDownloadProgressed(DownloadInfo info) {
		synchronized (mDownloadObserverLists) {
			for (DownloadObserver observer : mDownloadObserverLists) {
				observer.onRegisterDownloadProgressed(info);
			}
		}
	}

	class DownloadTask implements Runnable {

		private DownloadInfo info;

		public DownloadTask(DownloadInfo info) {
			this.info = info;
		}

		// 正常下载的url地址
		// url地址: http://127.0.0.1:8090/download?name=downloadUrl
		// 断点下载的url地址
		// url地址: http://127.0.0.1:8090/download?name=downloadUrl&range=672121
		@Override
		public void run() {
			// 设置当前的状态
			info.setDownloadState(STATE_DLOWNLOADING);// 先改变下载状态
			notifyDownloadStateChanged(info);
			File file = new File(info.getPath());// 获取下载文件
			HttpResult httpResult = null;
			InputStream stream = null;
			if (info.getCurrentSize() == 0 || !file.exists()
					|| file.length() != info.getCurrentSize()) {
				// 如果文件不存在，或者进度为0，或者进度和文件长度不相符，就需要重新下载
				info.setCurrentSize(0);
				file.delete();
				httpResult = HttpHelper.download(HttpHelper.URL
						+ "download?name=" + info.getUrl());
			} else {
				// 文件存在且长度和进度相等，采用断点下载
				httpResult = HttpHelper.download(HttpHelper.URL
						+ "download?name=" + info.getUrl() + "&range="
						+ info.getCurrentSize());
			}
			if (httpResult == null
					|| (stream = httpResult.getInputStream()) == null) {
				info.setDownloadState(STATE_ERROR);// 没有下载内容返回，修改为错误状态
				notifyDownloadStateChanged(info);
			} else {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file, true);
					int len = -1;
					byte[] buffer = new byte[1024];
					while (((len = stream.read(buffer)) != -1)
							&& info.getDownloadState() == STATE_DLOWNLOADING) {
						// 每次读取到数据后，都需要判断是否为下载状态，如果不是，下载需要终止，如果是，则刷新进度
						fos.write(buffer, 0, len);
						fos.flush();
						info.setCurrentSize(info.getCurrentSize() + len);
						notifyDownloadProgressed(info);// 刷新进度
					}
				} catch (Exception e) {
					LogUtils.e(e);// 出异常后需要修改状态并删除文件
					info.setDownloadState(STATE_ERROR);
					notifyDownloadStateChanged(info);
					info.setCurrentSize(0);
					file.delete();
				} finally {
					IOUtils.close(fos);
					if (httpResult != null) {
						httpResult.close();
					}
				}
				// 判断进度是否和app总长度相等
				if (info.getCurrentSize() == info.getAppSize()) {
					info.setDownloadState(STATE_DOWNLOADED);
					notifyDownloadStateChanged(info);
				} else if (info.getDownloadState() == STATE_PAUSED) {// 判断状态
					notifyDownloadStateChanged(info);
				} else {
					info.setDownloadState(STATE_ERROR);
					notifyDownloadStateChanged(info);
					info.setCurrentSize(0);// 错误状态需要删除文件
					file.delete();
				}

			}
			mDownloadTaskMap.remove(info.getId());

		}
	}

	/**
	 * 暂停下载
	 * 
	 * @param appInfo
	 */
	public void stop(AppInfo appInfo) {
		stopDownload(appInfo);
		// 获取到缓存的下载信息
		DownloadInfo downloadInfo = mDownloadInfoMap.get(appInfo.getId());
		// 如果下载信息不为null
		if (null != downloadInfo) {
			downloadInfo.setDownloadState(STATE_PAUSED);
			notifyDownloadStateChanged(downloadInfo);
		}
	}

	/**
	 * 停止下载
	 * 
	 * @param appInfo
	 */
	private void stopDownload(AppInfo appInfo) {
		DownloadTask task = mDownloadTaskMap.remove(appInfo.getId());// 先从集合中找出下载任务
		if (task != null) {
			ThreadManager.getDownloadPool().cancel(task);// 然后从线程池中移除
		}
	}

	/**
	 * 获取到下载信息
	 * 
	 * @param id
	 * @return
	 */
	public DownloadInfo getDownInfo(long id) {
		return mDownloadInfoMap.get(id);
	}

	// <intent-filter>
	// <action android:name="android.intent.action.VIEW" />
	// <category android:name="android.intent.category.DEFAULT" />
	// <data android:scheme="content" />
	// <data android:scheme="file" />
	// <data android:mimeType="application/vnd.android.package-archive" />
	// </intent-filter>
	public void install(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadInfoMap.get(appInfo.getId());// 找出下载信息
		if (info != null) {// 发送安装的意图
			Intent installIntent = new Intent(Intent.ACTION_VIEW);
			installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
					"application/vnd.android.package-archive");
			UIUtils.getContext().startActivity(installIntent);
		}
		notifyDownloadStateChanged(info);

	}
}
