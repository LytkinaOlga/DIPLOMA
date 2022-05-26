import { Button, Typography } from '@mui/material';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import ReactFlow, {
    addEdge, Background, Controls, ReactFlowProvider, useEdgesState, useNodesState
} from 'react-flow-renderer';
import FlowService from '../services/FlowService';
import FlowParametersPanel from './FlowParametersPanel';
import LeftPanel from './LeftPanel';
import TaskParametersPanel from './TaskParametersPanel';

let id = 0;
const getId = () => `${id++}`;

export default function FlowRenderer({initFlowId, initFlowName, flowNodes, flowEdges}) {

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
    const [flowId, setFlowId] = useState(initFlowId);
    const [isTaskSelected, setIsTaskSelected] = useState(false);

    useEffect(() => {
        flowNodes != undefined ? setNodes(flowNodes) : setNodes([]);
        flowEdges != undefined ? setEdges(flowEdges) : setEdges([]);
        initFlowName != undefined ? setFlowName(initFlowName) : setFlowName('');
        initFlowId != undefined ? setFlowId(initFlowId) : setFlowId(null);
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


    const handleClick = () => {
        FlowService.addFlow(flowId, flowName, nodes, edges).then((res) => {
            console.log(res.data);
        })
        alert("Flow saved");
    }

    const onNodeClick = (event, node) => {
        setIsTaskSelected(true);
        console.log('click node', node);
    };
    
    const onPaneClick = (event) => {
        setIsTaskSelected(false);
    }

    return (
        <div >
            <ReactFlowProvider>
                <div ref={reactFlowWrapper} style={{ height: 950 }}>
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
                <LeftPanel />
                {
                    (isTaskSelected) ? <TaskParametersPanel 
                    taskName = 'Test'/> : <FlowParametersPanel
                                                    defaultFlowName={flowName}
                                                    changeFlowName={changeFlowName}
                /> 
                }
                <Button
                    sx={{ position: 'absolute', bottom: 50, right: 300, zIndex: 4 }}
                    variant="contained"
                >
                    <Typography>EXECUTE</Typography>
                </Button>
                <Button
                    sx={{ position: 'absolute', bottom: 50, right: 430, zIndex: 4 }}
                    variant="contained"
                    onClick={handleClick}
                >
                    <Typography>SAVE</Typography>
                </Button>
            </ReactFlowProvider>
        </div >
    );
};
