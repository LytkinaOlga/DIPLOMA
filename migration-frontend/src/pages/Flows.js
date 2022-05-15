import { Button, Card, CardActions, CardContent, Container, Typography } from '@mui/material';
import * as React from 'react';
import FlowCard from '../components/FlowCard';

export default function Flows() {
    return (
        <Container sx={{ mt: 13 }}>
            <FlowCard/>        
            <FlowCard/>  
            <FlowCard/>  
            <FlowCard/>  
        </Container>
    );
}