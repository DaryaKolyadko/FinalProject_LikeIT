var sectionNamePattern = '^.{1,30}$';
var hexColorPattern = '^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$';

$(document).ready(function () {
    $('.selectpicker').selectpicker();

    var value = "#000000";
    var newValue = $('#hue-colorpicker').attr('data-new-value');

    if (newValue) {
        value = newValue;
    }

    $('#hue-colorpicker').minicolors({
        position: 'top left',
        changeDelay: 200,
        theme: 'bootstrap'
    });

    $('#hue-colorpicker').minicolors('value', value);

    $('#topic').on('change', function () {
        topicHandler();
    });

    var topic = $('#topic').attr('data-new-value');

    if (topic) {
        $('#topic').val(topic);
        $('#topic').selectpicker('refresh');
        $('#topic').change();
    }

    $('#edit-section-form').bootstrapValidator({
        framework: 'bootstrap',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            sectionName: {
                validators: {
                    regexp: {
                        regexp: sectionNamePattern,
                        message: 'Section name is invalid'
                    },
                    notEmpty: {
                        message: 'Section name is required'
                    },
                    stringLength: {
                        max: 30,
                        message: 'Section name must be less or equals 30 characters long'
                    }
                }
            },
            labelColor: {
                validators: {
                    regexp: {
                        regexp: hexColorPattern,
                        message: 'Color hex code is invalid'
                    }
                }
            }
        }
    });
});