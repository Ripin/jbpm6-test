package com.sample;

import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.drools.persistence.PersistenceContextManager;
import org.drools.persistence.TransactionManager;
import org.jbpm.process.audit.AuditLogService;
import org.jbpm.process.audit.JPAAuditLogService;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.test.JBPMHelper;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.spring.persistence.KieSpringJpaManager;
import org.kie.spring.persistence.KieSpringTransactionManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * This is a sample file to launch a process.
 */
public class TestJTAEMF {

	public static final void main(String[] args) throws Throwable {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		
		ClassPathXmlApplicationContext context =
		    new ClassPathXmlApplicationContext("META-INF/kmodule-spring.xml");
		
		KieServices kservices = KieServices.Factory.get();
		/**
		 * No need for additional environment instance, one managed by runtime manager is enough
		 */
//		Environment env = kservices.newEnvironment();
//
		EntityManagerFactory emf = (EntityManagerFactory) context.getBean("jbpmEMF");
//		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
//		
		AbstractPlatformTransactionManager aptm = (AbstractPlatformTransactionManager) context.getBean( "jbpmTxManager" );	
		/**
		 * No need to create these manually, internals will ensure proper ones are used based
		 * on TransactionManager instance which is spring one - line above
		 */
//		TransactionManager transactionManager = new KieSpringTransactionManager( aptm );
//		env.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);
//
//		PersistenceContextManager persistenceContextManager = new KieSpringJpaManager(env);
//		env.set(EnvironmentName.PERSISTENCE_CONTEXT_MANAGER, persistenceContextManager);
		
		RuntimeEnvironment runtimeEnv = RuntimeEnvironmentBuilder.Factory.get()
        	.newDefaultBuilder()
        	.addAsset(
				kservices.getResources().newClassPathResource("sample.bpmn"),
				 ResourceType.BPMN2)
				 /**
				  * don't need to set it on environment, there is special method to accept EMF
				  */
//		    .addEnvironmentEntry(EnvironmentName.ENTITY_MANAGER_FACTORY, emf)
		    .addEnvironmentEntry(EnvironmentName.TRANSACTION_MANAGER, aptm)
		    /**
		     * This will be created automatically when transaction manager (line above) is spring object
		     */
//		    .addEnvironmentEntry(EnvironmentName.PERSISTENCE_CONTEXT_MANAGER, persistenceContextManager)
		    .entityManagerFactory(emf)
        	.get();
        RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(runtimeEnv);
        
        RuntimeEngine engine = manager.getRuntimeEngine(null);
        KieSession ksession = engine.getKieSession();
        TaskService taskService = engine.getTaskService();
        
        ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
		
		System.out.println("Process started");

		/**
		 * no need to use env we can simply pass EMF instead
		 */
		AuditLogService logService = new JPAAuditLogService(emf);
		ProcessInstanceLog log = logService.findProcessInstance(processInstance.getId());
		if (log == null) {
			throw new IllegalArgumentException("Process instance log not found");
		}
		
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		System.out.println("Found " + tasks.size() + " task(s) for user 'john'");
		if (tasks.size() != 1) {
			throw new IllegalArgumentException("Incorrect amount of tasks");
		}
		long taskId = tasks.get(0).getId();
		taskService.start(taskId, "john");
		taskService.complete(taskId, "john", null);
		
		tasks = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		System.out.println("Found " + tasks.size() + " task(s) for user 'mary'");
		if (tasks.size() != 1) {
			throw new IllegalArgumentException("Incorrect amount of tasks");
		}
		taskId = tasks.get(0).getId();
		taskService.start(taskId, "mary");
		taskService.complete(taskId, "mary", null);
			
		processInstance = ksession.getProcessInstance(processInstance.getId());
		if (processInstance == null) {
			System.out.println("Process instance completed");
		}
		
		System.out.println("******************************************************************");
		
		UserTransaction ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        ut.begin();
        processInstance = ksession.startProcess("com.sample.bpmn.hello");
        long processInstanceId = processInstance.getId();
        ut.rollback();

        processInstance = ksession.getProcessInstance(processInstanceId);
        
        if (processInstance == null) {
        	System.out.println("Process instance rolled back");
        } else {
        	throw new IllegalArgumentException("Process instance not rolled back");
        }
		
        tasks = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		System.out.println("Found " + tasks.size() + " task(s) for user 'john'");
		if (tasks.size() != 0) {
			throw new IllegalArgumentException("Incorrect amount of tasks");
		}
		/**
		 * no need to use env we can simply pass EMF instead
		 */
		logService = new JPAAuditLogService(emf);
		log = logService.findProcessInstance(processInstanceId);
		if (log != null) {
			throw new IllegalArgumentException("Log not rolled back");
		}
		
		System.exit(0);
	}

}