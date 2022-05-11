import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';

import { Link } from "react-router-dom";

export default function Header() {   
      return (  
          <AppBar 
            position="static" 
            color="primary"
            enableColorOnDark="true"
          >
            <Toolbar>
                  <Typography variant="h6" component={Link} to={"/executions"} sx={{ flexGrow: 2 }} style={{ textDecoration: 'none', color: 'white'}}>
                            EXECUTIONS
                  </Typography> 
                  <Typography variant="h6" component={Link} to={"/flows"} sx={{ flexGrow: 2 }} style={{ textDecoration: 'none', color: 'white'}}>
                            FLOWS
                  </Typography> 
                  <Typography variant="h6" component={Link} to={"/add"} sx={{ flexGrow: 40 }} style={{ textDecoration: 'none' , color: 'white'}}>
                              NEW FLOWS
                  </Typography>                  
                <Button color="inherit">Login</Button>
            </Toolbar>
          </AppBar>
      );
    }





