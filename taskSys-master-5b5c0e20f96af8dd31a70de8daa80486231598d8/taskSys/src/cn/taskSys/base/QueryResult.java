package cn.taskSys.base;

import java.util.List;
import java.util.Map;

public class QueryResult<T> {
	private List<T> resultlist;
	private List<Map<String, Object>> resultMap;
	public List<Map<String, Object>> getResultMap() {
		return resultMap;
	}
	public void setResultMap(List<Map<String, Object>> resultMap) {
		this.resultMap = resultMap;
	}

	private long totalrecord;
	
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> list) {
		this.resultlist = list;
	}
	
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
	
	public static <T> QueryResult<T> getQueryResult(long totalrecord,List<T> resultlist){
		QueryResult<T> qr = new QueryResult<T>();
		qr.setTotalrecord(totalrecord);
		qr.setResultlist(resultlist);
		return qr;
	}
	
	
}
