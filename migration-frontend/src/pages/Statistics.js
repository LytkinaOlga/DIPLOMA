
import { Container, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import ExecutionService from '../services/ExecutionService';
import { Bar } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';

ChartJS.register(
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
    { field: 'errorMessage', headerName: 'ERROR MESSAGE', width: 300 }
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
                        backgroundColor: 'rgba(53, 162, 235, 0.5)',
                    }
                ],
            };
            setData(data);
            console.log(data);
        })

    }, [])

    console.log("statistics");
    console.log(useParams())
    return (
        <Container sx={{ mt: 15 }}>
            {
                (data.datasets != undefined) ? <Bar options={options} data={data} /> : <></>
            }
            
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