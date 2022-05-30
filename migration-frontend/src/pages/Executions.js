import { Container } from '@mui/material';
import * as React from 'react';
import ExecutionsTabs from '../components/ExecutionsTabs';
import ExecutionService from '../services/ExecutionService';

const STATUS_RUNNINIG = "RUNNING";
const STATUS_SUCCEEDED = "SUCCEEDED";
const STATUS_UPCOMING = "UPCOMING";
const STATUS_FAILED = "FAILED";

export default function Executions() {
    const [executions, setExecutions] = React.useState([]);
    const [runningExecutions, setRunningExecutions] = React.useState([]);
    const [completedExecutions, setCompletedExecutions] = React.useState([]);
    const [upcomingExecutions, setUpcomingExecutions] = React.useState([]);

    React.useEffect(() => {
        ExecutionService.getExecutions().then((res) => {
            setExecutions(res.data);
            console.log(res.data);
            res.data.forEach((execution, id ) => {
                let executionExists = false;
                if (execution.status === STATUS_RUNNINIG)
                {
                    runningExecutions.forEach((element) => {
                        if (element.id === execution.id){
                            executionExists = true;
                        }
                    })
                    if (!executionExists)
                    {
                        runningExecutions.push(execution);
                    }                    
                };
                if (execution.status === STATUS_UPCOMING)
                {
                    upcomingExecutions.forEach((element) => {
                        if (element.id === execution.id){
                            executionExists = true;
                        }
                    })
                    if (!executionExists)
                    {
                        upcomingExecutions.push(execution);
                    }   
                };
                if (execution.status === STATUS_SUCCEEDED || execution.status === STATUS_FAILED)
                {
                    completedExecutions.forEach((element) => {
                        if (element.id === execution.id){
                            executionExists = true;
                        }
                    })
                    if (!executionExists)
                    {
                        completedExecutions.push(execution);
                    }
                };
            })
            console.log(completedExecutions);
            setRunningExecutions(runningExecutions);
            setCompletedExecutions(completedExecutions);
            setUpcomingExecutions(upcomingExecutions);
        })
    }, [])

    return (
        <div>
            <Container sx={{ mt: 13 }}>
                <ExecutionsTabs 
                    runningExecutions = {runningExecutions}
                    completedExecutions = {completedExecutions}
                    upcomingExecutions = {upcomingExecutions}
                    allExecutions = {executions}                  
                    />
            </Container>
        </div>
    );
}