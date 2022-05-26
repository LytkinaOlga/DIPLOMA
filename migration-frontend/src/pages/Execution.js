import * as React from 'react';
import { useParams } from 'react-router-dom';
import FlowRenderer from '../components/FlowRenderer';
import ExecutionService from '../services/ExecutionService';
import FlowService from '../services/FlowService';

export default function Execution() {
    const executionId = useParams();
    console.log(executionId.executionId);

    const [execution, setExecution] = React.useState(null);
    const [flow, setFlow] = React.useState(null);

    React.useEffect(() => {
        ExecutionService.getExecutionById(executionId.executionId).then((res) => {
            setExecution(res.data);
            console.log("result");
            console.log(execution);
        })
        FlowService.getFlowById(execution.flowId).then((res) => {
            console.log(res.data);

            execution.nodes.forEach((node, id) => {
                res.data.nodes[id].status = node.status;
            })

            res.data.nodes.forEach((node, id) => {
                node.data = {
                    label: node.name + " " + node.status
                }
                delete node.name;
            })
            
            setFlow(res.data);
            console.log("flow");
            console.log(flow);
        })
    }, [])

    return (
        <>
            <FlowRenderer
                initFlowId = {flow.id}
                flowNodes = {flow.nodes}
                flowEdges = {flow.edges}
                />
        </>
    )
}