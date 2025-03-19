import {createAsyncThunk, createSlice} from '@reduxjs/toolkit';
import axios from 'axios';

export const login = createAsyncThunk('auth/login', async ({ username, password }, { rejectWithValue }) => {
    if (!username ||!password) {
        return rejectWithValue('Username and password are required');
    }
    try {
        const response = await axios.post('http://localhost:8080/back/api/users/login', {username, password});
        console.log(response.data)
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

export const register = createAsyncThunk('auth/register', async ({ username, password },  { rejectWithValue}) => {
    if (!username ||!password) {
        return rejectWithValue('Username and password are required');
    }
    try {
        const response = await axios.post('http://localhost:8080/back/api/users/register', { username, password });
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

const authSlice = createSlice({
    name: 'auth',
    initialState: {
        isAuthenticated: false,
        token: null,
        username: null,
        info: null,
        error: null
    },
    reducers: {
        logout: (state) => {
            state.isAuthenticated = false;
            state.token = null;
            state.username = null;
            state.info = null;
            state.error = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(login.fulfilled, (state, action) => {
                if (action.payload.status === "200") {
                    state.isAuthenticated = true;
                    state.token = action.payload.token;
                    state.username = action.payload.username;
                    state.error = null;
                } else {
                    state.error = action.payload.message;
                }
            })
            .addCase(login.rejected, (state, action) => {
                state.error = action.payload;
            })
            .addCase(register.fulfilled, (state, action) => {
                state.info = action.payload.message;
                state.error = null;
            })
            .addCase(register.rejected, (state, action) => {
                state.info = null;
                state.error = action.payload;
            });
    }
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;