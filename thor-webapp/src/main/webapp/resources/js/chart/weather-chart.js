$(document).ready(function() {

    google.charts.load('current', {'packages':['line']});
    google.charts.setOnLoadCallback(drawChart);

    function updateChart(response, element) {
        var options = {
            colors: '#0094e5',
            chart: {
                title: 'Weather Forecast',
                subtitle: 'Celsius'
            },
            curveType: 'function',
            explorer: {
                axis: 'horizontal',
                keepInBounds: true
            },
            trendlines: {
                0: {
                    type: 'polynomial',
                    degree: 3,
                    color: '#eeeeee',
                    lineWidth: 3,
                    opacity: 0.7,
                    showR2: true,
                    visibleInLegend: true
                }
            }
        };

        var data = new google.visualization.DataTable();

        data.addColumn('datetime', 'Date');
        data.addColumn('number', 'Temperature');

        var results = [];
        $.each(response, function(key, value) {
            results.push([new Date(value.date), value.temperature]);
        });

        data.addRows(results);
        var chart = new google.charts.Line(element.get(0));
        chart.draw(data, options);
    }

    function drawChart() {
        var actualElement = $('#weather-forecast-chart');

        $.ajax({
            url: '/weather/forecast/hourly',
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                updateChart(response, actualElement);
            }
        });
    }

});