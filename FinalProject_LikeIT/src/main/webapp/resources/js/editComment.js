$(document).ready(function () {
    $('#edit-comment-form').bootstrapValidator({
        framework: 'bootstrap',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            text: {
                validators: {
                    notEmpty: {
                        message: 'Comment text is required'
                    },
                    stringLength: {
                        max: 600,
                        message: 'Comment text must be less or equals 600 characters long'
                    }
                }
            }
        }
    });
});