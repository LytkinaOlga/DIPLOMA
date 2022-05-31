
import { Container, Typography } from '@mui/material';
import * as React from 'react';
import { useParams } from 'react-router-dom';


export default function Statistics(props) {
    console.log("statistics");
    console.log(useParams())
    return (
        <>
            <Container sx={{ mt: 13 }}>
                <Typography>Hello</Typography>
            </Container>
        </>
    );
}