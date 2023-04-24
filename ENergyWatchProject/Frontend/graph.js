import brokerInstance from "./main";

<script src="https://www.gstatic.com/charts/loader.js"></script>
const graphcontainer = document.getElementById('chartcontainer');

//add event listener
graphcontainer.addEventListener('click', function() {
    init();
});

//Loads the chart from google charts api
function init() {
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
}

//Draws the chart
function drawChart() {
    brokerInstance.subscribe(payload => {
        //add all the chart code here if we go with one chart or move this function to sortData if we go with 2-in-1 chart

    });

    var chartdata = google.visualization.DataTable();
    chartdata.addColumn('string', 'Time');
    chartdata.addColumn('string', 'Vest Danmark');
    chartdata.addColumn('string', 'Øst Danmark');

    //change this to a for loop with the real data
    chartdata.addRows([
        ['2014', 1000],
        ['2015', 1170],
        ['2016', 660],
    ]);
    
    var options = {
        title: 'Spot Price',
        hAxis: {title: 'Date',  titleTextStyle: {color: '#333'}},
        vAxis: {title: 'Price', minValue: 0, maxValue: 1000},
        legend: { position: 'bottom' }
    };
    
    var chart = new google.visualization.LineChart(graphcontainer);
    
    chart.draw(chartdata, options);
    }

    //sorts the data input into Area1 (Vest Danmark) and Area2 (Øst Danmark)
function sortData(data) {
    let array = data;
    array.forEach(element => {
        if (element.region === 'DK1') {
            let vestDK = vestDK.push(element);
            return vestDK;
        }
        else if (element.region === 'DK2') {
            let ostDK = ostDK.push(element);
            return ostDK;
        }
    });
}
