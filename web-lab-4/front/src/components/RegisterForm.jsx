import React, { useState } from 'react';
import {TextField, Button, Container, Box, Alert, Stack} from '@mui/material';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from 'react-redux';
import {register} from "../action/AuthAction";

const RegisterForm = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();
    const { error, info } = useSelector((state) => state.auth);

    const handleRegister = async () => {
        dispatch(register({ username, password }));
    };

    const LoginNavigate = () => {
        navigate('/');
    };

    return (
        <div className="container_start">
            <Container maxWidth="xs">
                <Box mb={4}>
                    {error && <Box m={1}><Alert severity="error">{error}</Alert></Box>}
                    {info && <Box m={1}><Alert severity="success">{info}</Alert></Box>}
                    <Stack alignItems="center" justifyContent="center">
                        <Box>
                            <h1>Register</h1>
                        </Box>
                    </Stack>
                    <Box>
                        <TextField
                            label="Username"
                            fullWidth
                            margin="normal"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </Box>
                    <Box mb={2}>
                        <TextField
                            label="Password"
                            type="password"
                            fullWidth
                            margin="normal"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </Box>
                    <Button variant="contained" fullWidth onClick={handleRegister}>
                        Register
                    </Button>
                    <Box>
                        <Button variant="text" fullWidth onClick={LoginNavigate} sx={{ textTransform: 'none' }}>
                            Return Login
                        </Button>
                    </Box>
                </Box>
            </Container>
        </div>
    );
};

export default RegisterForm;