import React, {useRef, useEffect, useCallback} from "react";
import {useSelector} from "react-redux";

const Canvas = ({ r = 1, results, onCanvasClick }) => {
    const canvasRef = useRef(null);
    const { username } = useSelector((state) => state.auth);

    const drawCanvas = useCallback(() => {
        let R = r;
        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;

        ctx.clearRect(0, 0, width, height);

        drawGrid(ctx, width, height);

        const centerX = width / 2;
        const centerY = height / 2;
        const scale = 50;

        createOxi(ctx, width, centerX, centerY, height);

        comments(ctx, scale, centerX, width, R);

        ctx.beginPath();
        ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.2)');
        ctx.fillRect(centerX, centerY - scale * R, scale * R, scale * R);

        ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.2)');
        ctx.moveTo(centerY, centerX);
        ctx.arc(centerY, centerX, scale * R, 0, 0.5 * Math.PI, false);
        ctx.lineTo(centerX, centerY);
        ctx.fill();

        ctx.beginPath();
        ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.3)');
        ctx.moveTo(centerY, centerX);
        ctx.lineTo(centerY, centerX - scale * R);
        ctx.lineTo(centerY - scale * R / 2, centerX);
        ctx.fill();

        if (results && results.length > 0) {
            results.forEach(result => {
                const { x, y } = result;
                drawPoints(x, y);
            });
        }
    }, [r, results]);

    const createGradient = (context, canvas, color) => {
        const gradient = context.createLinearGradient(0, 0, canvas.width, canvas.height);
        gradient.addColorStop(0, color);
        return gradient;
    };

    const createOxi = (ctx, width, centerX, centerY, height) => {
        // X和Y轴
        ctx.beginPath();
        ctx.strokeStyle = "#000000";
        ctx.moveTo(0, centerY);
        ctx.lineTo(width, centerY);
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX, height);
        // 箭头
        ctx.moveTo(width, centerY);
        ctx.lineTo(width - 10, centerY - 5);
        ctx.moveTo(width, centerY);
        ctx.lineTo(width - 10, centerY + 5);
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX - 5, 10);
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX + 5, 10);
        ctx.stroke();
    };

    const drawGrid = (ctx, width, height) => {
        ctx.strokeStyle = "#808080";
        ctx.lineWidth = 0.5;

        const step = 50;
        for (let x = 0; x < width; x += step) {
            ctx.beginPath();
            ctx.moveTo(x, 0);
            ctx.lineTo(x, height);
            ctx.stroke();
        }
        for (let y = 0; y < height; y += step) {
            ctx.beginPath();
            ctx.moveTo(0, y);
            ctx.lineTo(width, y);
            ctx.stroke();
        }
    };

    const comments = (ctx, scale, centerX, width, R) => {
        ctx.font = "12px Arial";
        ctx.textAlign = "center";
        ctx.fillStyle = "#000000";
        ctx.fillText("-R", centerX - scale * R, centerX + 10);
        ctx.fillText("-R/2", centerX - scale / 2 * R, centerX + 10);
        ctx.fillText("R/2", centerX + scale / 2 * R, centerX + 10);
        ctx.fillText("R", centerX + scale * R, centerX + 10);
        ctx.fillText("x", width - 5, centerX - 10);

        ctx.fillText("-R", centerX + 10, centerX + scale * R);
        ctx.fillText("-R/2", centerX + 10, centerX + scale / 2 * R);
        ctx.fillText("R/2", centerX + 10, centerX - scale / 2 * R);
        ctx.fillText("R", centerX + 10, centerX - scale * R);
        ctx.fillText("y", centerX + 10, 10);
    };

    const drawPoints = (x, y) => {
        const canvas = canvasRef.current;
        const ctx = canvas.getContext("2d");
        const scale = 50;

        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;

        const pointX = centerX + x * scale;
        const pointY = centerY - y * scale;

        ctx.fillStyle = "black";
        ctx.beginPath();
        ctx.arc(pointX, pointY, 3, 0, 2 * Math.PI);
        ctx.fill();
    };

    useEffect(() => {
        drawCanvas();
    }, [drawCanvas, r, results]);

    return (
        <canvas
            ref={canvasRef}
            width="400"
            height="400"
            className="Canv"
            onClick={(e) => {
                const canvas = canvasRef.current;
                const rect = canvas.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                const xCord = (x - 200) / 50;
                const yCord = (200 - y) / 50;
                drawPoints({x: xCord, y: yCord});

                if (onCanvasClick) {
                    onCanvasClick({ x: xCord.toFixed(2), y: yCord.toFixed(2), r , username});
                }
            }}
        />
    );
}

export default Canvas;
