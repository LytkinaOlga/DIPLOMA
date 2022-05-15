import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Button, CardActions } from '@mui/material';

export default function FlowCard() {
    return (
        <Card sx={{ mt: 5 }}>
            <CardContent>
                <Typography>Flow #1</Typography>
            </CardContent>
            <CardActions>
                <Button>EXECUTE</Button>
                <Button>VIEW EXECUTIONS</Button>
            </CardActions>
        </Card>
    );
}