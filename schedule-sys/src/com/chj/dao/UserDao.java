package com.chj.dao;

import com.chj.model.Login;
import com.chj.model.PersonSys;

public interface UserDao {

	void updateUser(Login user);

	PersonSys getMonthCount(PersonSys personSys);

}
