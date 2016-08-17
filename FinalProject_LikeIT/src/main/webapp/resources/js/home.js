$(document).ready( function() {
    $(document).ready( function() {
        $('#hue-colorpicker').minicolors({
            defaultValue: '#000000',
            position: 'top left',
            changeDelay: 200,
            change: function(value, opacity) {
                alert(value);
                $('#hue-colorpicker').minicolors('hide', null);
            },
            theme: 'bootstrap'
        });
    });
});