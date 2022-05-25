import axios from 'axios';

const URL_GET_TASKS = "http://localhost:8090/tasks";

class TaskService {

    getTasks() {
        return axios.get(URL_GET_TASKS);
    }
}

export default new TaskService()