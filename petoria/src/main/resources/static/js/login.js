document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;

    const data = {
        usernameOrEmail: form.usernameOrEmail.value,
        password: form.password.value
    };

    const res = await fetch("/api/users/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    if (res.ok) {
        alert("Logged in successfully!");
        window.location.href = "/"; // or home
    } else {
        alert("Invalid credentials.");
    }
});
