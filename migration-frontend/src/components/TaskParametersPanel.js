import { Drawer, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';

const drawerWidth = 240;

export default function TaskParametersPanel({ taskId, taskName, taskParams, fillTastParameters }) {

    console.log(taskParams);
    console.log(taskParams[0].value);
    const handleInput = (event) => {
        const id = event.target.name;
        const paramValue = event.target.value;
        const taskWithParams = {
            taskId: taskId,
            taskParam: {
                paramId: id,
                paramValue: paramValue
            }
        }
        console.log(taskWithParams)
        fillTastParameters(taskWithParams);
    }

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
                    taskParams.map((param) => (
                        <TextField
                            variant="outlined"
                            sx={{ ml: 2, mt: 3, mr: 2 }}
                            label={param.name} 
                            name={param.id}
                            value={param.value != undefined ? param.value : ""}
                            onChange={handleInput}
                            />
                    ))
                }
            </Box>
        </Drawer>
    );
}