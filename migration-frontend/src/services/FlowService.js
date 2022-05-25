import axios from 'axios';

const URL_GET_FLOWS = "http://localhost:8090/flows";
const UPL_GET_FLOW_BY_ID = "http://localhost:8090/flow/";
const URL_POST_ADD_FLOW = "http://localhost:8090/flow";

class FlowService {

    getFlows() {
        return axios.get(URL_GET_FLOWS);
    }

    getFlowById(id) {
        return axios.get(UPL_GET_FLOW_BY_ID + id)
    }

    addFlow(name, nodes, edges) {
        nodes.forEach((node, id) => {
            node.name = node.data.label
        });
        console.log("changedNodes");
        console.log(nodes);
        const flow = {
            id: 1,
            name: name,
            nodes: nodes,
            edges: edges
        };
        console.log("flow");
        console.log(JSON.stringify(flow));
        return axios.post(URL_POST_ADD_FLOW, flow)
    }


}

export default new FlowService()