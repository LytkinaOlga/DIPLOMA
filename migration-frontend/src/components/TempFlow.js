import * as React from 'react';
import { useParams } from "react-router-dom";
import Flow from '../pages/Flow';


export default function TempFlow(props) {    
    return (
      <Flow params = {useParams()}/>
    );
  }