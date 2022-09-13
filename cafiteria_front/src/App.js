import './App.css';

import "bootstrap/dist/css/bootstrap.min.css"
import { BrowserRouter, Routes, Route, Router } from "react-router-dom"

import Login from "./components/Login"


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element={<Login/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
