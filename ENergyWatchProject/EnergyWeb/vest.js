/**
 * @fileoverview class for the west chart
 * uses google charts to make a line chart
 * uses pub/sub to get the data from the broker
 */

import brokerInstance from "./PubSub/main.js"; //import the broker instance from main.js

//loading the google charts and returning the chart using callback
google.charts.load('current',{packages:['corechart']});
google.charts.setOnLoadCallback(drawChart);image.png

//method for drawing the graph
//using pub/sub to get the data (payload) from the broker
function drawChart() {
brokerInstance.subscribe(payload => {
    if (payload.length == 0) {
        return;
    } else {
    payload.forEach(function (row) {
        if (row.area != "DK1") {
            return;
        } else {
        let data = google.visualization.arrayToDataTable([
            ['Time', 'Price'],
            [row.time, row.price]
        ]); 
    }       
    });
    }
    // Set Options
    var options = {
        title: 'Energi priser vestlige Danmark',
        hAxis: {title: 'Tidspunkt'},
        vAxis: {title: 'Pris'},
        legend: 'none'
    };
    // Draw
    var chart = new google.visualization.LineChart(document.getElementById('westChart'));
    chart.draw(data, options);

});
}

/*
// This is only for testing graph functionality purposes as the data is hardcoded
// Set Data
function drawChart() {
var data = google.visualization.arrayToDataTable([
  ['Time', 'Price'],
  [07,0.88],[08,0.60],[09,0.70],[10, 0.80],[11,1.90],
  [12,0.12],[13,0.20],[14,0.70],[15, 0.80],[16,1.90]
]);
// Set Options
var options = {
  title: 'Energi priser vestlige Danmark',
  hAxis: {title: 'Tidspunkt'},
  vAxis: {title: 'Pris'},
  legend: 'none'
};
// Draw
var chart = new google.visualization.LineChart(document.getElementById('westChart'));
chart.draw(data, options);
}*/

