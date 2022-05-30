import { Button, Drawer, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import ExecutionService from '../services/ExecutionService';
import TaskService from '../services/TaskService';

const drawerWidth = 240;

export default function ExecutionTaskPanel({ flowNode, executedNode, execution }) {

    console.log(flowNode);
    console.log(executedNode);
    console.log(execution);

    const [tasks, setTasks] = React.useState([]);
    const parameters = [{
        name: "",
        value: ""
    }]

    React.useEffect(() => {
        TaskService.getTasks().then((res) => {
            setTasks(res.data);
            const task = res.data.filter(element => element.id === flowNode.taskId)
            flowNode.parameters.forEach((param, id) => {
                task[0].parameters.forEach((element) => {
                    if (element.id === param.id){
                        param.name = element.name;
                    }
                })
                
            })
        })
    })

    const completeManualTask = () => {
        ExecutionService.completeManualTask(execution.id, flowNode.id).then((res) => console.log(res.data))
    }

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
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }}>EXECUTION TASK PARAMETERS</Typography>
                <Divider />
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Task Name: {executedNode.nodeName}</Typography>
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Status: {executedNode.status}</Typography>
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Start: {executedNode.startDate}</Typography>
                {
                    (executedNode.endDate != null) ? <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >End: {executedNode.endDate}</Typography> : <></>
                }
                <Divider />
                {
                    flowNode.parameters.map((param) => (
                        <>
                            <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >{param.name}: {param.value}</Typography>
                        </>
                    ))
                }
                {
                    (executedNode.status === "FAILED") ? <Typography sx={{ mt: 3, ml: 3, mb: 3 }} >Error: {execution.errorMessage}</Typography> : <></>
                }
                {
                    (flowNode.taskId === "2" && executedNode.status === "RUNNING") ? <Button variant="contained"
                        sx={{ mt: 3, ml: 3, mb: 3 }}
                        onClick={completeManualTask}
                    >COMPLETE</Button> : <></>
                }
            </Box>
        </Drawer>
    );
}