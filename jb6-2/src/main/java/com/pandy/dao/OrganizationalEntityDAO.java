/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.pandy.dao;

import org.kie.api.task.model.Group;
import org.kie.api.task.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OrganizationalEntityDAO {
    public List<User> findAllOrgEntUser();
    public List<Group> findAllOrgEntGroup();
    public List<com.pandy.model.User> findAllUser();
}
