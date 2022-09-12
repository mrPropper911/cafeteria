import React, {useState} from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Stack from "@mui/system/Stack";

function Login(){
    const [user, setUser] = useState ({
        userphone: '',
        password: ''
    });

    const [isAuthenticated, setAuth] = useState(false);

    const handleChange = (event) => {
        setUser({...user,
            [event.target.name] : event.target.value
        });
    }

    const login = () => {
        fetch('http://localhost:8080/login', {
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
    
    if (isAuthenticated){
        return <div>HOME PAGE</div>
    }
    else{
        return(
            <div>
               <Stack spacing={2} alignItems='center' mt={2}>
                   <TextField name="userphone" label="Userphone" onChange={handleChange}/>
                   <TextField name="password" label="Password" onChange={handleChange}/>
                   <Button variant="outlined" color="primary" onClick={login}>Login</Button>
         
               </Stack>
            </div>
        );
    }    
}

export default Login;