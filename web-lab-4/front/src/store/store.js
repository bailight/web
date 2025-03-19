import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../action/AuthAction';
import resultReducer from  '../action/ResultAction';

const store = configureStore({
    reducer: {
        auth: authReducer,
        results: resultReducer
    }
});

export default store;