package com.pandy.service;

import com.pandy.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Jbpm6Service {
    public List<Map<String, Object>> listUsers();
    public void saveUser(User user);
    //public List<Map<String, Object>> listAll();
    public List<Map<String, Object>> listProcess();
    
    //public void start(String proId);
    public void startProcess(String proId);
    public void startRuleTaskProcess(String proId);

    public void claimTask(String taskId,String userId);
    public void startTask(String taskId,String userId);
    public Map<String, Object> getTaskParamterMapping(String taskId,String userId);
    public void completeTask(String taskId,String userId,Map<String,Object> resultMapping);
    
    public List<Map<String, Object>> listUserAllTask(String userId);
    
    public void evaluation();
}

