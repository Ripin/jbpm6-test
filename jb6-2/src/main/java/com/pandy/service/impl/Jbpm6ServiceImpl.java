package com.pandy.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pandy.dao.OrganizationalEntityDAO;
import com.pandy.model.User;
import com.pandy.rulemodel.HelloProcessModel;
import com.pandy.service.Jbpm6Service;
import org.jbpm.services.task.wih.LocalHTWorkItemHandler;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.task.api.InternalTaskService;
import org.kie.internal.task.api.TaskContentService;
import org.kie.internal.task.api.TaskQueryService;

import javax.persistence.EntityManagerFactory;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: yanggengxiang Date: 3/24/14 Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
// @Transactional
// @Service(value = "userService")
public class Jbpm6ServiceImpl implements Jbpm6Service {

    private EntityManagerFactory entityManagerFactory;
    private RuntimeManager runtimeManager;


	/*private StatefulKnowledgeSession ksession;
    private KieBase kbase;*/


    //org.jbpm.services.task.impl.command.CommandBasedTaskService
    private InternalTaskService taskService;
    //private BPMN2DataService bpmn2DataService;
    private TaskQueryService taskQueryService;
    private TaskContentService taskContentService;
    private OrganizationalEntityDAO organizationalEntityDAO;

    @Override
    public List<Map<String, Object>> listUsers() {
        List<com.pandy.model.User> list = organizationalEntityDAO.findAllUser();
        List<Map<String, Object>> rtList = Lists.newArrayList();
        if (list != null) {
            for (User user : list) {
                Map<String, Object> userMapping = Maps.newHashMap();
                userMapping.put("userId", user.getId());
                userMapping.put("userJbpmId", user.getUserJbpmId());
                userMapping.put("userName", user.getUserName());
                rtList.add(userMapping);
            }
        }
        return rtList;
    }

    public void saveUser(User user) {
        /*userDAO.saveUser(user);
		if (ksession == null) {
			System.out.println("=========");
		} else {
			System.out.println("+++++++++++++");
			// ksession.startProcess("com.sample.bpmn.hello");
			ksession.startProcess("com.sample.bpmn.test1_1");

			LocalHTWorkItemHandler humanTaskHandler = new LocalHTWorkItemHandler();
			humanTaskHandler.setRuntimeManager(runtimeManager);
			ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
					humanTaskHandler);
			ksession.startProcess("com.sample.bpmn.test1_2");
		}*/
    }

    /*@Override
    public List<Map<String, Object>> listAll() {
        //这个是使用xml定义的ksession和kbase
        Collection<org.kie.api.definition.process.Process> collection = kbase.getProcesses();
        List<org.kie.api.definition.process.Process> list = new ArrayList<org.kie.api.definition.process.Process>(collection);
        System.out.println(list.size());*/
    List<Map<String, Object>> rtList = Lists.newArrayList();

    /*for(org.kie.api.definition.process.Process process :list){
        Map<String, Object> proMap=Maps.newHashMap();
        proMap.put("processId", process.getId().toString());
        proMap.put("proName", process.getName());
        proMap.put("proVersion", process.getVersion());
        proMap.put("proMetaData", process.getMetaData());
        rtList.add(proMap);
    }
    return rtList;
}*/
    @Override
    public List<Map<String, Object>> listProcess() {
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
        KieBase kbase1 = engine.getKieSession().getKieBase();

        Collection<org.kie.api.definition.process.Process> collection = kbase1.getProcesses();
        List<org.kie.api.definition.process.Process> list = new ArrayList<org.kie.api.definition.process.Process>(collection);
        List<Map<String, Object>> rtList = Lists.newArrayList();
        for (org.kie.api.definition.process.Process process : list) {
            Map<String, Object> proMap = Maps.newHashMap();
            proMap.put("processId", process.getId().toString());
            proMap.put("proName", process.getName());
            proMap.put("proVersion", process.getVersion());
            proMap.put("proMetaData", process.getMetaData());
            rtList.add(proMap);
        }
        return rtList;
    }


