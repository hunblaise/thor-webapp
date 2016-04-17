$(document).ready(function() {

    google.charts.load('current', {'packages':['line']});
    google.charts.setOnLoadCallback(drawChart);

    function updateChart(response, element) {
        var options = {
            colors: '#0094e5',
            chart: {
                title: element.val()
            },
            curveType: 'function',
            explorer: {
                axis: 'horizontal',
                keepInBounds: true
            },
            trendlines: {
                0: {
                    type: 'linear',
                    color: '#eeeeee',
                    lineWidth: 3,
                    opacity: 0.7,
                    showR2: true,
                    visibleInLegend: true
                }
            },
            width: 900,
            height: 750
        };

        var data = new google.visualization.DataTable();

        data.addColumn('datetime', 'Date');
        data.addColumn('number', 'Result');

        var results = [];
        $.each(response, function(key, value) {
            results.push([new Date(value.date), value.value]);
        });

        data.addRows(results);
        var chart = new google.charts.Line(element.get(0));
        chart.draw(data, options);
    }

    function drawChart() {
        $('.measurement-results-chart').each(function() {
            var actualElement = $(this);

            $.ajax({
                url: actualElement.data('url'),
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    updateChart(response, actualElement);
                }
            });
        });
    }

    $('.datetimepicker-start').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        defaultDate: moment().subtract(14, 'days')
    });

    $('.datetimepicker-end').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        defaultDate: moment()
    });

    $('.date-picker-form').submit(function(event) {
        event.preventDefault();

        var actualElement = $(this);

        $.ajax({
            url: actualElement.data('url'),
            type: 'GET',
            data: actualElement.serialize(),
            dataType: 'json',
            success: function(response) {
               updateChart(response, $('#measurement-results-chart-' + actualElement.data('sensor')));
            }
        });
    });

});