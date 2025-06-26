document.getElementById("signupForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const username = this.username.value;
  const email = this.email.value;
  const password = this.password.value;
  const birthday = this.birthday.value;

  let isValid = true;
  const clearErrors = () => document.querySelectorAll(".error").forEach(e => e.textContent = "");
  clearErrors();

  const usernameRegex = /^[\w.]{5,20}$/;
  if (!usernameRegex.test(username)) {
    document.getElementById("usernameError").textContent = "5–20 characters, use letters, numbers, _ or .";
    isValid = false;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    document.getElementById("emailError").textContent = "Invalid email format.";
    isValid = false;
  }

  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{5,}$/;
  if (!passwordRegex.test(password)) {
    document.getElementById("passwordError").textContent = "Min 5 chars, incl. uppercase, lowercase, number, symbol.";
    isValid = false;
  }

  const age = Math.floor((new Date() - new Date(birthday)) / (365.25 * 24 * 60 * 60 * 1000));
  if (age < 13) {
    document.getElementById("birthdayError").textContent = "You must be at least 13 years old.";
    isValid = false;
  }

  if (!isValid) return;

  const res = await fetch("/api/users/signup", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({ username, email, password, birthday })
  });

  const message = await res.text();
  console.log("Payload:", { username, email, password, birthday });

  if (!res.ok) {
    alert(message);
    document.getElementById("signupFailError").textContent = "Sign-up failed. Please try again.";

  } else {
  showFlash("Signup successful!");
  setTimeout(() => window.location.href = "/login", 1500);
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

