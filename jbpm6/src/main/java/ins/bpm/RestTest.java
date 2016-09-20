package ins.bpm;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.kie.services.client.api.RemoteRuntimeEngineFactory;

/**
 * des
 *
 * @author Ripin Yan
 * @version $Id: RestTest.java  2016/9/20 17:31$
 */
public class RestTest {

    public static void main(String[] args) throws MalformedURLException {
        // Setup the factory class with the necessarry information to communicate with the REST services
        RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
                .addUrl(new URL("http://127.0.0.1:8888/jbpm-console"))
                .addTimeout(5)
                .addDeploymentId("org.jbpm:Evaluation:1.1")
                .addUserName("krisv")
                .addPassword("krisv")
                // if you're sending custom class parameters, make sure that
                // the remote client instance knows about them!
                // .addExtraJaxbClasses(MyType.class)
                .build();

        // Create KieSession and TaskService instances and use them
        KieSession ksession = engine.getKieSession();
        TaskService taskService = engine.getTaskService();

        // Each operation on a KieSession, TaskService or AuditLogService (client) instance
        // sends a request for the operation to the server side and waits for the response
        // If something goes wrong on the server side, the client will throw an exception.
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("reason", "rest");
        params.put("employee", "krisv");
        //ProcessInstance processInstance = ksession.startProcess("evaluation", params);
        //ProcessInstance processInstance1 = ksession.getProcessInstance(41L);
        //System.out.println(processInstance1.getProcessId());
        //long procId = processInstance.getId();
        long procId = 43L;

        String taskUserId = "krisv";
        taskService = engine.getTaskService();
        List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");

        long taskId = -1;
        for (TaskSummary task : tasks) {
            if (task.getProcessInstanceId() == procId) {
                taskId = task.getId();
            }
        }

        if (taskId == -1) {
            throw new IllegalStateException("Unable to find task for " + "krisv" +
                    " in process instance " + procId);
        }

        taskService.claim(taskId, taskUserId);
        taskService.start(taskId, taskUserId);

        // resultData can also just be null
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("Performance", "Very good!");
        taskService.complete(taskId, taskUserId, resultData);
    }

}
