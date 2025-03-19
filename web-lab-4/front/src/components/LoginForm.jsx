import React, { useState } from'react';
import { useDispatch, useSelector } from 'react-redux';
import {login} from "../action/AuthAction";
import {Alert, Box, Button, Container, Stack, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {getResults} from "../action/ResultAction";

const LoginForm = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();
    const { error } = useSelector((state) => state.auth);
    const { isAuthenticated } = useSelector((state) => state.auth);

    const handleLogin = () => {
        dispatch(login({ username, password }));
        dispatch(getResults({username}));
    };

    const RegisterNavigate = () => {
        navigate('/register');
    };

    React.useEffect(() => {
        if (isAuthenticated) {
            navigate('/main');
        }
    }, [isAuthenticated, navigate]);

    return (
        <div className="container_start">
            <form style={{ width: '100%' }}>
                <Container maxWidth="xs">
                    <Box mb={4}>
                        {error && <Box m={1}><Alert severity="error">{error}</Alert></Box>}
                        <Stack alignItems="center" justifyContent="center">
                            <Box>
                                <h1>Login</h1>
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
                        <Button variant="contained" fullWidth onClick={handleLogin}>
                            Login
                        </Button>
                        <Box>
                            <Button variant="text" fullWidth onClick={RegisterNavigate} sx={{ textTransform: 'none' }}>
                                Haven't account?
                            </Button>
                        </Box>
                    </Box>
                </Container>
            </form>
        </div>
    );

};

export default LoginForm;