$(document).ready(function () {
    $('[data-toggle=confirmation]').confirmation({
        onConfirm: function() {
            alert('Your choice: ok');
        },
        onCancel: function() {
            alert('You didn\'t choose');
        },
        btnOkLabel: 'Send',
        btnOkClass: 'btn btn-sm btn-success'
    });
});

function setEmailUnconfirmed(){
    $('#emailText').addClass('alert-danger');
    $('#emailText').attr('title', 'Email not confirmed');
}