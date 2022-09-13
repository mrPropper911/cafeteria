import React, {useState} from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import { Grid, Paper, Box, rgbToHex } from "@mui/material";

import {SERVER_URL} from './constants'

function Login(){
    let[authMode, setAuthMode] = useState("signin");

    const changeAuthMode = () => {
        setAuthMode(authMode === "signin" ? "signup" : "signin")
    }

    const [user, setUser] = useState ({
        username: '',
        password: ''
    });

    const [newUser, setNewUser] = useState ({
        username: '',
        surname: '',
        location: '',
        phone: '',
        email: '',
        password: ''
    })

    const handleNewChange = (event) => {
        setNewUser({...newUser,
            [event.target.name] : event.target.value
        });
    }
    

    const [isAuthenticated, setAuth] = useState(false);

    const handleChange = (event) => {
        setUser({...user,
            [event.target.name] : event.target.value
        });
    }

    const login = () => {
        fetch(SERVER_URL + 'login', {
            method: 'POST',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(user)
        }).then(res => {
            const jwtToken = res.headers.get('Authorization');
            if(jwtToken != null){
                sessionStorage.setItem("jwt", jwtToken);
                setAuth(true);
            }
        }).catch(err => console.error(err))
    }

    const createNewUser = () => {

    }

    if (isAuthenticated){
        return <div>
           HOME
        </div>
    }
    else
    {
        if (authMode === "signin"){
            return(
                <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh">
                     <Paper elevation={3} style={{marginBottom: "10px", padding: "45px 40px 5px 40px", borderRadius: "10px" ,textAlign: "left"}}>
    
                        <h3 style={{textAlign: 'center', marginBottom: "1em", fontSize: "24px", fontWeight: "800"}}>Sign In</h3>
                            <Grid container direction="column" justifyContent="center" alignItems="center">
                                <TextField style={{marginBottom: "10px"}} size="small" name="username" label="Username" onChange={handleChange} />
                                <TextField style={{marginBottom: "4px"}} size="small" name="password" type={"password"} label="Password" onChange={handleChange}/>
                                <h5 style={{fontSize: "13px", fontWeight: "300",marginBottom: "15px", paddingRight: "60px"}}>
                                    Forgot <a style={{textDecoration: "none"}} href="#">password?</a>
                                </h5>
                                <Grid style={{marginBottom: "30px"}} container direction="row" justifyContent="space-between" alignItems="center" >
                                    <Button variant="outlined" color="secondary" onClick={changeAuthMode}>Sign Up</Button>
                                    <Button variant="outlined" color="primary" onClick={login}>Sign In</Button>
                                </Grid>
                            </Grid>
                    </Paper>
                </Box>
            );
        }

        return(
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh">
                     <Paper elevation={3} style={{marginBottom: "10px", padding: "45px 40px 5px 40px", borderRadius: "10px" ,textAlign: "left"}}>
    
                        <h3 style={{textAlign: 'center', marginBottom: "1em", fontSize: "24px", fontWeight: "800"}}>Create new account</h3>
                            <Grid container direction="column" justifyContent="center" alignItems="center">
                                <TextField style={{marginBottom: "10px"}} size="small" name="username" label="Username" onChange={handleNewChange} />
                                <TextField style={{marginBottom: "10px"}} size="small" name="surname" label="Surname" onChange={handleNewChange} />
                                <TextField style={{marginBottom: "10px"}} size="small" name="location" label="Location" onChange={handleNewChange} />
                                <TextField style={{marginBottom: "10px"}} size="small" name="phone" label="Phone" onChange={handleNewChange} />
                                <TextField style={{marginBottom: "10px"}} size="small" name="email" label="Email" onChange={handleNewChange} />
                                <TextField style={{marginBottom: "30px"}} size="small" name="password" type={"password"} label="Password" onChange={handleChange}/>
                                <Grid container direction="row" justifyContent="space-between" alignItems="center" style={{marginBottom: "30px"}} >
                                    <Button variant="outlined" color="error" onClick={changeAuthMode}>Back</Button>
                                    <Button variant="outlined" color="primary" onClick={createNewUser}>Submit</Button>
                                </Grid>
                            </Grid>
                    </Paper>
                </Box>
        )
        
    }    
}

export default Login;