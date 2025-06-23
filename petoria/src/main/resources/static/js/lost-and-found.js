let currentPage = 0;

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("addNoticeBtn").addEventListener("click", () => {
        document.getElementById("noticeModal").style.display = "block";
    });

    document.getElementById("addNoticeForm").addEventListener("submit", submitNotice);
    document.getElementById("typeFilter").addEventListener("change", () => loadPage(0));
    loadPage(0);
});

function closeModal() {
    document.getElementById("noticeModal").style.display = "none";
    document.getElementById("urlError").style.display = "none";
    document.getElementById("addNoticeForm").reset();
}

async function submitNotice(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    const errorMsg = document.getElementById("urlError");

    const res = await authFetch("/api/notices", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        closeModal();
        loadPage(0);
    } else {
        alert("Error adding notice.");
    }
}

async function loadPage(offset) {
    const type = document.getElementById("typeFilter")?.value || "";
    if (offset === 0) currentPage = 0;
    else {
        currentPage += offset;
        if (currentPage < 0) currentPage = 0;
    }

    const res = await authFetch(`/api/notices?page=${currentPage}&type=${type}`);
    const pageData = await res.json();

    const grid = document.getElementById("noticeGrid");
    grid.innerHTML = "";

    if (pageData.content.length === 0 && currentPage > 0) {
        currentPage = 0;
        loadPage(0);
        return;
    }

    pageData.content.forEach(n => {
        const card = document.createElement("div");
        card.className = "card";
        card.innerHTML = `
            <img src="${n.photoUrl}" alt="notice" />
            <h3>${n.title}</h3>
            <p>${n.description}</p>
            <p class="location">${n.location}</p>
            <span class="type-tag ${n.type.toLowerCase()}">${n.type}</span>
            <button onclick="window.location.href='/lost-and-found/${n.id}'">View</button>
        `;
        grid.appendChild(card);
    });

    renderPagination(pageData.totalPages);
}

function renderPagination(totalPages) {
    const top = document.getElementById("pagination-top");
    const bottom = document.getElementById("pagination-bottom");

    [top, bottom].forEach(container => {
        container.innerHTML = `
            <button onclick="loadPage(-1)">Previous</button>
            <span>Page ${currentPage + 1} of ${totalPages}</span>
            <button onclick="loadPage(1)">Next</button>
        `;
    });
}
