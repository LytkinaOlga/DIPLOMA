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
        return (
            <div>
                <FlowRenderer 
                    initFlowId = {this.state.flow.id}
                    initFlowName = {this.state.flow.name}
                    flowNodes = {this.state.flow.nodes}
                    flowEdges = {this.state.flow.edges}/>
            </div >
        )
    }
}
export default Flow;




