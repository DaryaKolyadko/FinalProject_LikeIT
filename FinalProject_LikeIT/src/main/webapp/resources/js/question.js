function generateJRate(a, b) {
    $("#rating-" + a).jRate({
        strokeColor: 'black',
        height: 20,
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
}