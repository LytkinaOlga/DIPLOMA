import { Drawer, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';

const drawerWidth = 240;

export default function FlowParametersPanel({ defaultFlowName, changeFlowName, defaultFlowDescription, changeFlowDescription }) {

    const handleNameInput = (event) => {
        changeFlowName(event.target.value);
    }

    const handleDescriptionInput = (event) => {
        changeFlowDescription(event.target.value);
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
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }}>FLOW PARAMETERS</Typography>
                <Divider />
                <TextField
                    label="Flow Name"
                    variant="outlined"
                    sx={{ ml: 2, mt: 3, mr: 2 }}
                    value={defaultFlowName}
                    onChange={handleNameInput}
                />
                <TextField
                    label="Flow Description"
                    variant="outlined"
                    sx={{ ml: 2, mt: 3, mr: 2 }}
                    value={defaultFlowDescription}
                    onChange={handleDescriptionInput}
                />
            </Box>
        </Drawer>
    );
}