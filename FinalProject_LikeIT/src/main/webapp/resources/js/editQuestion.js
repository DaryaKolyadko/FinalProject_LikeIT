$(document).ready(function () {
    $('.selectpicker').selectpicker();

    var topic = $('#topic').attr('data-new-value');

    if (topic) {
        $('#topic').val(topic);
        $('#topic').selectpicker('refresh');
        $('#topic').change();
    }


    $('#edit-question-form').bootstrapValidator({
        framework: 'bootstrap',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            sectionId: {
                validators: {
                    notEmpty: {
                        message: 'The section is required'
                    }
                }
            },
            title: {
                validators: {
                    notEmpty: {
                        message: 'Question title is required'
                    },
                    stringLength: {
                        max: 65,
                        message: 'Question title must be less or equals 65 characters long'
                    }
                }
            },
            text: {
                validators: {
                    notEmpty: {
                        message: 'Question text is required'
                    },
                    stringLength: {
                        max: 4000,
                        message: 'Question text must be less or equals 4000 characters long'
                    }
                }
            }
        }
    });
});