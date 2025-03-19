import axios from 'axios';
import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";

export const check =  createAsyncThunk('results/check', async ({x, y, r, username}, {rejectWithValue }) => {
    try {
        const response = await axios.post('http://localhost:8080/back/api/results/check', {x, y, r, username});
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

export const clear = createAsyncThunk('results/clear', async ({username}, {rejectWithValue }) => {
    try {
        const response = await axios.post('http://localhost:8080/back/api/results/clear',{username});
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

export const getResults = createAsyncThunk('results/getResults', async ({username}, {rejectWithValue }) => {
    try {
        const response = await axios.post('http://localhost:8080/back/api/results',{username});
        return response.data.results;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

const resultsSlice = createSlice({
    name: 'results',
    initialState: {
        username: null,
        results: [],
        info: null,
        error: null
    },
    reducers: {
        clearInfoAndError: (state) => {
            state.info = null;
            state.error = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(check.fulfilled, (state, action) => {
                state.info = action.payload.message;
            })
            .addCase(check.rejected, (state, action) => {
                state.error = action.payload;
            })

            .addCase(clear.fulfilled, (state, action) => {
                state.info = action.payload.message;
                state.results = [];
            })
            .addCase(clear.rejected, (state, action) => {
                state.error = action.payload;
            })

            .addCase(getResults.fulfilled, (state, action) => {
                state.results = action.payload;
            })
            .addCase(getResults.rejected, (state, action) => {
                state.error = action.payload;
            });
    }
});

export  const { clearInfoAndError } = resultsSlice.actions;
export default resultsSlice.reducer;
