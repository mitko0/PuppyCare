function clicked() {
   var user=document.getElementById('username');
   var pass = document.getElementById('password');

   var coruser="test";
    var corpass="123";

    if(user.value==coruser)
    {
        if(pass.value==corpass)
        {
            window.alert("You are logged in!");
            window.open("http://www.facebook.com")
        }
        else
        {
            window.alert("Incorrect username or password");
        }
    }
    else
    {
        window.alert("Incorrect username or password");
    }
}

function registered() {
    var user=document.getElementById('username');
    var pass = document.getElementById('password');
    var mail = document.getElementById('email');

    var coruser="test";
    var corpass="123";
    var cormail="test@mail.com";

    if(user.value==coruser)
    {
        if(pass.value==corpass)
        {
            if (mail.value == cormail)
            {
                window.alert("You are registered!");
                window.open("http://facebook.com")
            }
        }
    }
}