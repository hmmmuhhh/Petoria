function showPopup(message) {
  const popup = document.getElementById("auth-popup");
  if (popup) {
    popup.querySelector(".popup-text").innerText = message;
    popup.style.display = "flex";
  }
}

function closePopup() {
  const popup = document.getElementById("auth-popup");
  if (popup) popup.style.display = "none";
}

document.addEventListener("DOMContentLoaded", async () => {

  const token = localStorage.getItem("token");
  const usernameDisplay = document.getElementById("username-display");
  const logoutBtn = document.getElementById("logoutBtn");
  const authButtons = document.getElementById("authButtons");
  const userInfo = document.getElementById("userInfo");

  if (!token) {
    if (authButtons) authButtons.style.display = "flex";
    if (userInfo) userInfo.style.display = "none";
  } else {
    try {
      const res = await fetch("/api/users/me", {
        headers: { Authorization: "Bearer " + token }
      });

      if (res.ok) {
        const username = await res.text();
        if (usernameDisplay) usernameDisplay.innerText = "Welcome, " + username + "!";
        if (userInfo) userInfo.style.display = "flex";
        if (authButtons) authButtons.style.display = "none";
      } else {
        console.error("Failed to fetch user info:", await res.text());
        localStorage.removeItem("token");
        if (authButtons) authButtons.style.display = "flex";
        if (userInfo) userInfo.style.display = "none";
      }
    } catch (err) {
      console.error("Error fetching username:", err);
      localStorage.removeItem("token");
      if (authButtons) authButtons.style.display = "flex";
      if (userInfo) userInfo.style.display = "none";
    }
  }


  document.querySelectorAll(".nav-link").forEach(link => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      const section = link.dataset.section;
      const token = localStorage.getItem("token");

      if (!token) {
        showPopup("You need to log in to access this page.");
      } else {
        window.location.href = `/${section}`;
      }
    });
  });

  window.logout = function () {
    localStorage.removeItem("token");
    location.href = "/";
  };
});