    @Override
    public List<Map<String, Object>> listUserAllTask(String userId) {
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(userId, "en-UK");
        System.out.println("--------------------------------------------");
        System.out.println(list != null ? list.size() : 0);
        System.out.println("--------------------------------------------");

        List<Map<String, Object>> rtList = Lists.newArrayList();
        for (TaskSummary taskSummary : list) {
            Map<String, Object> proMap = Maps.newHashMap();
            proMap.put("getId", taskSummary.getId());
            proMap.put("getActualOwner", taskSummary.getActualOwner());
            proMap.put("getName", taskSummary.getName());
            proMap.put("getStatus", taskSummary.getStatus());
            rtList.add(proMap);
        }

        return rtList;

    }


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
    public void startProcess(String proId) {
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
        KieSession ksession1 = engine.getKieSession();

        LocalHTWorkItemHandler humanTaskHandler = new LocalHTWorkItemHandler();
        humanTaskHandler.setRuntimeManager(runtimeManager);
        ksession1.getWorkItemManager().registerWorkItemHandler("Human Task",
                humanTaskHandler);

        //把传值给工作流里面的变量
        Map<String, Object> inputParams = new HashMap<String, Object>();
        /*inputParams.put("var1", "This is a test message.");
        inputParams.put("name", "krisv");
        inputParams.put("age", "35");
        inputParams.put("content", "Yearly performance evaluation");
        inputParams.put("message", "This is a test message.");*/
        ksession1.startProcess(proId, inputParams);
        ksession1.fireAllRules();
        //ksession1.dispose();

    }

    @Override
    public void startRuleTaskProcess(String proId) {
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
        KieSession ksession1 = engine.getKieSession();

        LocalHTWorkItemHandler humanTaskHandler = new LocalHTWorkItemHandler();
        humanTaskHandler.setRuntimeManager(runtimeManager);
        ksession1.getWorkItemManager().registerWorkItemHandler("Human Task",
                humanTaskHandler);

        //把传值给工作流里面的变量
        Map<String, Object> params = new HashMap<String, Object>();
        HelloProcessModel hpm = new HelloProcessModel();
        hpm.setCount(new Integer("3"));
        hpm.setUserlocation("NewYorkUser");
        params.put("hpm", hpm);
        ksession1.startProcess(proId, params);
        ksession1.fireAllRules();

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

    @Override
    public void evaluation() {
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
        KieSession ksession = engine.getKieSession();
        TaskService taskService = engine.getTaskService();
        LocalHTWorkItemHandler humanTaskHandler = new LocalHTWorkItemHandler();
        humanTaskHandler.setRuntimeManager(runtimeManager);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                humanTaskHandler);

        // start a new process instance
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("employee", "krisv");
        params.put("reason", "Yearly performance evaluation");
        ProcessInstance processInstance =
                ksession.startProcess("com.sample.evaluation", params);
        System.out.println("Process started ...");

        // complete Self Evaluation
        List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        TaskSummary task = tasks.get(0);
        System.out.println("'krisv' completing task " + task.getName() + ": " + task.getDescription());
        taskService.start(task.getId(), "krisv");
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("performance", "exceeding");
        taskService.complete(task.getId(), "krisv", results);

        // john from HR
        tasks = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        task = tasks.get(0);
        System.out.println("'john' completing task " + task.getName() + ": " + task.getDescription());
        taskService.claim(task.getId(), "john");
        taskService.start(task.getId(), "john");
        results = new HashMap<String, Object>();
        results.put("performance", "acceptable");
        taskService.complete(task.getId(), "john", results);

        // mary from PM
        tasks = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        task = tasks.get(0);
        System.out.println("'mary' completing task " + task.getName() + ": " + task.getDescription());
        taskService.claim(task.getId(), "mary");
        taskService.start(task.getId(), "mary");
        results = new HashMap<String, Object>();
        results.put("performance", "outstanding");
        taskService.complete(task.getId(), "mary", results);

        System.out.println("Process instance completed");
        runtimeManager.disposeRuntimeEngine(engine);
        runtimeManager.close();

    }


    public InternalTaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(InternalTaskService taskService) {
        this.taskService = taskService;
    }

	/*public StatefulKnowledgeSession getKsession() {
		return ksession;
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public KieBase getKbase() {
		return kbase;
	}

	public void setKbase(KieBase kbase) {
		this.kbase = kbase;
	}*/

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

    public OrganizationalEntityDAO getOrganizationalEntityDAO() {
        return organizationalEntityDAO;
    }

    public void setOrganizationalEntityDAO(OrganizationalEntityDAO organizationalEntityDAO) {
        this.organizationalEntityDAO = organizationalEntityDAO;
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
