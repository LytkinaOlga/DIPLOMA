import * as React from 'react';
import { useParams } from 'react-router-dom';
import ExecutionRenderer from '../components/ExecutionRenderer';
import FlowRenderer from '../components/FlowRenderer';
import ExecutionService from '../services/ExecutionService';
import FlowService from '../services/FlowService';

export default function Execution() {
    const executionId = useParams().executionId;
    console.log(useParams().executionId);

    const [execution, setExecution] = React.useState('');
    const [flow, setFlow] = React.useState('');

    React.useEffect(() => {
        console.log("useeffect")
        ExecutionService.getExecutionById(executionId).then((execution) => {
            setExecution(execution.data);
            console.log("result");
            console.log(execution.data);
            if (execution.data.flowId != undefined) {
                FlowService.getFlowById(execution.data.flowId).then((res) => {
                    console.log(res.data);

                    execution.data.nodes.forEach((node, id) => {
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
            }
        })



    }, [])

    return (
        <>
            <ExecutionRenderer
                flowNodes={flow.nodes}
                flowEdges={flow.edges}
            />
        </>
    )
}