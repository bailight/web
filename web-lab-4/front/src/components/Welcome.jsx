import React from 'react';
import {logout} from "../action/AuthAction";
import {useDispatch, useSelector} from "react-redux";
import {Box, Button} from "@mui/material";

const Welcome = () => {
    const { username } = useSelector((state) => state.auth);
    const dispatch = useDispatch();

    const onLogout = () => {
        dispatch(logout());
    };

    return (
        <div className="welcome">
            <Box sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                padding: '16px',
                borderRadius: '4px',
                marginTop: '20px'
            }}>
                <h2 style={{ margin: 10 }}>Welcome, {username}</h2>
                <Button
                    variant="outlined"
                    onClick={onLogout}
                >
                    Logout
                </Button>
            </Box>
        </div>
    )
}

export default Welcome;