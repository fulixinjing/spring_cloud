package cn.taskSys.common.cache;

/**
 * 缓存默认常量
 */
public interface DefaultCachedConstants {

	public static String DEFAULT_CACHE_TYPE = "DEFAULT";// DefaultCached|SpyMemcached
	
	public static final int DEFAULT_CACHE_TIMEOUT=30*60000;//30分钟超时

	public static String DEFAULT_CACHE_KEY_TYPE = "ONLINE";// DefaultCached|SpyMemcached
}
