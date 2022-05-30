import { Drawer, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';

const drawerWidth = 240;

export default function ExecutionPanel({ execution }) {

    console.log(execution)

    return (
        <Drawer
            variant="permanent"
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
            }}
        >
            <Toolbar />
            <Box sx={{ overflow: 'auto' }}>
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }}>EXECUTION PARAMETERS</Typography>
                <Divider />
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Status: {execution.status}</Typography>
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Start: {execution.startDate}</Typography>
                {
                    (execution.endDate != null) ? <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >End: {execution.endDate}</Typography> : <></>
                }
                {
                    (execution.errorMessage != null) ? <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >End: {execution.errorMessage}</Typography> : <></>
                }
            </Box>
        </Drawer>
    );
}