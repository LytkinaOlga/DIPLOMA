import axios from 'axios';

const UPL_GET_EXECUTION_BY_ID = "http://localhost:8090/execution/";

class ExecutionService {

    getExecutionById(id) {
        return axios.get(UPL_GET_EXECUTION_BY_ID + id)
    }

}

export default new ExecutionService()