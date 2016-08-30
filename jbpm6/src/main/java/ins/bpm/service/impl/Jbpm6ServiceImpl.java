package ins.bpm.service.impl;


import ins.bpm.service.Jbpm6Service;
import org.jbpm.services.task.wih.LocalHTWorkItemHandler;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.task.api.InternalTaskService;
import org.kie.internal.task.api.TaskContentService;
import org.kie.internal.task.api.TaskQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: yanggengxiang Date: 3/24/14 Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "jbpm6Service")
public class Jbpm6ServiceImpl implements Jbpm6Service {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private RuntimeManager runtimeManager;
    private InternalTaskService taskService;
    private TaskQueryService taskQueryService;
    private TaskContentService taskContentService;

    /*@Override
    public void start(String proId) {
        //这个是使用xml定义的ksession和kbase
        LocalHTWorkItemHandler humanTaskHandler = new LocalHTWorkItemHandler();
        humanTaskHandler.setRuntimeManager(runtimeManager);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                humanTaskHandler);
        ksession.startProcess(proId);
        ksession.fireAllRules();
    }*/
    @Override
    public void startProcess(String processId) {
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());

        KieSession ksession1 = engine.getKieSession();

        LocalHTWorkItemHandler humanTaskHandler = new LocalHTWorkItemHandler();
        humanTaskHandler.setRuntimeManager(runtimeManager);
        ksession1.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);

        //把传值给工作流里面的变量
        Map<String, Object> inputParams = new HashMap<String, Object>();
        /*inputParams.put("var1", "This is a test message.");
        inputParams.put("name", "krisv");
        inputParams.put("age", "35");
        inputParams.put("content", "Yearly performance evaluation");
        inputParams.put("message", "This is a test message.");*/
        ProcessInstance processInstance = ksession1.startProcess(processId, inputParams);

        System.out.println("流程id：" + processInstance.getId());
        ksession1.fireAllRules();
        //ksession1.dispose();

    }

    @Override
    public void claimTask(String taskId, String userId) {
        taskService.claim(Long.parseLong(taskId), userId);
        System.out.println("---------------------------------------------------claimTask");

    }

    @Override
    public void startTask(String taskId, String userId) {
        taskService.start(Long.parseLong(taskId), userId);
    }

    @Override
    public Map<String, Object> getTaskParamterMapping(String taskId, String userId) {
        Map<String, Object> content = taskService.getTaskContent(Long.parseLong(taskId));
        System.out.println("---------------------------------------------------startTask");
        return content;
    }

    @Override
    public void completeTask(String taskId, String userId, Map<String, Object> resultMapping) {
        System.out.println("---------------------------------------------------start completeTask");
        //把传值给工作流里面的变量
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("output_name", "Kylin");
        results.put("output_age", 29);
        results.put("output_content", "performance evaluation finish");
        taskService.complete(Long.parseLong(taskId), userId, resultMapping);
        System.out.println("---------------------------------------------------end completeTask");
    }



    public InternalTaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(InternalTaskService taskService) {
        this.taskService = taskService;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(
            EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public RuntimeManager getRuntimeManager() {
        return runtimeManager;
    }

    public void setRuntimeManager(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
    }


    public TaskQueryService getTaskQueryService() {
        return taskQueryService;
    }

    public void setTaskQueryService(TaskQueryService taskQueryService) {
        this.taskQueryService = taskQueryService;
    }

    public TaskContentService getTaskContentService() {
        return taskContentService;
    }

    public void setTaskContentService(TaskContentService taskContentService) {
        this.taskContentService = taskContentService;
    }
}
