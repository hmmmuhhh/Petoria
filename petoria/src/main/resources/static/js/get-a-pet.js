//let currentPage = 0;
//
//const paginationTop = document.getElementById("pagination-top");
//const paginationBottom = document.getElementById("pagination-bottom");
//
//function renderPetCard(pet) {
//  return `
//    <h3>${pet.name}</h3>
//    <img src="${pet.photoUrl}" alt="Photo of ${pet.name}">
//    <p>${pet.description}</p>
//    <p><strong>Price:</strong> $${pet.price ?? 'Free'}</p>
//    <button onclick="location.href='/pet/${pet.id}'" class="view-btn">View</button>
//  `;
//}
//
//function openModal() {
//    document.getElementById("modal").style.display = "flex";
//}
//
//function closeModal() {
//  document.getElementById("modal").style.display = "none";
//  document.getElementById("addPetForm").reset();
//  document.getElementById("urlError").style.display = "none";
//}
//
//function openSection(section) {
//    const token = localStorage.getItem("token");
//    if (!token) {
//        const popup = document.getElementById("auth-popup");
//        if (popup) popup.style.display = "flex";
//        return;
//    }
//
//    window.location.href = `/${section}`;
//}
//
//document.addEventListener("click", (e) => {
//  const popup = document.getElementById("auth-popup");
//  if (popup && e.target === popup) {
//    popup.style.display = "none";
//  }
//});
//
//document.getElementById("addPetForm").addEventListener("submit", async (e) => {
//    e.preventDefault();
//    const form = e.target;
//    const errorMsg = document.getElementById("urlError");
//    const photoInput = document.getElementById("photoUrl");
//
//    const data = {
//        name: form.name.value,
//        price: parseFloat(form.price.value),
//        description: form.description.value,
//        photoUrl: photoInput.value,
//        type: form.type.value
//    };
//
//    const isValid = await validateImageURL(data.photoUrl);
//    if (!isValid) {
//        errorMsg.style.display = "inline";
//        photoInput.value = "";
//        photoInput.focus();
//        return;
//    }
//
//    errorMsg.style.display = "none";
//    await authFetch("/api/pets", {
//      method: "POST",
//      headers: { "Content-Type": "application/json" },
//      body: JSON.stringify(data)
//    });
//
//    form.reset();
//    closeModal();
//    loadPage(0);
//});
//
//async function validateImageURL(url) {
//    return new Promise((resolve) => {
//        const img = new Image();
//        img.onload = () => resolve(true);
//        img.onerror = () => resolve(false);
//        img.src = url;
//    });
//}
//
//async function loadPage(offset) {
//    const sortValue = document.getElementById("sortFilter")?.value || "newest";
//
//    if (offset === 0) {
//        currentPage = 0;
//    } else {
//        currentPage += offset;
//        if (currentPage < 0) currentPage = 0;
//    }
//
//    const res = await authFetch(`/api/pets?page=${currentPage}&sort=${sortValue}`);
//    const pageData = await res.json();
//
//    const list = document.getElementById("petList");
//    list.innerHTML = "";
//
//    if (pageData.content.length === 0 && currentPage > 0) {
//        currentPage = 0;
//        return loadPage(0);
//    }
//
//    if (pageData.content.length === 0) {
//        list.innerHTML = "<p>No pets found.</p>";
//        renderPagination(0);
//        return;
//    }
//
//    pageData.content.forEach(p => {
//        const card = document.createElement("div");
//        card.className = "card";
//        card.innerHTML = renderPetCard(p);
//        list.appendChild(card);
//    });
//
//    renderPagination(pageData.totalPages);
//}
//
//
//function renderPagination(totalPages) {
//    const containers = [paginationTop, paginationBottom];
//    containers.forEach(container => container.innerHTML = "");
//
//    if (!totalPages || totalPages <= 1) return;
//
//    containers.forEach(container => {
//        const prevBtn = document.createElement("button");
//        prevBtn.textContent = "← Previous";
//        prevBtn.disabled = currentPage === 0;
//        prevBtn.onclick = () => loadPage(-1);
//
//        const nextBtn = document.createElement("button");
//        nextBtn.textContent = "Next →";
//        nextBtn.disabled = currentPage >= totalPages - 1;
//        nextBtn.onclick = () => loadPage(1);
//
//        container.appendChild(prevBtn);
//        container.appendChild(nextBtn);
//    });
//}
//
//function applySort() {
//    const selected = document.getElementById("sortFilter").value;
//    currentSort = selected;
//    currentPage = 0;
//    loadPage(0);
//}
//
//document.addEventListener("DOMContentLoaded", () => {
//    loadPage(0);
//});

