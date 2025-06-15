document.getElementById("signupForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;

    const data = {
        username: form.username.value,
        email: form.email.value,
        password: form.password.value,
        birthday: form.birthday.value
    };

    const res = await fetch("/api/users/signup", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    if (res.ok) {
        alert("Sign-up successful!");
        window.location.href = "/login";
    } else {
        alert("Sign-up failed.");
    }
});
