package cn.taskSys.common.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 默认缓存
 */
public class DefaultCached {
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	private static Map<String, Object> defaultMap = new ConcurrentHashMap<String, Object>();
	public static void init() throws IOException {
			defaultMap = new ConcurrentHashMap<String, Object>();
	}

	/**
	 * 添加缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Boolean add(String key, Object value) {
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
	public static Object get(String key) {
		if (key == null) {
			return null;
		}
		return defaultMap.get(key);
	}
	/**
	 * 移除缓存
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
	/**
	 * 获取缓存的大小
	 * @return
	 */
	public static int size(){
		return defaultMap.size();
	}
	/**
	 * 获取默认缓存的键值
	 * @return
	 */
	public static Set<String> keys() {
		return defaultMap.keySet();
	}
	/**
	 * 获取默认缓存的键名
	 * 
	 * @return
	 */
	public static Collection<Object> values() {
		return defaultMap.values();
	}
}
