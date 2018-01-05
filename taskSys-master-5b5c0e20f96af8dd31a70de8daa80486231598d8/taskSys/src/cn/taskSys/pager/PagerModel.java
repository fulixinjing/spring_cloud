package cn.taskSys.pager;
import java.util.List;
@SuppressWarnings("unchecked")
public class PagerModel {
	private List datas;
	private int total;
	private int totalHits;

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	
}
