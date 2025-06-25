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

async function authFetch(url, options = {}) {
    const token = localStorage.getItem("token");
    options.headers = options.headers || {};
    if (token) {
        options.headers["Authorization"] = "Bearer " + token;
    }

    const res = await fetch(url, options);

    if (res.status === 401 || res.status === 403) {
        showPopup("Login required to access this page.");
        throw new Error("Unauthorized");
    }

    return res;
}

