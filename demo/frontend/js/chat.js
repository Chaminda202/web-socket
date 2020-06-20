const url = 'http://localhost:8090';
let stompClient;
let selectedUser;

function connectToChat(userName) {
    console.log('Connecting to chat....');
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('connected to: ' + frame);
        stompClient.subscribe('/topic/message/' + userName, function(response) {
            let data = JSON.parse(response.body);
            console.log('Response ' + data);
            render(data.message, data.fromLogin);
        });
    });
}

function sendMsg(from, text) {
    stompClient.send('/app/chat/' + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}
/*
function register() {
    let userName = document.getElementById('userName').value;
    $.post(url + '/users', {
        username: userName,
        age: 18,
        occupation: 'Student',
        birthday: '1995-12-12'
    }, function(response) {
        connectToChat(userName);
    }).fail(function(error) {
        if (error.status == 404) {
            alert('User already logged');
        }
    });
}
*/
function register() {
    let userName = document.getElementById('userName').value;
    let payload = {
                  username: userName,
                  age: 18,
                  occupation: 'Student',
                  birthday: '1995-12-12'
                  };
    /*
    $.post(url + '/users', {
        username: userName,
        age: 18,
        occupation: 'Student',
        birthday: '1995-12-12'
    }, function(response) {
        connectToChat(userName);
    }).fail(function(error) {
        if (error.status == 404) {
            alert('User already logged');
        }
    });
    */
    $.ajax({
        type: 'POST',
        url: url + '/users',
        headers: {
           'Accept': 'application/json',
           'Content-Type': 'application/json'
        },
        data: JSON.stringify(payload),
        success : function(response) {
            connectToChat(userName);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
             alert('User already logged');
        }
    })
}
function selectUser(user){
    console.log(user);
    selectedUser = user;
    // $('#selectedUserId').html(selectedUser);
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + selectedUser);
}

function refreshAll() {
    $.get(url + '/users', function(response) {
        let users = response;
        console.log(JSON.stringify(users));
        let usersTemplateHTML = '';
        for(let i=0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i].username+ '\')"><li class="clearfix">\n' +
                            '                <img src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" width="55px" height="55px" alt="avatar" />\n' +
                            '                <div class="about">\n' +
                            '                    <div id="userNameAppender_' + users[i].username + '" class="name">' + users[i].username + '</div>\n' +
                            '                    <div class="status">\n' +
                            '                        <i class="fa fa-circle offline"></i>\n' +
                            '                    </div>\n' +
                            '                </div>\n' +
                            '            </li></a>';
        }
         $('#usersList').html(usersTemplateHTML);
    });
}


