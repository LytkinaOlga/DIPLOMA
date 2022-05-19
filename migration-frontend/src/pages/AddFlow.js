import { Button, Typography } from '@mui/material';
import * as React from 'react';
import FlowRenderer from '../components/FlowRenderer';
import RightPanel from '../components/RightPanel';

import FileCopyIcon from '@mui/icons-material/FileCopyOutlined';
import SaveIcon from '@mui/icons-material/Save';
import PrintIcon from '@mui/icons-material/Print';
import ShareIcon from '@mui/icons-material/Share';
import FlowService from '../services/FlowService';

class AddFlow extends React.Component {


    render(){
        return (
            <>            
                <FlowRenderer />
                <RightPanel />
                
            </>
        );
    }    
}

export default AddFlow;