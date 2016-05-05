$(document).ready(function() {
    $('.sensor-update-button').click(function() {
        var actualElement = $(this);

        $.ajax({
            url: actualElement.data('url'),
            type: 'GET',
            dataType: 'json',
            success: function(response) {

                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "newestOnTop": false,
                    "progressBar": false,
                    "positionClass": "toast-bottom-full-width",
                    "preventDuplicates": true,
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "5000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                };
                toastr["success"]("Successful update. Actual value: " + response.value);
            }
        });
    });
});