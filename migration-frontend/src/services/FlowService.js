import axios from 'axios';

const URL_GET_FLOWS = "http://localhost:8090/flows";
const UPL_GET_FLOW_BY_ID = "http://localhost:8090/flow/";
const URL_POST_ADD_FLOW = "http://localhost:8090/flow";

class FlowService {

    getFlows(){
        return axios.get(URL_GET_FLOWS);
    }

    getFlowById(id){
        return axios.get(UPL_GET_FLOW_BY_ID + id)
    }

    addFlow(nodes, edges){
        console.log(nodes);
        console.log(edges);
        return axios.post(URL_POST_ADD_FLOW, {
            name: "test ola555",
            nodes: nodes,
            edges: edges
        })
    }
}

export default new FlowService()