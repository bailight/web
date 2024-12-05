<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="web.data.Result"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>lab2</title>
  <link rel="stylesheet" href="style.css">
  <%List<Result> results = (List<Result>) request.getAttribute("results");%>
  <script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
      drawGraph(1);
      draw();

    });

    function draw() {
      <% if (results != null && results.size()>0) {
          for (Result result : results) {
      %>
      drawPoints(<%=result.x%>, <%=result.y%>);
      <% }} %>
    }

    function drawGraph(value) {
      const R = value;
      const canvas = document.getElementById('Canv');
      const ctx = Canv.getContext('2d');

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
      ctx.fillRect(centerX - scale * R, centerY, scale *  R, scale * R/2);

      ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.2)');
      ctx.moveTo(centerY, centerX);
      ctx.arc(centerY, centerX, scale * R, 0, 0.5 * Math.PI, false);
      ctx.lineTo(centerX, centerY);
      ctx.fill();

      ctx.beginPath();
      ctx.fillStyle = createGradient(ctx, canvas, 'rgba(0, 0, 0, 0.3)');
      ctx.moveTo(centerY, centerX);
      ctx.lineTo(centerY, centerX - scale * R/2);
      ctx.lineTo(centerY - scale * R/2, centerX);
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

    function drawPoints(x, y) {
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

    let rValue = 1;

    function setR(value) {
      rValue = Number(value);
      drawGraph(rValue);
      console.log(rValue);
      draw();
    }

    function submitData() {
      const yText = document.getElementById('y').value;
      let X = document.getElementById('x').value;
      let R = rValue;
      console.log(R + " is R");
      let num = yText;
      let yValue = 0;
      if (!/^-?\d+(\.\d+)?$/.test(yText) || num <-3 || num > 5){
        alert("Please enter a valid Y coordinate between -3 and 3.");
        console.warn("Invalid Y value:", yText);
        return false;
      } else {
        console.log(yText + " is Y");
        yValue = yText;
      }

      const xValues = document.querySelectorAll('.x:checked')

      if (xValues.length === 0) {
        alert("X ещё не выбран");
        return false;
      } else if(xValues.length > 1){
        alert("Х только можно выбрать 1");
        return false
      } else {
        X = xValues[0].value;
        console.log(X + " is X");
      }

      if (isNaN(yValue) || yValue < -3 || yValue > 3 || isNaN(R)) {
        alert("Please enter valid values for all fields.");
        return false;
      }

      document.getElementById('x').value = X;
      document.getElementById('y').value = yValue;
      document.getElementById('r').value = R;

      return true;
    }

    window.onload = function() {
      draw();

      document.getElementById("Canv").addEventListener("click", function(event){
        const rect = document.getElementById("Canv").getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        let xCord = (x - 200) / 50;
        let yCord = (200 - y) / 50;
        document.getElementById('x').value = xCord.toFixed(2);
        document.getElementById('y').value = yCord.toFixed(2);
        document.getElementById('r').value = rValue;
        document.forms["point-form"].submit();
        drawPoints(xCord, yCord);
      });
    };
  </script>
</head>
<body>
<header>
  <div id="student-info" class="container">
    <h1>Чэнь Жохань P3222 413107</h1>
  </div>
</header>
<main>
  <div class="container">
    <div id="graph" class="card">
      <canvas id="Canv" width="400" height="400"></canvas>
    </div>
    <div id="point" class="card">
      <form id="point-form" action="Ctrl" method="get" onsubmit="return submitData();">
        <table>
          <tbody>
          <th class="input-info">
            <label>X: </label>
            <input type="hidden" id="x" name="x" value="">
            <input type="checkbox" class="x" value="-3"/><span>-3</span>
            <input type="checkbox" class="x" value="-2"/><span>-2</span>
            <input type="checkbox" class="x" value="-1"/><span>-1</span>
            <input type="checkbox" class="x" value="0"/><span>0</span>
            <input type="checkbox" class="x" value="1"/><span>1</span>
            <input type="checkbox" class="x" value="2"/><span>2</span>
            <input type="checkbox" class="x" value="3"/><span>3</span>
            <input type="checkbox" class="x" value="4"/><span>4</span>
          </th>
          <th class="input-info">
            <label for="y">Y: </label>
            <input type="text" id="y" name="y" placeholder="от -3 до 3">
          </th>
          <th class="input-info">
            <label>R: </label>
            <input type="hidden" id="r" name="r" value="">
            <input type="button" value="1" onclick="setR('1')"/>
            <input type="button" value="2" onclick="setR('2')"/>
            <input type="button" value="3" onclick="setR('3')"/>
            <input type="button" value="4" onclick="setR('4')"/>
            <input type="button" value="5" onclick="setR('5')"/>
          </th>
          <th>
            <input type="submit" value="check">
          </th>
          </tbody>
        </table>
      </form>
      <div class="card">
        <table id="resultTable" style="overflow-y:scroll">
          <thead>
          <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Result</th>
            <th>Curtime</th>
            <th>Execution time</th>
          </tr>
          </thead>
          <tbody id="results">
          <%
            if (results != null && results.size()>0) {
              for (Result result : results) {
          %>
            <tr>
              <td><%= result.x %></td>
              <td><%= result.y %></td>
              <td><%= result.r %></td>
              <td><%= result.check %></td>
              <td><%= result.timeNow %></td>
              <td><%= result.executionTime %></td>
            </tr>
          <%
            }
          } else {
          %>
            <tr><td colspan="6" >Ещё нет результатов</td></tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</main>
</body>
</html>
