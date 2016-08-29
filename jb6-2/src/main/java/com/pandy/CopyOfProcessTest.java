package com.pandy;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

/**
 * This is a sample file to test a process.
 */
public class CopyOfProcessTest extends JbpmJUnitBaseTestCase {

	@Test
	public void testProcess() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(emf).knowledgeBase(kbase);
		RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "com.sample:example:1.0");
		
		
		RuntimeManager manager = createRuntimeManager("com/pandy/jbpm/test1_1.bpmn");
		RuntimeEngine engine = getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();
		
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.test1_2");
		// check whether the process instance has completed successfully
		assertProcessInstanceCompleted(processInstance.getId(), ksession);
		assertNodeTriggered(processInstance.getId(), "Hello");
		
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("pandy", "en-UK");
		TaskSummary task = list.get(0);
		taskService.start(task.getId(), "pandy");
		taskService.complete(task.getId(), "pandy", null);
		
		manager.disposeRuntimeEngine(engine);
		manager.close();
	}

}