var stringPattern = '^[A-Za-z \pL{А-я}]+$';
var loginPattern = '^[A-Za-z][_A-Za-z0-9-\.]{2,24}$';
var passwordPattern = '^[_A-Za-z0-9-\.]{3,25}$';
var emailPattern = '^[A-Za-z][_A-Za-z0-9-\.]*@([A-Za-z]+\.)[A-Za-z]{2,4}$';

$(document).ready(function () {
    $('#birthday-picker').datepicker({
        format: 'mm/dd/yyyy',
        autoclose: true,
        endDate: '0d',
        language: $('#birthday-picker').attr('data-locale')
    }).on('changeDate', function (e) {
        $('#sign-up-form').bootstrapValidator('revalidateField', 'birthDate');
    });


    $('#sign-up-form').bootstrapValidator({
        framework: 'bootstrap',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName: {
                validators: {
                    regexp: {
                        regexp: stringPattern,
                        message: 'First name is invalid'
                    },
                    stringLength: {
                        max: 25,
                        message: 'First name must be less than 25 characters long'
                    }
                }
            },
            lastName: {
                validators: {
                    regexp: {
                        regexp: stringPattern,
                        message: 'Last name is invalid'
                    },
                    stringLength: {
                        max: 25,
                        message: 'Last name must be less than 25 characters long'
                    }
                }
            },
            userLogin: {
                validators: {
                    regexp: {
                        regexp: loginPattern,
                        message: 'Login is invalid'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: 'Login must be more than 3 and less than 20 characters long'
                    }
                }
            },
            userEmail: {
                validators: {
                    regexp: {
                        regexp: emailPattern,
                        message: 'The input is not a valid email address'
                    }
                }
            },
            userPassword: {
                validators: {
                    regexp: {
                        regexp: passwordPattern,
                        message: 'Password is invalid'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        message: 'Password must be more than 3 and less than 30 characters long'
                    },
                    identical: {
                        field: 'userConfirmPassword',
                        message: 'The password and its confirm are not the same'
                    }
                }
            },
            userConfirmPassword: {
                validators: {
                    identical: {
                        field: 'userPassword',
                        message: 'The password and its confirm are not the same'
                    }
                }
            },
            gender: {
                validators: {
                    notEmpty: {
                        message: 'The gender is required'
                    }
                }
            },
            birthDate: {
                validators: {
                    notEmpty: {
                        message: 'The date is required'
                    },
                    date: {
                        format: 'MM/DD/YYYY',
                        message: 'The date is invalid'
                    }
                }
            }
        }
    });
});