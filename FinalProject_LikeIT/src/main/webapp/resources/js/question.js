function generateJRate(a, b) {
    $("#rating-" + a).jRate({
        strokeColor: 'black',
        height: 20,
        width: 23,
        precision: 1,
        min: 0,
        max: 10,
        rating: b,
        onChange: function (n) {
            $("#rating-" + a + "-value").text(n);
        },
        onSet: function (n) {
            $("#rating-c-form-" + a + " input[name='mark']").val(n);
            var dataString = $("#rating-c-form-" + a).serialize();

            $.ajax({
                type: "POST",
                url: "/LikeIT/Home",
                data: dataString,
                dataType: "json"
                //if received a response from the server
                //success: function( data, textStatus, jqXHR) {
                //    //our country code was correct so we have some information to display
                //    if(data.success){
                //        alert('success');
                //    }
                //    //display error message
                //    else {
                //        alert("fuck")
                //    }
                //},
                ////If there was no resonse from the server
                //error: function(jqXHR, textStatus, errorThrown){
                //    alert("Something really bad happened " + textStatus);
                //}
            });
        }
    });

    $("#rating-" + a + "-value").text(b);
}

function generateQuestionJRate(a, b) {
    $("#rating-q-" + a).jRate({
        strokeColor: 'black',
        height: 20,
        width: 23,
        precision: 1,
        min: 0,
        max: 10,
        rating: b,
        startColor: 'yellow',
        endColor: 'orange',
        onChange: function (n) {
            $("#rating-q-" + a + "-value").text(n);
        },
        onSet: function (n) {
            $("#rating-q-form-" + a + " input[name='mark']").val(n);
            var dataString = $("#rating-q-form-" + a).serialize();

            $.ajax({
                type: "POST",
                url: "/LikeIT/Home",
                data: dataString,
                dataType: "json"
                //if received a response from the server
                //success: function( data, textStatus, jqXHR) {
                //    //our country code was correct so we have some information to display
                //    if(data.success){
                //        alert('success');
                //    }
                //    //display error message
                //    else {
                //        alert("fuck")
                //    }
                //},
                ////If there was no resonse from the server
                //error: function(jqXHR, textStatus, errorThrown){
                //   alert("Something really bad happened " + textStatus);
                //}
            });
        }
    });

    $("#rating-q-" + a + "-value").text(b);
}

$(document).ready(function () {
    $('[data-tool="tooltip"]').tooltip();

    $("form[id^='rating-q-form-']").submit(function (e) {
        e.preventDefault();
    });

    $("a[id^='answer-c-']").click(function () {
        var note = $(this).attr('data-answer');

        if (note == 'true') {
            $(this).removeClass('answer-mark-on');
            $(this).addClass('answer-mark-off');
        } else {
            $(this).removeClass('answer-mark-off');
            $(this).addClass('answer-mark-on');
        }

        var comment = $(this).attr('data-comment');
        var dataString = $("#answer-c-form-" + comment).serialize();

        $.ajax({
            type: "POST",
            url: "/LikeIT/Home",
            data: dataString,
            dataType: "json"
            //if received a response from the server
            //success: function( data, textStatus, jqXHR) {
            //    //our country code was correct so we have some information to display
            //    if(data.success){
            //        alert('success');
            //    }
            //    //display error message
            //    else {
            //        alert("fuck")
            //    }
            //},
            ////If there was no resonse from the server
            //error: function(jqXHR, textStatus, errorThrown){
            //   alert("Something really bad happened " + textStatus);
            //}
        });

        if (note == 'true') {
            note = 'false';
        } else {
            note = 'true';
        }

        $(this).attr('data-answer', note);
        $('#answer-c-form-' + comment + ' input[name="answer"]').val(note);
        return false;
    });

    $('#delete-comment').on('show.bs.modal', function (e) {
        $comment = $(e.relatedTarget).attr('data-comment');
        $(this).find('.modal-footer #delete-comment-button').attr('data-comment', $comment);
    });

    $('#delete-comment').find('.modal-footer #delete-comment-button').on('click', function () {
        document.forms['form-delete-' + $(this).attr('data-comment')].submit();
    });

    $('#restore-comment').on('show.bs.modal', function (e) {
        $commentId = $(e.relatedTarget).attr('data-comment');
        $(this).find('.modal-footer #restore-comment-button').attr('data-comment', $commentId);
    });

    $('#restore-comment').find('.modal-footer #restore-comment-button').on('click', function () {
        document.forms['form-restore-' + $(this).attr('data-comment')].submit();
    });
});