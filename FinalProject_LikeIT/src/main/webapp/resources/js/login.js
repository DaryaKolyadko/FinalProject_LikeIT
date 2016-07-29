var loginPasswordPattern = '^[A-Za-z][_A-Za-z0-9-\.]*$';

$(document).ready(function () {
    $('#login-form').bootstrapValidator({
        framework: 'bootstrap',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userLogin: {
                validators: {
                    regexp: {
                        regexp: loginPasswordPattern,
                        message: 'Login is invalid'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: 'Login must be more than 3 and less than 20 characters long'
                    }
                }
            },
            userPassword: {
                validators: {
                    regexp: {
                        regexp: loginPasswordPattern,
                        message: 'Password is invalid'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        message: 'Password must be more than 3 and less than 30 characters long'
                    }
                }
            }
        }
    });
});