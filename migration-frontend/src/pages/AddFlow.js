import { Box, Button, Container, SpeedDial, SpeedDialIcon, SpeedDialAction } from '@mui/material';
import * as React from 'react';
import FlowRenderer from '../components/FlowRenderer';
import LeftPanel from '../components/LeftPanel';
import RightPanel from '../components/RightPanel';

import FileCopyIcon from '@mui/icons-material/FileCopyOutlined';
import SaveIcon from '@mui/icons-material/Save';
import PrintIcon from '@mui/icons-material/Print';
import ShareIcon from '@mui/icons-material/Share';

const actions = [
    { icon: <FileCopyIcon />, name: 'Copy' },
    { icon: <SaveIcon />, name: 'Save' },
    { icon: <PrintIcon />, name: 'Print' },
    { icon: <ShareIcon />, name: 'Share' },
];

export default function AddFlow() {
    return (
        <>
            
            <FlowRenderer />
            <RightPanel />
            <Button
                sx={{ position: 'absolute', bottom: 50, right: 300 }}
                variant="contained"
            >
                EXECUTE
            </Button>
        </>
    );
}