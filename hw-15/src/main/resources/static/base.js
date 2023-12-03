$('.action-button.put').click(function () {
    let data = [];
    $('.put-value').each(function () {
        for (let i = 0; i < $(this).find('input').eq(0).val(); i++) {
            data.push({
                nominal: $(this).find('label').eq(0).attr('mode')
            })
        }
    });
    console.log(data);
    console.log(JSON.stringify(data))
    $.ajax({
        method: 'POST',
        url: "/balance",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (result) {
            $('#put-money-status').text('Операция успешно выполнена');
            $('.container').html(result);
        },
        error: function (err) {
            $('#put-money-status').text(err.responseJSON.error);
        }
    });
});

$('.action-button.take').click(function () {

    let amount = $('#take-money').val();
    $.ajax({
        method: 'DELETE',
        url: "/balance?amount=" + amount,
        success: function (result) {
            $('.container').html(result);
            $('#take-money-status').text('Операция успешно выполнена');
        },
        error: function (err) {
            $('#take-money-status').text(err.responseJSON.error);
            console.log(err);
        }
    });
});