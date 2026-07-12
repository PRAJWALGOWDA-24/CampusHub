const BASE_URL = window.location.origin;

async function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch(BASE_URL + "/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email,
            password
        })
    });

    if (!response.ok) {
        alert("Invalid email or password.");
        return;
    }

    const data = await response.json();

    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);

    window.location.href = "dashboard.html";
}