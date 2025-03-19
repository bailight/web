import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import MainPage from './MainPage';
import LoginForm from "./components/LoginForm";
import Header from "./components/Header";
import './style/App.css';
import RegisterForm from "./components/RegisterForm";
import PrivateRoute from "./components/PrivateRoute";

const App = () => {
    return (
        <Router>
            <div>
                <Header />
                <Routes>
                    <Route path="/" element={<LoginForm />} />
                    <Route path={'/main'} element={
                        <PrivateRoute>
                            <MainPage />
                        </PrivateRoute>
                    }/>
                    <Route path="/register" element={<RegisterForm />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;