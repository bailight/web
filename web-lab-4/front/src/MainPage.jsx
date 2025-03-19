import React, {useState} from 'react';
import Welcome from "./components/Welcome";
import {useDispatch, useSelector} from "react-redux";
import Canvas from "./components/Canvas";
import PointForm from "./components/PointForm";
import ResultTable from "./components/ResultTable";
import {check, clearInfoAndError, getResults} from "./action/ResultAction";

const MainPage = () => {
    const { username } = useSelector((state) => state.auth);
    const { results } = useSelector((state) => state.results);
    const [r, setR] = useState(1);
    const dispatch = useDispatch();

    const handleCanvasClick = async (data) => {
        await dispatch(check(data));
        await dispatch(getResults({username})).unwrap().then(() => {
            setTimeout(() => {
                dispatch(clearInfoAndError());
            }, 1500);
        });
    };

    return (
        <div className="main-page">
            <div className="container">
                <Welcome/>
            </div>
            <div className="container">
                <div className="card" id="graph">
                    <Canvas r={r} results={results} onCanvasClick={handleCanvasClick}/>
                </div>
                <div className="card" id="point">
                    <PointForm r={r} setR={setR}/>
                    <div className="card">
                        <ResultTable results={results}/>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MainPage;