
console.log("login.js loaded");


async function login() {

    const email =
        document.getElementById("email").value;

    const password =
        document.getElementById("password").value;

const response = await fetch(
    "http://localhost:8080/auth/login",
    {
        method: "POST",
        headers: {
            "Content-Type":"application/json"
        },
        body: JSON.stringify({
            email,
            password
        })
    }
);

const data = await response.json();

console.log(data);

// Save JWT
localStorage.setItem("token", data.token);
localStorage.setItem("role", data.role);

console.log("Role :", data.role);

// Redirect
window.location.href = "dashboard.html";
}