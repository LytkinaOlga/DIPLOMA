import { Button, Card, CardContent, CardHeader, CardMedia, Drawer, Icon } from '@mui/material';
import React, { useState, useRef, useCallback, useEffect } from 'react';
import PropTypes from 'prop-types';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MailIcon from '@mui/icons-material/Mail';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import AddchartRoundedIcon from '@mui/icons-material/AddchartRounded';
import BackupTableRoundedIcon from '@mui/icons-material/BackupTableRounded';
import TaskService from '../services/TaskService';

const drawerWidth = 240;

export default function LeftPanel({tasks}) {

    const onDragStart = (event, nodeType, taskId) => {
        event.dataTransfer.setData('application/reactflow', nodeType);
        event.dataTransfer.setData('application/reactflow/taskId', taskId);
        event.dataTransfer.effectAllowed = 'move';
    };

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
                <Typography sx={{ mt: 3, ml: 3, mb: 3 }}>TASK CATALOG</Typography>
                <Divider sx={{ mb: 2 }} />
                {
                    tasks.map((task) => (
                        <Button variant="outlined" sx={{ ml: 1, width: 100, mt: 2 }} onDragStart={(event) => onDragStart(event, task.name, task.id)} draggable>
                            <Box>
                                <BackupTableRoundedIcon sx={{ width: 50, height: 50 }} />
                                <Typography variant='subtitle2'>{task.name}</Typography>
                            </Box>
                        </Button>
                    ))
                }
            </Box>
        </Drawer>
    );
}