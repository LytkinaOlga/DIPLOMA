import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Typography from '@mui/material/Typography';
import PropTypes from 'prop-types';
import * as React from 'react';
import ExecutionCard from './ExecutionCard';

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};
function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function ExecutionsTabs({ runningExecutions, completedExecutions, upcomingExecutions, allExecutions }) {
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ width: '100%' }}>
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" centered="true">
          <Tab label="RUNNING EXECUTIONS" {...a11yProps(0)} />
          <Tab label="COMPLETED EXECUTIONS" {...a11yProps(1)} />
          <Tab label="UPCOMING EXECUTIONS" {...a11yProps(2)} />
          <Tab label="ALL EXECUTIONS" {...a11yProps(3)} />
        </Tabs>
      </Box>
      <TabPanel value={value} index={0}>
        {
          runningExecutions.map((execution) => (
            <ExecutionCard execution={execution} />
          ))
        }
      </TabPanel>
      <TabPanel value={value} index={1}>
        {
          completedExecutions.map((execution) => (
            <ExecutionCard execution={execution} />
          ))
        }
      </TabPanel>
      <TabPanel value={value} index={2}>
        {
          upcomingExecutions.map((execution) => (
            <ExecutionCard execution={execution} />
          ))
        }
      </TabPanel>
      <TabPanel value={value} index={3}>
        {
          allExecutions.map((execution) => (
            <ExecutionCard execution={execution} />
          ))
        }
      </TabPanel>
    </Box>
  );
}
