import { Container } from '@mui/material';
import * as React from 'react';
import ExecutionsTabs from '../components/ExecutionsTabs';

export default function Executions() {   
      return (
        <div>
          <Container sx={{ mt: 5 }}>
            <ExecutionsTabs />
          </Container>
        </div>
      );
    }