import axios from 'axios';

const URL_GET_FLOWS = "http://localhost:8090/flows";
const UPL_GET_FLOW_BY_ID = "http://localhost:8090/flow/";

class FlowService {

    getFlows(){
        return axios.get(URL_GET_FLOWS);
    }

    getFlowById(id){
        return axios.get(UPL_GET_FLOW_BY_ID, {
            params: {
                id: id
            }
        })
    }
}

export default new FlowService()