import { Container } from '@mui/material';
import * as React from 'react';
import ExecutionsTabs from '../components/ExecutionsTabs';

export default function Executions() {
    return (
        <div>
            <Container sx={{ mt: 13 }}>
                <ExecutionsTabs />
            </Container>
        </div>
    );
}