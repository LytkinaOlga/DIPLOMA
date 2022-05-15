import Header from './components/Header';
import ExecutionsTabs from './components/ExecutionsTabs';
import './App.css';
import { Container, createTheme, ThemeProvider } from '@mui/material';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import Flows from './pages/Flows';
import AddFlow from './pages/AddFlow';
import Executions from './pages/Executions';
import { Component } from 'react';
import Flow from './pages/Flow';

const theme = createTheme({
  palette: {
    primary: {
      main: "#000000",
    }
  }
})

class Dashboard extends Component {
  render(){
    return (
      <>
        <BrowserRouter>
          <ThemeProvider theme={theme}>
            <Header />
            <Routes>
              <Route path="/executions" element={<Executions />} />
              <Route path="/flows" element={<Flows />} />
              <Route path="/add" element={<AddFlow />} />
              <Route path="/flow/:id" element={<Flow />} />
            </Routes>
          </ThemeProvider>
        </BrowserRouter>
      </>
    );
  }
}

export default Dashboard;
