let currentPage = 0;

async function authFetch(url, options = {}) {
  const token = localStorage.getItem("token");
  options.headers = options.headers || {};
  if (token) {
    options.headers["Authorization"] = "Bearer " + token;
  }

  const res = await fetch(url, options);

  if (res.status === 401 || res.status === 403) {
    const popup = document.getElementById("auth-popup");
    if (popup) popup.style.display = "flex";
    return Promise.reject("Unauthorized");
  }

  return res;
}


function openModal() {
    document.getElementById("modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
}

function openSection(section) {
    const token = localStorage.getItem("token");
    if (!token) {
        const popup = document.getElementById("auth-popup");
        if (popup) popup.style.display = "flex";
        return;
    }

    window.location.href = `/${section}`;
}


document.addEventListener("click", (e) => {
  const popup = document.getElementById("auth-popup");
  if (popup && e.target === popup) {
    popup.style.display = "none";
  }
});

document.getElementById("addPetForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;
    const errorMsg = document.getElementById("urlError");
    const photoInput = document.getElementById("photoUrl");

    const data = {
        name: form.name.value,
        price: parseFloat(form.price.value),
        description: form.description.value,
        photoUrl: photoInput.value,
        type: form.type.value
    };

    const isValid = await validateImageURL(data.photoUrl);
    if (!isValid) {
        errorMsg.style.display = "inline";
        photoInput.value = "";
        photoInput.focus();
        return;
    }

    errorMsg.style.display = "none";

    await authFetch("/api/pets", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    form.reset();
    closeModal();
    loadPage(0);
});

async function validateImageURL(url) {
    return new Promise((resolve) => {
        const img = new Image();
        img.onload = () => resolve(true);
        img.onerror = () => resolve(false);
        img.src = url;
    });
}

async function loadPage(offset) {
    const sortValue = document.getElementById("sortFilter")?.value || "newest";

    if (offset === 0) {
        currentPage = 0;
    } else {
        currentPage += offset;
        if (currentPage < 0) currentPage = 0;
    }

    const res = await authFetch(`/api/pets?page=${currentPage}&sort=${sortValue}`);
    const pets = await res.json();

    const list = document.getElementById("petList");
    list.innerHTML = "";

    if (pets.length === 0 && currentPage > 0) {
        currentPage = 0;
        loadPage(0);
        return;
    }

    pets.forEach(p => {
        const card = document.createElement("div");
        card.className = "pet-card";
        card.innerHTML = `
            <h3>${p.name}</h3>
            <img src="${p.photoUrl}" alt="Pet Photo">
            <p>${p.description}</p>
            <p><strong>Price:</strong> $${p.price ?? 'Free'}</p>
            <button onclick="location.href='/pet/${p.id}'">View</button>
        `;
        list.appendChild(card);
    });
}

function applySort() {
    const selected = document.getElementById("sortFilter").value;
    currentSort = selected;
    currentPage = 0;
    loadPage(0);
}

document.addEventListener("DOMContentLoaded", () => {
    loadPage(0);
});