import { Button, Card, CardActions, CardContent, Container, Typography } from '@mui/material';
import * as React from 'react';
import FlowCard from '../components/FlowCard';
import FlowService from '../services/FlowService';

const myFlowsExample = [
    {
        flowId: '1',
        name: 'TestFlow1',
        creationDate: "01-02-2022"
    },
    {
        flowId: '2',
        name: 'TestFlow2',
        creationDate: "01-03-2022"
    },
    {
        flowId: '3',
        name: 'TestFlow3',
        creationDate: "01-04-2022"
    }
];

class Flows extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            flows: [
                {
                    id: "",
                    name: "",
                    creationDate: ""
                }
            ]
        }
    }

    componentDidMount() {
        FlowService.getFlows().then((res) => {
            this.setState({ flows: res.data });
        })
    }

    render() {
        return (
            <Container sx={{ mt: 13 }}>
                {
                    this.state.flows.map((flow) => (
                        <FlowCard flow={flow} key={flow.id} />
                    ))
                }
            </Container>
        );
    }
}

export default Flows;