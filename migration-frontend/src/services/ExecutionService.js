import axios from 'axios';

const UPL_GET_EXECUTION_BY_ID = "http://localhost:8090/execution/";
const URL_GET_EXECUTIONS = "http://localhost:8090/executions";

class ExecutionService {

    getExecutionById(id) {
        return axios.get(UPL_GET_EXECUTION_BY_ID + id)
    }

    getExecutions() {
        return axios.get(URL_GET_EXECUTIONS);
    }

    completeManualTask(executionId, nodeId) {
        return axios.post("http://localhost:8090/execution/" + executionId + "/complete/" + nodeId);
    }

}

export default new ExecutionService()