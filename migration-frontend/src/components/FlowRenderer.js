import { Button, Typography } from '@mui/material';
import { getThemeProps } from '@mui/system';
import React, { useState, useRef, useCallback, useEffect } from 'react';
import ReactFlow, {
    ReactFlowProvider,
    addEdge,
    useNodesState,
    useEdgesState,
    Controls,
    Background,
    MiniMap,
} from 'react-flow-renderer';
import FlowService from '../services/FlowService';
import LeftPanel from './LeftPanel';

let id = 0;
const getId = () => `${id++}`;

export default function FlowRenderer({myNodess, myEdgess}) {

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
    const [nodes, setNodes, onNodesChange] = useNodesState(myNodess);
    const [edges, setEdges, onEdgesChange] = useEdgesState(myEdgess);
    const [reactFlowInstance, setReactFlowInstance] = useState(null);

    useEffect(()=> {
        setNodes(myNodess);
        setEdges(myEdgess)
    }, [myNodess, myEdgess])

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
            };

            setNodes((nds) => nds.concat(newNode));
        },
        [reactFlowInstance]
    );
    

    function handleClick(){
        FlowService.addFlow(nodes, edges).then((res) => {
            console.log(res.data);
        })
        alert("Hi");
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
                        fitView
                    >
                        <Controls />
                        <Background />
                    </ReactFlow>
                </div>
                <LeftPanel />
                <Button
                    sx={{ position: 'absolute', bottom: 50, right: 300 , zIndex: 4}}
                    variant="contained"
                >
                    <Typography>EXECUTE</Typography>
                </Button>
                <Button
                    sx={{ position: 'absolute', bottom: 50, right: 430 , zIndex: 4}}
                    variant="contained"
                    onClick = {handleClick}
                >
                    <Typography>SAVE</Typography>
                </Button>
            </ReactFlowProvider>
        </div >
    );
};
