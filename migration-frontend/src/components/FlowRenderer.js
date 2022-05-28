import { Button, Typography } from '@mui/material';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import ReactFlow, {
    addEdge, Background, Controls, ReactFlowProvider, useEdgesState, useNodesState
} from 'react-flow-renderer';
import FlowService from '../services/FlowService';
import TaskService from '../services/TaskService';
import FlowParametersPanel from './FlowParametersPanel';
import LeftPanel from './LeftPanel';
import TaskParametersPanel from './TaskParametersPanel';

let id = 0;
const getId = () => `${id++}`;
const height = window.innerHeight;

export default function FlowRenderer({ initFlowId, initFlowName, flowNodes, flowEdges, initFlowDescription }) {

    const myNodes = [
        {
            id: '1',
            data: { label: 'Test node' },
            position: { x: 500, y: 100 }
        },
        {
            id: '2',
            data: { label: 'Test node 2' },
            position: { x: 500, y: 150 }
        },
        {
            id: '3',
            data: { label: 'Test node 3' },
            position: { x: 500, y: 200 }
        }
    ];

    const myEdges = [
        { id: 'e1-2', source: '1', target: '2' }
    ];

    const reactFlowWrapper = useRef(null);
    const [nodes, setNodes, onNodesChange] = useNodesState(flowNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(flowEdges);
    const [reactFlowInstance, setReactFlowInstance] = useState(null);
    const [flowName, setFlowName] = useState(initFlowName);
    const [flowDescription, setFlowDescription] = useState(initFlowDescription);
    const [flowId, setFlowId] = useState(initFlowId);
    const [isTaskSelected, setIsTaskSelected] = useState(false);
    const [tasks, setTasks] = useState([]);
    const [selectedTaskParameters, setSelectedTaskParameters] = useState([]);
    const [selectedTaskName, setSelectedTaskName] = useState([]);
    const [selectedTaskId, setSelectedTaskId] = useState([]);
    const [taskParametersWithValue, setTaskParametersWithValue] = useState({});

    useEffect(() => {
        flowNodes != undefined ? setNodes(flowNodes) : setNodes([]);
        flowEdges != undefined ? setEdges(flowEdges) : setEdges([]);
        initFlowName != undefined ? setFlowName(initFlowName) : setFlowName('');
        initFlowDescription != undefined ? setFlowDescription(initFlowDescription) : setFlowDescription('');
        initFlowId != undefined ? setFlowId(initFlowId) : setFlowId(null);
        TaskService.getTasks().then((res) => { setTasks(res.data) });
    }, [flowNodes, flowEdges])

    const onConnect = useCallback((params) => setEdges((eds) => addEdge(params, eds)), []);

    const onDragOver = useCallback((event) => {
        event.preventDefault();
        event.dataTransfer.dropEffect = 'move';
    }, []);

    const onDrop = useCallback(
        (event) => {
            event.preventDefault();

            const reactFlowBounds = reactFlowWrapper.current.getBoundingClientRect();
            const type = event.dataTransfer.getData('application/reactflow');
            const taskId = event.dataTransfer.getData('application/reactflow/taskId');

            // check if the dropped element is valid
            if (typeof type === 'undefined' || !type) {
                return;
            }

            const position = reactFlowInstance.project({
                x: event.clientX - reactFlowBounds.left,
                y: event.clientY - reactFlowBounds.top,
            });

            const newNode = {
                id: getId(),
                position,
                data: { label: `${type}` },
                taskId: taskId,
            };

            setNodes((nds) => nds.concat(newNode));
        },
        [reactFlowInstance]
    );

    const changeFlowName = (flowNameValue) => {
        setFlowName(flowNameValue);
    }

    const changeFlowDescription = (flowDescriptionValue) => {
        setFlowDescription(flowDescriptionValue);
    }

    const fillTastParameters = (taskWithParams) => {
        setTaskParametersWithValue(taskWithParams);
        console.log("taskWithParams");
        console.log(taskWithParams);
        console.log("nodes");
        console.log(nodes);
        const node = nodes.filter(node => node.taskId === taskWithParams.taskId);
        console.log("node[0]" + node[0])
        node[0].nodeParameters = taskWithParams.taskParams;
        console.log(node[0]);
        setNodes(nodes);
    }

    const saveFlow = () => {
        console.log("nodes before saving");
        console.log(nodes);
        FlowService.addFlow(flowId, flowName, flowDescription, nodes, edges).then((res) => {
            console.log(res.data);
        })
        alert("Flow saved");
    }

    const onNodeClick = (event, node) => {
        setIsTaskSelected(true);
        console.log(node);
        const taskId = node.taskId;
        const task = tasks.filter(task => task.id === taskId);
        console.log(task);
        const selectedTaskParams = task[0].parameters;
        const selectedTaskNameValue = task[0].name;
        const selectedTaskIdValue = task[0].id;
        console.log(selectedTaskParams);

        node.parameters.forEach((param) => {
            const selectedTaskParam = selectedTaskParams.filter(taskParam => param.paramId === taskParam.id);
            selectedTaskParam[0].value = param.value
        });

        setSelectedTaskParameters(selectedTaskParams);
        setSelectedTaskName(selectedTaskNameValue);
        setSelectedTaskId(selectedTaskIdValue);
    };

    const onPaneClick = () => {
        setIsTaskSelected(false);
    }

    const executeFlow = () => {
        FlowService.executeFlow(flowId).then((res) => {
            console.log(res.data);
            const executionId = res.data;
            window.location.href = '/execution/' + executionId;
        })
    }

    return (
        <div >
            <ReactFlowProvider>
                <div ref={reactFlowWrapper} style={{ height: (height) }}>
                    <ReactFlow
                        nodes={nodes}
                        edges={edges}
                        onNodesChange={onNodesChange}
                        onEdgesChange={onEdgesChange}
                        onConnect={onConnect}
                        onInit={setReactFlowInstance}
                        onDrop={onDrop}
                        onDragOver={onDragOver}
                        onNodeClick={onNodeClick}
                        onPaneClick={onPaneClick}
                        fitView
                    >
                        <Controls />
                        <Background />
                    </ReactFlow>
                </div>
                <LeftPanel tasks={tasks} />
                {
                    (isTaskSelected) ? <TaskParametersPanel 
                                            taskId={selectedTaskId}
                                            taskName={selectedTaskName} 
                                            taskParams={selectedTaskParameters} 
                                            fillTastParameters={fillTastParameters}
                                            /> :
                        <FlowParametersPanel
                            defaultFlowName={flowName}
                            changeFlowName={changeFlowName}
                            defaultFlowDescription = {flowDescription}
                            changeFlowDescription={changeFlowDescription}
                        />
                }
                <Button
                    sx={{ position: 'absolute', bottom: 50, right: 300, zIndex: 4 }}
                    variant="contained"
                    onClick={executeFlow}
                >
                    <Typography
                    // component={Link}
                    // to={"/executions"}
                    // style={{ textDecoration: 'none', color: 'white' }}

                    >  EXECUTE</Typography>
                </Button>
                <Button
                    sx={{ position: 'absolute', bottom: 50, right: 430, zIndex: 4 }}
                    variant="contained"
                    onClick={saveFlow}
                >
                    <Typography>SAVE</Typography>
                </Button>
            </ReactFlowProvider>
        </div >
    );
};
