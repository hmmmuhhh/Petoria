let currentPage = 0;

function openModal() {
    document.getElementById("modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
}

document.getElementById("addPetForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;
    const errorMsg = document.getElementById("urlError");
    const photoInput = document.getElementById("photoUrl");

    const data = {
        name: form.name.value,
        price: parseFloat(form.price.value),
        description: form.description.value,
        photoUrl: photoInput.value
    };

    const isValid = await validateImageURL(data.photoUrl);
    if (!isValid) {
        errorMsg.style.display = "inline";
        photoInput.value = "";
        photoInput.focus();
        return;
    }

    errorMsg.style.display = "none";

    await fetch("/api/pets", {
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
    currentPage += offset;
    if (currentPage < 0) currentPage = 0;

    const res = await fetch(`/api/pets?page=${currentPage}`);
    const pets = await res.json();

    const list = document.getElementById("petList");
    list.innerHTML = "";

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
