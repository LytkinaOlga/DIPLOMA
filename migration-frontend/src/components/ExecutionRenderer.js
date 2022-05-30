import React, { useCallback, useEffect, useRef, useState } from 'react';
import ReactFlow, {
    addEdge, Background, Controls, ReactFlowProvider, useEdgesState, useNodesState
} from 'react-flow-renderer';
import ExecutionPanel from './ExecutionPanel';
import ExecutionTaskPanel from './ExecutionTaskPanel';

let id = 0;
const getId = () => `${id++}`;
const height = window.innerHeight;

export default function ExecutionRenderer({ flowNodes, flowEdges, execution }) {

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
    const [isTaskSelected, setIsTaskSelected] = useState(false);
    const [selectedNode, setSelectedNode] = useState();
    const [selectedExecutionNode, setSelectedExecutionNode] = useState();

    useEffect(() => {
        flowNodes != undefined ? setNodes(flowNodes) : setNodes([]);
        flowEdges != undefined ? setEdges(flowEdges) : setEdges([]);
    }, [flowNodes, flowEdges])

    const onNodeClick = (event, node) => {
        console.log("click node " + node);
        console.log(node);

        execution.nodes.forEach((element) => {
            if (element.nodeId === node.id) {
                setSelectedExecutionNode(element);
            }
        })

        setIsTaskSelected(true);
        setSelectedNode(node);
    };

    const onPaneClick = () => {
        console.log("click pane ");
        setIsTaskSelected(false);
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
                        onInit={setReactFlowInstance}
                        nodesDraggable={false}
                        onNodeClick={onNodeClick}
                        onPaneClick={onPaneClick}
                        fitView
                    >
                        <Background />
                    </ReactFlow>
                </div>
                {
                    (isTaskSelected) ? <ExecutionTaskPanel
                        flowNode={selectedNode}
                        executedNode={selectedExecutionNode}
                        execution={execution}
                    /> : <ExecutionPanel execution={execution} />
                }
            </ReactFlowProvider>
        </div >
    );
};
