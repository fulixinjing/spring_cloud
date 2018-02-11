package com.chj.dao;

import com.chj.model.Login;

public interface LoginDao {

	Login getUser(Login login);

	void addUser(Login login);

}
