
import * as React from 'react';
import ReactFlow, { Background, Controls, ReactFlowProvider } from 'react-flow-renderer';

const testNodes = [
    {
        id: '1',
        data: { label: "Dump loading" },
        position: { x: 700, y: 200 }
    },
    {
        id: '2',
        data: { label: "MasterList Creator" },
        position: { x: 700, y: 280 }
    },
    {
        id: '3',
        data: { label: "Notify other system" },
        position: { x: 950, y: 280 }
    },
    {
        id: '4',
        data: { label: "Validate ML Adapter" },
        position: { x: 825, y: 360 }
    },
    {
        id: '5',
        data: { label: "Load data to system A" },
        position: { x: 825, y: 440 }
    },
    {
        id: '6',
        data: { label: "Manual check" },
        position: { x: 825, y: 520 }
    },
    {
        id: '7',
        data: { label: "Load data to system B" },
        position: { x: 700, y: 600 }
    },
    {
        id: '8',
        data: { label: "Load data to system D" },
        position: { x: 950, y: 600 }
    },
    {
        id: '9',
        data: { label: "Send report" },
        position: { x: 825, y: 680 }
    }
];

const testEdges = [
    {
        id: '1',
        source: '1',
        target: '2'
    },
    {
        id: '2',
        source: '2',
        target: '4'
    },
    {
        id: '3',
        source: '3',
        target: '4'
    },
    {
        id: '4',
        source: '4',
        target: '5'
    },
    {
        id: '5',
        source: '5',
        target: '6'
    },
    {
        id: '6',
        source: '6',
        target: '7'
    },
    {
        id: '7',
        source: '6',
        target: '8'
    },
    {
        id: '8',
        source: '7',
        target: '9'
    },
    {
        id: '9',
        source: '8',
        target: '9'
    }
]

const height = window.innerHeight;

class TestFlow extends React.Component {

    render() {
        return (
            <ReactFlowProvider>
                <div style={{ height: (height) }}>
                    <ReactFlow
                        nodes={testNodes}
                        edges={testEdges}
                    />
                    <Background />
                    <Controls />
                </div>
            </ReactFlowProvider>
        )
    }
}

export default TestFlow;