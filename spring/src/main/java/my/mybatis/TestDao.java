package my.mybatis;

import my.spring.bean.Test;

public interface TestDao {

	Test selectByPrimaryKey(int i);

}
