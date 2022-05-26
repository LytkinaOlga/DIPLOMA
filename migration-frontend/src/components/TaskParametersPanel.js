import { Drawer, TextField } from '@mui/material';
import * as React from 'react';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { useParams } from 'react-router-dom';

const drawerWidth = 240;

export default function TaskParametersPanel(params) {

    const[flowName, setFlowName] = React.useState("New Flow");

    const handleInput = (event) => {
        setFlowName(event.target.value);
        console.log(flowName);
        params.changeFlowName(flowName);
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
                <Typography>TASK: {params.taskName}</Typography>
                <Divider />
                <TextField
                    label="Flow Name"
                    variant="outlined"
                    sx={{ ml: 2, mt: 3, mr: 2 }}
                    onInput={handleInput}
                />
            </Box>
        </Drawer>
    );
}