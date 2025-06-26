document.querySelector("form").addEventListener("submit", async (e) => {
  e.preventDefault();

  const usernameInput = document.getElementById("username");
  const passwordInput = document.getElementById("password");

  if (!usernameInput.value || !passwordInput.value) {
    alert("Please fill in both fields.");
    return;
  }

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
    showFlash("Login successful!");
    localStorage.setItem("token", token);
    setTimeout(() => window.location.href = "/", 1500);

  } else {
    const error = await res.text();
    showFlash("Login failed: " + error, "error");
    passwordInput.value = "";
    passwordInput.focus();
  }

});

function togglePassword(id, button) {
const input = document.getElementById(id);
const isVisible = input.type === "text";
input.type = isVisible ? "password" : "text";
button.textContent = isVisible ? "🙈" : "👁️";
}

function showFlash(message, type = "success") {
  const box = document.getElementById("flashMessage");
  box.textContent = message;
  box.className = `flash-message ${type}`;
  box.style.display = "block";
  setTimeout(() => {
    box.style.display = "none";
  }, 1400);
}

