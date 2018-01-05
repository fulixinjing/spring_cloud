package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Grades;
import cn.taskSys.entity.UserScore;

public interface GradesDao {
	
//	public void saveScore(Grades grades)throws Exception;
	
	public void saveScore(List<Grades> gradesList)throws Exception;
	
	public List<Grades> getScoreByPro(Map<String, Object> map) throws Exception;
	
	public List<UserScore> getScoreList(UserScore userScore) throws Exception;//分页列表
	
	public int getScoreListCount(UserScore userScore) throws Exception;
	
	public List<UserScore> getScoreList2(UserScore userScore) throws Exception;//导出列表
	
	void delScore(UserScore userScore) throws Exception;
}
