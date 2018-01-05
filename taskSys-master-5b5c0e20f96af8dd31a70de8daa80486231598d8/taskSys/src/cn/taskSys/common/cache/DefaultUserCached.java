/**
 * 平台默认缓存
 */
package cn.taskSys.common.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 */
public class DefaultUserCached {
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	private static Map<String, UserCachedEntity<?>> defaultMap = new ConcurrentHashMap<String, UserCachedEntity<?>>();
	private static String CONST_CACHED_USER = "CONST_CACHED_USER";

	public static void init() throws IOException {
			defaultMap = new ConcurrentHashMap<String, UserCachedEntity<?>>();
	}

	/**
	 * 添加缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Boolean add(String key, UserCachedEntity<?> value) {
		try {
			lock.readLock().lock();
			defaultMap.put(key, value);
		} finally {
			lock.readLock().unlock();
		}
		return true;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public static UserCachedEntity<?> get(String key) {
		if (key == null) {
			return null;
		}
		return getUserMap().get(key);
	}

	public static Map<String, UserCachedEntity<?>> getUserMap() {
			return defaultMap;
	}

	/**
	 * 获取在线用户列表
	 * 
	 * @return
	 */
	public static List<UserCachedEntity<?>> getUserList() {
		List<UserCachedEntity<?>> list = new ArrayList<UserCachedEntity<?>>();
		for (Map.Entry<String, UserCachedEntity<?>> entry : getUserMap()
				.entrySet()) {
			list.add(entry.getValue());
		}
		ComparatorUser comparator = new ComparatorUser();
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * 获取在线用户列表 根据用户的登录名字过滤
	 * 
	 * @return
	 */
	public static List<UserCachedEntity<?>> getUserList(String sLoginName) {
		if (sLoginName == null || sLoginName.length() == 0) {
			return getUserList();
		}
		List<UserCachedEntity<?>> list = new ArrayList<UserCachedEntity<?>>();
		UserCachedEntity<?> item;
		for (Map.Entry<String, UserCachedEntity<?>> entry : getUserMap()
				.entrySet()) {
			item = entry.getValue();
			if (item.getLoginName().indexOf(sLoginName) != -1) {
				list.add(entry.getValue());
			}
		}
		ComparatorUser comparator = new ComparatorUser();
		Collections.sort(list, comparator);
		return list;
	}

	public static boolean isOnline(String vstrLoginUUID) {
		UserCachedEntity<?> user = get(vstrLoginUUID);
		if (user != null) {
			if (user.getLoginHeartbeatDate().compareTo(
					java.util.Calendar.getInstance().getTime()) == 1) {
				return true;
			}
		}
		return false;
	}

	public static List<UserCachedEntity<?>> getUserListOnLine(String sLoginName) {
		List<UserCachedEntity<?>> list = new ArrayList<UserCachedEntity<?>>();
		UserCachedEntity<?> item;
		for (Map.Entry<String, UserCachedEntity<?>> entry : getUserMap()
				.entrySet()) {
			item = entry.getValue();
			if (isOnline(entry.getKey())) {
				if (sLoginName == null || sLoginName.length() == 0) {
					list.add(entry.getValue());
				} else {
					if (item.getLoginName().indexOf(sLoginName) != -1) {
						list.add(entry.getValue());
					}
				}
			}
		}
		ComparatorUser comparator = new ComparatorUser();
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * 移除缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object remove(String key) {
		if (key == null) {
			return null;
		}
		try {
			lock.readLock().lock();
			return defaultMap.remove(key);
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 清空缓存
	 */
	public static void clear() {
			defaultMap.clear();
	}
}

class ComparatorUser implements Comparator<UserCachedEntity<?>> {

	public int compare(UserCachedEntity<?> arg0, UserCachedEntity<?> arg1) {
		// 首先比较年龄，如果年龄相同，则比较名字
		int flag = arg0.getLoginHeartbeatDate().compareTo(
				arg1.getLoginHeartbeatDate());
		if (flag == 0) {
			return arg0.getLoginName().compareTo(arg1.getLoginName());
		} else {
			return flag;
		}
	}

}
