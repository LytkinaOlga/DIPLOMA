import { Drawer, TextField } from '@mui/material';
import * as React from 'react';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { useParams } from 'react-router-dom';

const drawerWidth = 240;

export default function TaskParametersPanel({ taskName, taskParams }) {
    const params = Object.keys(taskParams);
    console.log(params[0]);
    return (
        <Drawer
            variant="permanent"
            anchor="right"
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
            }}
        >
            <Toolbar />
            <Box sx={{ overflow: 'auto' }}>
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }}>TASK PARAMETERS</Typography>
                <Divider />
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Task Name: {taskName}</Typography>
                {
                     params.map((param) => (
                        <TextField 
                            variant="outlined"
                            sx={{ ml: 2, mt: 3, mr: 2 }}
                            label={param} />
                     ))
                }              
            </Box>
        </Drawer>
    );
}