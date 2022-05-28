import { Button } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import LinearProgress from '@mui/material/LinearProgress';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { Link } from 'react-router-dom';

export default function ExecutionCard({ execution }) {
  return (
    <Card sx={{ minWidth: 275, mt: 5 }}>
      <CardContent>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          Start: {execution.startDate}
        </Typography>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          End: {execution.endDate}
        </Typography>
        <Typography>Status: {execution.status}</Typography>
        <Button
          variant="contained"
          component={Link}
          to={`/execution/${execution.id}`}
        >Details
        </Button>
      </CardContent>
    </Card>
  );
}