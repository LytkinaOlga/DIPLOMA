import { Button, Drawer, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { Link } from 'react-router-dom';
import FlowService from '../services/FlowService';
import TaskService from '../services/TaskService';

const drawerWidth = 240;

export default function ExecutionPanel({ execution }) {
    const [migrationStatisticsNeeded, setMigrationStatisticsNeeded] = React.useState(false);

    console.log(execution)

    React.useEffect(() => {
        let masterListTaskId = null;
        TaskService.getTasks().then((res) => {
            const tasks = res.data;
            console.log(tasks);
            tasks.forEach((task) => {
                if (task.name === "Master List Creator"){
                    masterListTaskId = task.id;
                }                    
            })
            console.log(masterListTaskId);
            if (masterListTaskId != null) {
                FlowService.getFlowById(execution.flowId).then((res) => {
                    const nodes = res.data.nodes;
                    nodes.forEach((node) => {
                        if (node.taskId === masterListTaskId)
                        {
                            setMigrationStatisticsNeeded(true);
                        }
                    })
                    console.log(nodes);
                })
            }
        })
        
        
    })

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
                {
                    (migrationStatisticsNeeded && (execution.status === "SUCCEEDED" || execution.status === "FAILED")) ? <Button variant="contained"
                    component={Link}
                    to={`/statistics/${execution.id}`}
                    sx={{ mt: 3, ml: 3, mb: 3 }}
                        >VIEW STATISTICS</Button> : <></>
                }
            </Box>
        </Drawer>
    );
}