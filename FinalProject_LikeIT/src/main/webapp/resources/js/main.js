function showPassword() {
    var key_attr = $('#user-password').attr('type');
    if (key_attr != 'text') {
        $('.checkbox').addClass('show');
        $('#user-password').attr('type', 'text');
    } else {
        $('.checkbox').removeClass('show');
        $('#user-password').attr('type', 'password');
    }
}

function selectMenuItem(itemId) {
    $(itemId).addClass('active');
}