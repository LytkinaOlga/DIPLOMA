import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Button, CardActions } from '@mui/material';
import { Link } from 'react-router-dom';

export default function FlowCard({flow}) {
    return (
        <Card sx={{ mt: 5 }}>
            <CardContent>
                <Typography component={Link} to={`/flow/${flow.id}`}>{flow.name}</Typography>
                <Typography>{flow.creationDate}</Typography>
            </CardContent>
            <CardActions>
                <Button>EXECUTE</Button>
                <Button>VIEW EXECUTIONS</Button>
            </CardActions>
        </Card>
    );
}