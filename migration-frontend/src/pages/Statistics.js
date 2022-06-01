
import { Box, Container, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import ExecutionService from '../services/ExecutionService';
import { Bar, Pie } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
    ArcElement
} from 'chart.js';

ChartJS.register(
    ArcElement,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

const columns = [
    {
        field: 'entityId',
        headerName: 'ENTITY ID',
        width: 150
    },
    {
        field: 'status',
        headerName: 'STATUS',
        width: 150
    },
    { field: 'failedOn', headerName: 'FAILED ON', width: 250 },
    { field: 'errorMessage', headerName: 'ERROR MESSAGE', width: 1000 }
];
const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Chart.js Bar Chart',
        },
    },
};
export default function Statistics(props) {

    const executionId = useParams().executionId;
    const [masterList, setMasterList] = React.useState([]);
    const [execution, setExecution] = React.useState({});
    const [data, setData] = React.useState({});
    const [dataForPie, setDataForPie] = React.useState({});



    React.useEffect(() => {
        console.log(executionId)
        ExecutionService.getMasterList(executionId).then((res) => {
            console.log(res.data.entities[0]);
            setMasterList(res.data.entities);
        })
        ExecutionService.getExecutionById(executionId).then((res) => {
            setExecution(res.data);
            console.log(res.data);
            const labels = [];
            res.data.nodes.forEach((node) => {
                labels.push(node.nodeName);
            })
            console.log(labels);
            const quantity = [];
            res.data.nodes.forEach((node) => {
                quantity.push(node.successfullyProcessedEntities);
            })
            console.log(quantity);
            const data = {
                labels,
                datasets: [
                    {
                        label: 'Migrated entities',
                        data: quantity,
                        backgroundColor: [
                            'rgba(255, 99, 132)',
                            'rgba(54, 162, 235)',
                            'rgba(255, 206, 86)',                            
                          ],
                    }
                ],
            };
            setData(data);
            console.log(data);
            const labelsForPie = [];
            labels.forEach((element) => {
                if (element != "Master List Creation"){
                    labelsForPie.push(element + " working time");
                }                
            })
            console.log(labelsForPie);
            const workingTime = [];
            res.data.nodes.forEach((node) => {
                const duration = node.startDate - node.endDate;
                console.log(duration);
                workingTime.push(duration);
            })
            const dataForPie = {
                labels,
                datasets: [
                    {
                        label: 'Migrated entities',
                        data: quantity,
                        backgroundColor: [
                            'rgba(255, 99, 132)',
                            'rgba(54, 162, 235)',
                            'rgba(255, 206, 86)',                            
                          ],
                    }
                ],
            };
            setDataForPie(dataForPie);
        })

    }, [])

    console.log("statistics");
    console.log(useParams())
    return (
        <Container sx={{ mt: 15 }}>
            <Box sx={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between', flexWrap: 'wrap' }}>
                <div style={{width: '600px'}}>
            {
                (data.datasets != undefined) ? <Bar options={options} data={data} /> : <></>
            }
            </div>
            <div style={{width: '400px'}}>
            {
                (data.datasets != undefined) ? <Pie data={dataForPie} /> : <></>
            }
            </div>
            </Box>
            

            <div style={{ height: 800, width: '100%', marginTop: 40 }}>
                <DataGrid
                    rows={masterList}
                    columns={columns}
                    pageSize={50}
                    rowsPerPageOptions={[50]}
                    getRowId={(row) => row.entityId}
                />
            </div>
        </Container>
    );
}