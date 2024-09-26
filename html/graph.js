document.getElementById("R").addEventListener("change", drawGraph);

function drawGraph() {
     // 选中画布
    const R = document.getElementById("R").value;
    const canvas = document.getElementById('Canv');
    const ctx = Canv.getContext('2d');
    
    const width = canvas.width;
    const height = canvas.height;

    // 清除画布
    ctx.clearRect(0, 0, width, height);

    // 画网格
    drawGrid(ctx, width, height);

    // 选中心
    const centerX = width / 2;
    const centerY = height / 2;
    const scale = 50;

    createOxi(ctx, width, centerX, centerY, height);

    //标签
    comments(ctx, scale, centerX, width, R);

    
    ctx.beginPath();
    //长方形
    ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.2)');
    ctx.fillRect(centerX - scale * R, centerY - scale * R / 2, scale * R, scale * R / 2);

    //1/4圆形
    ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.2)');
    ctx.moveTo(centerY, centerX);
    ctx.arc(centerY, centerX, scale * R, 0 * Math.PI, 0.5 * Math.PI, false);
    ctx.lineTo(centerX, centerY);
    ctx.fill();
    
    //三角形
    ctx.beginPath();
    ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.3)');
    ctx.moveTo(centerY, centerX); 
    ctx.lineTo(centerY, centerX - scale * R);
    ctx.lineTo(centerY + scale * R, centerX);
    ctx.fill();

    

}

function createGradient(context, canvas, color) {
    const gradient = context.createLinearGradient(0, 0, canvas.width, canvas.height);
    gradient.addColorStop(0, color);
    return gradient;
}


function createOxi(ctx, width, centerX, centerY, height){
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
}

function drawGrid(ctx, width, height){
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
}

function comments(ctx, scale, centerX, width, R){
    ctx.font = "12px Arial";
    ctx.textAlign = "center";
    ctx.fillStyle = "#000000";
    ctx.fillText("-R", centerX - scale * R, centerX + 10);
    ctx.fillText("-R/2", centerX - scale/2 * R , centerX + 10);
    ctx.fillText("R/2", centerX + scale/2 * R , centerX + 10);
    ctx.fillText("R", centerX + scale * R , centerX + 10);
    ctx.fillText("x", width - 5, centerX - 10);

    ctx.fillText("-R", centerX + 10, centerX + scale * R);
    ctx.fillText("-R/2", centerX + 10, centerX + scale/2 *R);
    ctx.fillText("R/2", centerX + 10, centerX - scale/2 *R);
    ctx.fillText("R", centerX + 10, centerX - scale * R);
    ctx.fillText("y", centerX + 10, 10);
}

function drawPoints(x, y, r) {
    const canvas = document.getElementById("Canv");
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
}

function submitData() {
    const yText = document.getElementById('Y').value;
    let num = new Big(yText);
    let yValue =  new Big(0);
    if (!/^-?\d+(\.\d+)?$/.test(yText)|| num.lt(-3) || num.gt(5)){
        console.log(yText);
        console.log(new Big(yText));
        alert("Please enter a valid Y coordinate between -3 and 5.");
        console.warn("Invalid Y value:", yText);
        return false;
    } else {
        console.log(yText);
        console.log(new Big(yText));
        yValue = new Big((yText));
    }

    const rValue = Number(document.getElementById('R').value);
    let xValue = Number(document.getElementById('X').value);


    if (xValue === null || isNaN(yValue) || yValue < -3 || yValue > 5 || isNaN(rValue)) {
        alert("Please enter valid values for all fields.");
        return false;
    }

    const data = JSON.stringify({x: xValue, y: yValue, r: rValue});

    fetch('/fcgi-bin/server.jar', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: data
    })
        .then(response => response.json())
        .then(json => {
            const resultBody = document.getElementById('results');
            const newRow = document.createElement('tr');
            console.log(json);

            newRow.innerHTML = `
              <td>${xValue}</td>
              <td>${yValue}</td>
              <td>${rValue}</td>
              <td>${json.result !== undefined ? (json.result ? 'true' : 'false') : 'undefined'}</td>
              <td>${json.currentTime !== undefined ? json.currentTime : 'undefined'}</td>
              <td>${json.executionTime !== undefined ? json.executionTime : 'undefined'}</td>
          `;

            resultBody.appendChild(newRow);
            saveResponseToLocalStorage(json);

        })
        .catch(error => console.error('Error:', error));
    
        

    drawPoints(xValue, yValue, rValue);
}

function getResponsesFromLocalStorage() {
    let data = localStorage.getItem("data");
    if (data == null) {
        data = '[]';
    }
    const obj = JSON.parse(data);
    return Object.keys(obj).map((key) => obj[key]);
}

function saveResponseToLocalStorage(response) {
    let responses = getResponsesFromLocalStorage();
    responses.push(response);
    console.log('All responses:', responses);
    localStorage.setItem("data", JSON.stringify(responses));
}

function showResponse(response) {
    const resultBody = document.getElementById('results');
    const newRow = document.createElement('tr');

    newRow.innerHTML = `
        <td>${response.x}</td>
        <td>${response.y}</td>
        <td>${response.r}</td>
        <td>${response.result !== undefined ? (response.result ? 'true' : 'false') : 'undefined'}</td>
        <td>${response.currentTime !== undefined ? response.currentTime : 'undefined'}</td>
        <td>${response.executionTime !== undefined ? response.executionTime : 'undefined'}</td>
    `;

    resultBody.appendChild(newRow);
}

function init() {
    let data = getResponsesFromLocalStorage();
    for (let i = 0; i < data.length; i++) {
        console.log("Loaded from local storage:", data[i]);
        showResponse(data[i]);
    }
}


window.onload = function() {
    init();
    drawGraph();
};
