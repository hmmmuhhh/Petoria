// services.js
let currentPage = 0;
let currentType = "";

// DOM references
const orgGrid = document.getElementById("orgGrid");
const paginationTop = document.getElementById("pagination-top");
const paginationBottom = document.getElementById("pagination-bottom");
const modal = document.getElementById("orgModal");
const addOrgForm = document.getElementById("addOrgForm");

// ----- AUTH FETCH -----
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

// ----- LOAD PROVIDERS -----
async function loadPage(page) {
    currentPage = page;
    const typeParam = currentType ? `&type=${currentType}` : "";
    const res = await authFetch(`/api/services?page=${page}${typeParam}`);
    const data = await res.json();

    renderProviders(data.content);
    renderPagination(data.totalPages);
}

function formatType(type) {
    return type.replace(/_/g, " ").toLowerCase().replace(/\b\w/g, c => c.toUpperCase());
}

function renderProviders(providers) {
    orgGrid.innerHTML = "";
    if (providers.length === 0) {
        orgGrid.innerHTML = "<p>No providers found.</p>";
        return;
    }

    providers.forEach(p => {
        const card = document.createElement("div");
        card.className = "org-card";
        card.innerHTML = `
            <img src="${p.logoUrl}" alt="${p.name}" style="object-fit: contain; background: #f7f7f7;" />
            <h3>${p.name}</h3>
            <p>${p.description}</p>
            <p><strong>Location:</strong> ${p.location}</p>
            ${p.phone ? `<p><strong>Phone:</strong> ${p.phone}</p>` : ""}
            <div class="types">
              ${p.types.map(t => `<span class="type-badge">${formatType(t)}</span>`).join(" ")}
            </div>
            <a href="${p.websiteUrl}" target="_blank" class="website-btn">Visit Website</a>
        `;
        orgGrid.appendChild(card);
    });
}

function renderPagination(totalPages) {
    const containers = [paginationTop, paginationBottom];
    containers.forEach(container => container.innerHTML = "");

    if (!totalPages || totalPages <= 1) return;

    containers.forEach(container => {
        const prevBtn = document.createElement("button");
        prevBtn.textContent = "← Previous";
        prevBtn.disabled = currentPage === 0;
        prevBtn.onclick = () => loadPage(currentPage - 1);

        const nextBtn = document.createElement("button");
        nextBtn.textContent = "Next →";
        nextBtn.disabled = currentPage >= totalPages - 1;
        nextBtn.onclick = () => loadPage(currentPage + 1);

        container.appendChild(prevBtn);
        container.appendChild(nextBtn);
    });
}

// ----- FILTERING -----
document.getElementById("typeFilter").addEventListener("change", (e) => {
    currentType = e.target.value;
    loadPage(0);
});

// ----- MODAL HANDLING -----
document.getElementById("addOrgBtn").addEventListener("click", () => {
    modal.style.display = "flex";
});

function closeOrgModal() {
    modal.style.display = "none";
    addOrgForm.reset();
}

// ----- SUBMIT FORM -----
addOrgForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;
    const checkboxes = form.querySelectorAll("input[name='types']:checked");

    const data = {
        name: form.name.value,
        logoUrl: form.logoUrl.value,
        phone: form.phone.value,
        location: form.location.value,
        websiteUrl: form.websiteUrl.value,
        description: form.description.value,
        types: Array.from(checkboxes).map(cb => cb.value)
    };

    await authFetch("/api/services", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    closeOrgModal();
    loadPage(0);
});

// ----- INITIAL LOAD -----
document.addEventListener("DOMContentLoaded", () => {
    loadPage(0);
});
