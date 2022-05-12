import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import LinearProgress from '@mui/material/LinearProgress';

export default function ExecutionCard() {
  return (
    <Card sx={{ minWidth: 275, mt: 5 }}>
      <CardContent>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          MIGRATION #1
        </Typography>
        <LinearProgress variant="determinate" />
      </CardContent>
    </Card>
  );
}