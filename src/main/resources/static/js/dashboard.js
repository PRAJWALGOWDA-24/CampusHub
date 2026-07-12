const role = localStorage.getItem("role");

window.onload = function(){

    if(role === "ROLE_ADMIN"){

        document.getElementById("welcomeMessage").innerHTML =
        " Welcome Admin";

    }
    else{

        document.getElementById("welcomeMessage").innerHTML =
        " Welcome User";

    }

}

function logout(){

    localStorage.removeItem("token");
    localStorage.removeItem("role");

    window.location.href="login.html";

}