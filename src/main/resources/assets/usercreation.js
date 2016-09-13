$(document).ready(function () {

    var currentUserName;
    var currentPassword;

    function onPlayerCreated() {
        $('#fighparent').show();
        currentUserName = $('#username').val();
        currentPassword = $('#password').val();

        $('#fightparent').removeClass('pre-fight');
        $('#fightparent').addClass('current-fight')

        $('#postplayercreation').removeClass('pre-fight');
        $('#postplayercreation').addClass('current-fight');
        $('#yournamehere').html(currentUserName);
        $('#createplayerparent').addClass('pre-fight');
    }

    function onGameFinished(data) {
        var list = "<li>" +  data.player_one_result +  ": Your move " + data.player_one_move.move + " their move " + data.player_two_move.move +  "</li>";
        $('#yourresultshere').append(list);
    }

    function playGameWithMove(moveType) {
        var jsonData = {username: currentUserName, password: currentPassword,
                            move: moveType };
        $.ajax({
            type: 'POST',
            url: '../game/playrandom/',
            contentType: 'application/json',
            data: JSON.stringify(jsonData)
        }).done(function(data) {
            onGameFinished(data)
        }).fail(function() {
            console.log('failed to play game');
        })
    }

    $('#rock').on('click', function() {
        playGameWithMove('Rock');
    });
    $('#paper').on('click', function() {
        playGameWithMove('Paper');
    });
    $('#Scissors').on('click', function() {
        playGameWithMove('Scissor');
    });

    $('#createplayerparent').submit(function (e) {
        e.preventDefault();
        //var formData = $('#createplayerparent').serializeArray();
        //var jsonData = JSON.parse(JSON.stringify(formData));
        var jsonData = {username: $('#username').val(), password: $('#password').val()};
        $.ajax({
            type: 'POST',
            url: '../player/',
            contentType: 'application/json',
            data: JSON.stringify(jsonData),
        }).done(
            // What happened to success jquery?
            onPlayerCreated()
        ).fail(function () {
            console.log("failed to create user")
        });

    });

    // TODO: replace with some sort of socket situation
    function updateHighScore() {
        clearInterval(interval);
        $.ajax({
            type: 'GET',
            url: '../game/highscore',
            contentType: 'application/json',
        }).done(function(data) {
            if (data !== undefined) {
                $('#topplayers').empty();
                data.forEach(function(entry){
                    $('#topplayers').append('<li>' + entry.username + ' ' + entry.wins  + '</li>');
                });

            }
        }).fail(function() {
            // possibly put in a backoff.
            console.log("failed getting hight score list");
        }).always(function() {
            interval = setInterval( updateHighScore , 5000);
        });
    }

    var interval = setInterval( updateHighScore , 5000);
    updateHighScore();

});