document.addEventListener("DOMContentLoaded", () => {
    loadPage(0);

    document.getElementById("addPetForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData();

        formData.append("name", form.name.value.trim());
        formData.append("type", form.type.value);
        formData.append("description", form.description.value.trim());
        formData.append("price", form.price.value);

        const photos = form.querySelector('input[type="file"]').files;
        for (const photo of photos) {
            formData.append("photos", photo);
        }

        try {
            const res = await authFetch("/api/pets", {
                method: "POST",
                body: formData
            });

            if (!res.ok) {
                throw new Error("Failed to add pet");
            }

            form.reset();
            closeModal();
            loadPage(0);
        } catch (err) {
            showFormError("Something went wrong. Please try again.");
        }
    });

});

function showFormError(message) {
    const errorElem = document.getElementById("formError");
    errorElem.textContent = message;
    errorElem.style.display = "block";
}

function renderPetCard(pet) {
    const isSold = pet.sold;
    const isOwner = pet.isOwner;

    const label = isSold ? `<span class="label sold">SOLD</span>` : "";
    const checkbox = isOwner ? `
        <label class="toggle-label">
            <input type="checkbox" ${isSold ? "checked" : ""} onchange="toggleSold(${pet.id}, this.checked)"> Mark as Sold
        </label>` : "";

    const disabled = isSold ? "disabled class='view-btn sold'" : "class='view-btn'";

    return `
        <div class="card">
            <div class="card-header">
                <img src="${pet.posterProfilePicUrl}" class="profile-pic">
                <strong>${pet.posterUsername}</strong>
                ${label}
            </div>
            <img src="${pet.photoPaths?.[0] || "/img/default.png"}" alt="${pet.name}">
            <h3>${pet.name}</h3>
            <p>${pet.description}</p>
            <p><strong>Price:</strong> ${pet.price != null ? `$${pet.price}` : "Free"}</p>
            <p>${pet.description ?? ""}</p>
            ${checkbox}
            <button onclick="location.href='/pet/${pet.id}'" ${disabled}>View</button>
        </div>`;
}

function toggleSold(petId, isChecked) {
    const url = `/api/pets/${petId}/mark-sold`;
    authFetch(url, { method: "PUT" }).then(() => loadPage(0));
}

function openModal() {
    document.getElementById("modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
    document.getElementById("addPetForm").reset();
    document.getElementById("formError").style.display = "none";
}

function applySort() {
    currentPage = 0;
    loadPage(0);
}

let currentPage = 0;
function loadPage(offset) {
    const sortValue = document.getElementById("sortFilter")?.value || "newest";
    if (offset === 0) currentPage = 0;
    else {
        currentPage += offset;
        if (currentPage < 0) currentPage = 0;
    }

    authFetch(`/api/pets?page=${currentPage}&sort=${sortValue}`)
        .then(res => res.json())
        .then(pageData => {
            const list = document.getElementById("petList");
            list.innerHTML = "";

            if (pageData.content.length === 0 && currentPage > 0) return loadPage(0);
            if (pageData.content.length === 0) {
                list.innerHTML = "<p>No pets found.</p>";
                return renderPagination(0);
            }

            pageData.content.forEach(pet => {
                list.innerHTML += renderPetCard(pet);
            });

            renderPagination(pageData.totalPages);
        });
}

function renderPagination(totalPages) {
    const containers = ["pagination-top", "pagination-bottom"];
    containers.forEach(id => {
        const container = document.getElementById(id);
        container.innerHTML = "";

        if (totalPages <= 1) return;

        const prev = document.createElement("button");
        prev.textContent = "← Previous";
        prev.disabled = currentPage === 0;
        prev.onclick = () => loadPage(-1);

        const next = document.createElement("button");
        next.textContent = "Next →";
        next.disabled = currentPage >= totalPages - 1;
        next.onclick = () => loadPage(1);

        container.appendChild(prev);
        container.appendChild(next);
    });
}

document.addEventListener("click", (e) => {
    const popup = document.getElementById("auth-popup");
    if (popup && e.target === popup) popup.style.display = "none";
});
