package ins.bpm.common;

/**
 * Rest api
 *
 * @author Ripin Yan
 * @version $Id: RestConstants.java  2016/9/22 15:39$
 */
public final class RestConstants {

    public interface RuntimeREST {

        /*
         * returns basic information about the process definition, including process variable information
         */
        String GET_PROCESS_DEFINITION = "/runtime/{deploymentId}/process/{procDefId}";

        /*
         * start a process instance based on the Process definition (accepts query map parameters)
         */
        String POST_START_PROCESS_INSTANCE = "/runtime/{deploymentId}/process/{procDefId}/start";
    }

    public interface TaskREST {
        /*
         * claim the task
         */
        String POST_CLAIM_TASK = "/task/{taskId}/claim";

        /*
         * complete the task (accepts query map paramaters)
         */
        String POST_COMPLETE_TASK = "/task/{taskId}/complete";

        /*
         * start the task
         */
        String POST_START_TASK = "/task/{taskId}/start";


    }
}
