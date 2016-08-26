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
            alert("change to " + n);
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
            alert("change to " + n);
        }
    });

    $("#rating-q-" + a + "-value").text(b);
}