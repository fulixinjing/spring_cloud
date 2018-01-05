package cn.taskSys.common.cache;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CacheMgr {

	public final static int CONST_LOGIN_TYPE_OK = 1;
	public final static int CONST_LOGIN_TYPE_Fail = 0;
	public final static String CONST_KEY_LOGIN_FAIL = "loginFailMap";
	public final static String CONST_KEY_LOGIN_USER = "loginUserMap";


	/**
	 * 添加在线用户
	 * 
	 * @param vobjUserCached
	 */
	public static void addUserOnline(UserCachedEntity<?> vobjUserCached) {
		if (vobjUserCached.getLoginUUID() != null) {
			DefaultUserCached
					.add(vobjUserCached.getLoginUUID(), vobjUserCached);
		}
	}

	/**
	 * 移除在线用户
	 * 
	 * @param vstrLoginUUID
	 */
	public static void removeUserOnline(String vstrLoginUUID) {
		DefaultUserCached.remove(vstrLoginUUID);
	}

	/**
	 * 从缓存中获取在线用户
	 * 
	 * @param vstrLoginUUID
	 * @return
	 */
	public static UserCachedEntity<?> getUserOnline(String vstrLoginUUID) {
		return (UserCachedEntity<?>) DefaultUserCached.get(vstrLoginUUID);
	}

	public static boolean isUserOnline(String vstrLoginUUID) {
		return DefaultUserCached.isOnline(vstrLoginUUID);
	}

	/**
	 * 更新用户心跳时间
	 * 
	 * @param vstrLoginUUID
	 */
	public static void changeUserHeartbeat(String vstrLoginUUID) {
		UserCachedEntity<?> user = getUserOnline(vstrLoginUUID);
		if (user != null) {
			user.setLoginHeartbeatDate(getUserHeartbeat());
			// removeUser(vstrLoginUUID);
			addUserOnline(user);
		}
	}

	/**
	 * 获取用户心跳时间
	 * 
	 * @return
	 */
	public static Date getUserHeartbeat() {
		java.util.Calendar date = java.util.Calendar.getInstance();
		date.setTimeInMillis(System.currentTimeMillis()
				+ DefaultCachedConstants.DEFAULT_CACHE_TIMEOUT);
		return date.getTime();
	}

	/**
	 * 获取在线用户列表
	 * 
	 * @return
	 */
	public static List<UserCachedEntity<?>> getUserListOnLine(String sLoginName) {
		return DefaultUserCached.getUserListOnLine(sLoginName);
	}

	/**
	 * 获取所有在线用户列表
	 * 
	 * @return
	 */
	public static List<UserCachedEntity<?>> getUserListOnLine() {
		return DefaultUserCached.getUserListOnLine(null);
	}

	/**
	 * 所有所有在线用户的个数
	 * 
	 * @return
	 */
	public static int getUserListSizeOnLine() {
		return getUserListOnLine().size();
	}

	/**
	 * 获取用户缓存
	 * 
	 * @return
	 */
	public static Map<String, UserCachedEntity<?>> getUserMap() {
		return DefaultUserCached.getUserMap();
	}
	/**
	 * 移除用户缓存
	 * 
	 * @param vstrLoginUUID
	 */
	public static void removeUsers() {
		remove(CONST_KEY_LOGIN_USER);
	}
	/**
	 * 获取默认缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object value = DefaultCached.get(key);
		return value;
	}

	/**
	 * 添加默认缓存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Object put(String key, Object value) {
		DefaultCached.add(key, value);
		return value;
	}

	/**
	 * 移除默认缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object remove(String key) {
		DefaultCached.remove(key);
		return null;
	}

	/**
	 * 清空默认缓存
	 */
	public static void clear() {
		DefaultCached.clear();
	}

	/**
	 * 获取默认缓存的大小
	 * 
	 * @return
	 */
	public static int size() {
		return DefaultCached.size();
	}

	/**
	 * 获取默认缓存的键值
	 * 
	 * @return
	 */
	public static Set<String> keys() {
		return DefaultCached.keys();
	}

	/**
	 * 获取默认缓存的键名
	 * 
	 * @return
	 */
	public static Collection<Object> values() {
		return DefaultCached.values();
	}
}
