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
    const [counter, setCounter] = React.useState(0);

    React.useEffect(() => {
        console.log("useeffect")
        ExecutionService.getExecutionById(executionId).then((execution) => {
            setExecution(execution.data);
            console.log("result");
            console.log(execution.data);
            monitorFlow(execution);                     
        })
    }, [counter])

    function monitorFlow(execution){
        if (execution.data.flowId != undefined) {
            FlowService.getFlowById(execution.data.flowId).then((res) => {
                console.log(res.data.nodes);
                execution.data.nodes.forEach((node, id) => {
                    const changedNode = res.data.nodes.filter(flowNode => flowNode.id == node.nodeId);
                    changedNode[0].status = node.status;
                    console.log("Yes");
                    // if (node.nodeName === "Manual Task" && node.status === "RUNNING")
                    // {
                    //     console.log("Yes");
                    //     changedNode[0].type = 'manualTask'
                    // }
                    if (node.status === "RUNNING") {
                        changedNode[0].style = {
                            borderColor: '#FFEA2C',
                            borderWidth: 2,
                        }
                    };
                    if (node.status === "SUCCEEDED") {
                        changedNode[0].style = {
                            borderColor: '#2EB92B',
                            borderWidth: 2,
                        }
                    };
                    if (node.status === "FAILED") {
                        changedNode[0].style = {
                            borderColor: '#C63030',
                            borderWidth: 2,
                        }
                    }
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
        };
        if (execution.data.endDate === null || execution.data.endDate === undefined) {
            setTimeout(()=> {
                setCounter(counter + 1);
            }, 3000)            
        }
    }

    return (
        <>
            <ExecutionRenderer
                flowNodes={flow.nodes}
                flowEdges={flow.edges}
            />
        </>
    )
}