import { Container, Typography } from '@mui/material';
import * as React from 'react';
import FlowRenderer from '../components/FlowRenderer';
import FlowService from '../services/FlowService';
import { Routes, Route, useParams } from 'react-router-dom';


class Flow extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            flow:
            {
                id: "",
                name: "",
                createdDate: "",
                nodes: [{
                    id: "",
                    data: {
                        label: ""
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

        }
    }

    componentDidMount() {
        console.log("ola id: " + this.props.params.id);
        FlowService.getFlowById(this.props.params.id).then((res) => {

            res.data.nodes.forEach((node, id) => {
                node.data = {
                    label: node.name
                }
                delete node.name;
            })
            this.setState({ flow: res.data });
            console.log(this.state.flow);
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
                <FlowRenderer 
                    flowNameValue = {this.state.flow.name}
                    myNodess={this.state.flow.nodes}
                    myEdgess={this.state.flow.edges} />
            </div >
        )
    }
}
export default Flow;




