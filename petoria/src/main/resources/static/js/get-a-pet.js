let currentPage = 0;

const paginationTop = document.getElementById("pagination-top");
const paginationBottom = document.getElementById("pagination-bottom");

function renderPetCard(pet) {
  return `
    <h3>${pet.name}</h3>
    <img src="${pet.photoUrl}" alt="Photo of ${pet.name}">
    <p>${pet.description}</p>
    <p><strong>Price:</strong> $${pet.price ?? 'Free'}</p>
    <button onclick="location.href='/pet/${pet.id}'">View</button>
  `;
}

async function authFetch(url, options = {}) {
  const token = localStorage.getItem("token");
  options.headers = options.headers || {};
  if (token) {
    options.headers["Authorization"] = "Bearer " + token;
  }

  const res = await fetch(url, options);

  if (res.status === 401 || res.status === 403) {
    localStorage.removeItem("token");
    window.location.href = "/";

    setTimeout(() => {
      const popup = document.getElementById("auth-popup");
      if (popup) popup.style.display = "flex";
    }, 300);

    throw new Error("Unauthorized");
  }

  return res;
}



function openModal() {
    document.getElementById("modal").style.display = "flex";
}

function closeModal() {
  document.getElementById("modal").style.display = "none";
  document.getElementById("addPetForm").reset();
  document.getElementById("urlError").style.display = "none";
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
      headers: { "Content-Type": "application/json" },
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
    const pageData = await res.json();

    const list = document.getElementById("petList");
    list.innerHTML = "";

    // 👇 If no pets AND not first page, go back and retry
    if (pageData.content.length === 0 && currentPage > 0) {
        currentPage = 0;
        return loadPage(0);
    }

    // 👇 If no pets AND first page, show message
    if (pageData.content.length === 0) {
        list.innerHTML = "<p>No pets found.</p>";
        renderPagination(0);
        return;
    }

    pageData.content.forEach(p => {
        const card = document.createElement("div");
        card.className = "pet-card";
        card.innerHTML = renderPetCard(p);
        list.appendChild(card);
    });

    renderPagination(pageData.totalPages);
}


function renderPagination(totalPages) {
    const containers = [paginationTop, paginationBottom];
    containers.forEach(container => container.innerHTML = "");

    if (!totalPages || totalPages <= 1) return;

    containers.forEach(container => {
        const prevBtn = document.createElement("button");
        prevBtn.textContent = "← Previous";
        prevBtn.disabled = currentPage === 0;
        prevBtn.onclick = () => loadPage(-1);

        const nextBtn = document.createElement("button");
        nextBtn.textContent = "Next →";
        nextBtn.disabled = currentPage >= totalPages - 1;
        nextBtn.onclick = () => loadPage(1);

        container.appendChild(prevBtn);
        container.appendChild(nextBtn);
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