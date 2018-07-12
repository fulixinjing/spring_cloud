package my.mybatis;

import my.spring.annotation.Mapper;
import my.spring.bean.Test;

@Mapper
public interface TestDao {

	Test selectByPrimaryKey(int i);

}
