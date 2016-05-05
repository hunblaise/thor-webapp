$(document).ready(function() {

    google.charts.load('current', {'packages':['line', 'corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function updateLineChart(response, element) {
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

        var data = prepareLineData(response);

        var chart = new google.charts.Line(element.get(0));
        chart.draw(data, options);
    }

    function updateHistogram(response, element) {
        var options = {
            colors: ['#0094e5'],
            title: 'Detected Motions',
            legend: { position: 'none' },
            bar:    { groupWidth: '99%' },
            width: 900,
            height: 750,
            hAxis: {
                textPosition: 'none'
            }
        };

        var data = new google.visualization.DataTable();

        data.addColumn('datetime', 'Date');
        data.addColumn('number', 'Date');

        var results = [];
        $.each(response, function(key, value) {
            results.push([new Date(value.date), new Date(value.date).value]);
        });

        data.addRows(results);

        var chart = new google.visualization.Histogram(element.get(0));
        chart.draw(data, options);
    }

    function prepareLineData(response) {
        var data = new google.visualization.DataTable();

        data.addColumn('datetime', 'Date');
        data.addColumn('number', 'Result');

        var results = [];
        $.each(response, function(key, value) {
            results.push([new Date(value.date), value.value]);
        });

        data.addRows(results);

        return data;
    }

    function drawChart() {
        $('.measurement-results-chart').each(function() {
            var actualElement = $(this);

            $.ajax({
                url: actualElement.data('url'),
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    updateLineChart(response, actualElement);
                }
            });
        });

        $('#measurement-results-chart-motion').each(function() {
            var actualElement = $(this);

            $.ajax({
                url: actualElement.data('url'),
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    updateHistogram(response, actualElement);
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
                var source = $('#measurement-result-table').html();
                var template = Handlebars.compile(source);

                var data = new Object();
                data.measurements = response;

                $('#measurement-result-table-parent' + actualElement.data('sensor')).html(template(data));

                if (actualElement.data('sensor') == 'motion') {
                    updateHistogram(response, $('#measurement-results-chart-motion'));
                } else {
                    updateLineChart(response, $('#measurement-results-chart-' + actualElement.data('sensor')));
                }
            }
        });
    });

});