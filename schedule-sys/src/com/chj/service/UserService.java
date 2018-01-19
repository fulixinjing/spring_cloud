package com.chj.service;

import com.chj.model.Login;
import com.chj.model.PersonSys;

public interface UserService {


	void updateUser(Login user);

	PersonSys getMonthCount(PersonSys personSys);

}
