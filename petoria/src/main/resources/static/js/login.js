document.querySelector("form").addEventListener("submit", async (e) => {
  e.preventDefault();

  const usernameInput = document.getElementById("username");
  const passwordInput = document.getElementById("password");

  const res = await fetch("/api/users/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
    usernameOrEmail: usernameInput.value,
    password: passwordInput.value
    })
  });

  if (res.ok) {
    const token = await res.text();
    alert("Login successful!");
    localStorage.setItem("token", token);
    window.location.href = "/";

  } else {
    const error = await res.text();
    alert("Login failed: " + error);
  }
});
