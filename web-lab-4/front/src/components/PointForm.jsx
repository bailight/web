import React, {useEffect, useState} from 'react';
import {
    Alert,
    Box,
    Button,
    Container,
    FormControl,
    Input,
    InputLabel,
    MenuItem,
    Select,
    Snackbar,
    Stack
} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {check, clear, getResults} from "../action/ResultAction";

const PointForm = ({r = 1, setR}) => {
    const { username } = useSelector((state) => state.auth);
    const { error, info } = useSelector((state) => state.results);
    const dispatch = useDispatch();

    const [x, setX] = useState(0);
    const [y, setY] = useState(0);

    const handleXChange = (e) => {
        setX(parseFloat(e.target.value));
    };

    const handleYChange = (e) => {
        const value = parseFloat(e.target.value);
        if (value >= -3 && value <= 3) {
            setY(value);
        }
    };

    const handleRChange = (e) => {
        setR(parseFloat(e.target.value));
    };

    const checkPoint = () => {
        dispatch(check({ x, y, r, username}));
        dispatch(getResults({ username }));
        console.log(`X: ${x}, Y: ${y}, R: ${r}`);
    };

    const clearPoint = () => {
        dispatch(clear({username}));
    }

    const xOptions = [-4, -3, -2, -1, 0, 1, 2, 3, 4];
    const rOptions = [1, 2, 3, 4, 5];

    const [openErrorSnackbar, setOpenErrorSnackbar] = useState(false);
    const [openInfoSnackbar, setOpenInfoSnackbar] = useState(false);

    useEffect(() => {
        if (error) {
            setOpenErrorSnackbar(true);
            const timer = setTimeout(() => {
                setOpenErrorSnackbar(false);
            }, 1000);
            return () => clearTimeout(timer);
        }
    }, [error]);

    useEffect(() => {
        if (info) {
            setOpenInfoSnackbar(true);
            const timer = setTimeout(() => {
                setOpenInfoSnackbar(false);
            }, 1000);
            return () => clearTimeout(timer);
        }
    }, [info]);

    return (
        <div className="card">
            <Container>
                <Snackbar
                    open={openErrorSnackbar}
                    autoHideDuration={5000}
                    onClose={() => setOpenErrorSnackbar(false)}
                >
                    <Alert severity="error">{error}</Alert>
                </Snackbar>
                <Snackbar
                    open={openInfoSnackbar}
                    autoHideDuration={5000}
                    onClose={() => setOpenInfoSnackbar(false)}
                >
                    <Alert severity="success">{info}</Alert>
                </Snackbar>
                <Stack alignItems="center" justifyContent="center">
                    <Box>
                        <h1>Point check</h1>
                    </Box>
                </Stack>
            </Container>
            <form id="point-form">
                <Stack direction="row" spacing={3} sx={{width: '100%'}}>
                    <FormControl sx={{width: 120}}>
                        <InputLabel id="x-label">X</InputLabel>
                        <Select
                            labelId="x-label"
                            id="x-select"
                            value={x}
                            label="X"
                            onChange={handleXChange}
                        >
                            {xOptions.map((option) => (
                                <MenuItem key={option} value={option}>
                                    {option}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Input sx={{width: 120}} type="number" label="Y" value={y} onChange={handleYChange}/>
                    <FormControl sx={{width: 120}}>
                        <InputLabel id="r-label">R</InputLabel>
                        <Select
                            labelId="r-label"
                            id="r-select"
                            value={r}
                            label="R"
                            onChange={handleRChange}
                        >
                            {rOptions.map((option) => (
                                <MenuItem key={option} value={option}>
                                    {option}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Stack direction="row" spacing={2} sx={{flexGrow: 1, justifyContent: 'center'}}>
                        <Button variant="contained" onClick={checkPoint}>
                            Check
                        </Button>
                        <Button variant="contained" onClick={clearPoint}>
                            Clear
                        </Button>
                    </Stack>
                </Stack>
            </form>
        </div>
    );
}

export default PointForm;