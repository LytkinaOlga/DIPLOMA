import { Container, Typography } from '@mui/material';
import * as React from 'react';
import FlowRenderer from '../components/FlowRenderer';
import FlowService from '../services/FlowService';
import { Routes, Route, useParams } from 'react-router-dom';


class Flow extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            flow: [
                {
                    id: "",
                    name: "",
                    createdDate: "",
                    nodes: [{
                        id: "",
                        data: {
                            label: "node"
                        },
                        position: {
                            x: 500,
                            y: 100
                        }
                    }],
                    edges: [{
                        id: "",
                        source: "",
                        target: ""
                    }]
                }
            ]
        }
    }    

    componentDidMount() {
        console.log("ola id: " + this.props.params.id)
        FlowService.getFlowById(this.props.params.id).then((res) => {
            res.data.nodes[0].position = {
                x: 500,
                y: 100
            };
            res.data.nodes[0].data = {
                label: "voly"
            };
            res.data.nodes[1].position = {
                x: 500,
                y: 150
            }
            res.data.nodes[1].data= {
                label: "voly"
            };
            this.setState({ flow: res.data });
            console.log(res.data)
        })
    }

    render() {
        const myNodes = [
            {
                id: 1,
                data: { label: 'Test node' },
                position: { x: 500, y: 100 }
            },
            {
                id: '2',
                type: 'output',
                data: { label: 'Test node 2' },
                position: { x: 500, y: 150 }
            },
            {
                id: '3',
                type: 'output',
                data: { label: 'Test node 3' },
                position: { x: 500, y: 200 }
            }
        ];
        return (
            <div>
                <FlowRenderer nodes = {this.state.flow.nodes}
                              edges = {this.state.flow.edges}/>
            </div>
        )
    }
}
export default Flow;




