
import { Container, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import ExecutionService from '../services/ExecutionService';

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

export default function Statistics(props) {

    const executionId = useParams().executionId;
    const [masterList, setMasterList] = React.useState([]);

    React.useEffect(() => {
        console.log(executionId)
        ExecutionService.getMasterList(executionId).then((res) => {
            console.log(res.data.entities[0]);
            setMasterList(res.data.entities);
        })
    }, [])

    console.log("statistics");
    console.log(useParams())
    return (
        <Container sx={{ mt: 15 }}>
            <div style={{ height: 800, width: '100%' }}>
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