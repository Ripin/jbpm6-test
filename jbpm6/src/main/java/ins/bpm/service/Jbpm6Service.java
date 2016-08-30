package ins.bpm.service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Jbpm6Service {

    void startProcess(String processId);
    void claimTask(String taskId, String userId);
    void startTask(String taskId, String userId);
    Map<String, Object> getTaskParamterMapping(String taskId, String userId);
    void completeTask(String taskId, String userId, Map<String, Object> resultMapping);

}

