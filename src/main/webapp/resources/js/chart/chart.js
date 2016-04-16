$(document).ready(function() {

    google.charts.load('current', {'packages':['line']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {

        $('.measurement-results-chart').each(function() {
            var actualElement = $(this);

            var options = {
                colors: '#0094e5',
                chart: {
                    title: actualElement.val()
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
            data.addColumn('number', 'Measurement Result');

            $.ajax({
                url: actualElement.data('url'),
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    var results = [];
                    $.each(response, function(key, value) {
                        var measurementResult = [value.value, value.date];
                        results.push([new Date(value.date), value.value]);
                    });

                    data.addRows(results);
                    var chart = new google.charts.Line(actualElement.get(0));
                    chart.draw(data, options);
                }
            });


        });
    }

    $('.datetimepicker-form').datepicker();

});