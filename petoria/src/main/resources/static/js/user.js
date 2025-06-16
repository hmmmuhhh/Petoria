document.addEventListener("DOMContentLoaded", async () => {
  const token = localStorage.getItem("token");
  const usernameDisplay = document.getElementById("username-display");
  const logoutBtn = document.getElementById("logoutBtn");

  if (!token) {
    if (usernameDisplay) usernameDisplay.style.display = "none";
    if (logoutBtn) logoutBtn.style.display = "none";
  } else {
    try {
      const res = await fetch("/api/users/me", {
        headers: { Authorization: "Bearer " + token }
      });

      if (res.ok) {
        const username = await res.text();
        if (usernameDisplay) {
          usernameDisplay.innerText = "Welcome, " + username + "!";
          usernameDisplay.style.display = "inline";
        }
        if (logoutBtn) logoutBtn.style.display = "inline";
      } else {
        console.error("Failed to fetch user info:", await res.text());
        localStorage.removeItem("token");
      }
    } catch (err) {
      console.error("Error fetching username:", err);
      localStorage.removeItem("token");
    }
  }

  document.querySelectorAll(".nav-link").forEach(link => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      const section = link.dataset.section;
      const token = localStorage.getItem("token");

      if (!token) {
        const popup = document.getElementById("auth-popup");
        if (popup) popup.style.display = "flex";
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
