package com.pandy.dao.impl;

import javax.persistence.EntityManagerFactory;

import com.pandy.dao.BaseDAO;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseDAOImpl implements BaseDAO {
	@Autowired
    protected EntityManagerFactory entityManagerFactory;

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
}
