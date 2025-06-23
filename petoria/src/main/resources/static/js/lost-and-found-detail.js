let noticeId = window.location.pathname.split("/").pop();
let commentsVisible = false;

document.addEventListener("DOMContentLoaded", () => {
    loadNotice();
    loadComments();

    document.getElementById("comment-form").addEventListener("submit", async (e) => {
        e.preventDefault();
        const form = e.target;

        const data = {
            text: form.text.value,
            imageUrl: form.imageUrl.value || null
        };

        await authFetch(`/api/notices/${noticeId}/comments`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        form.reset();
        loadComments();
    });
});

async function loadNotice() {
    const res = await authFetch(`/api/notices/${noticeId}`);
    const n = await res.json();

    const box = document.getElementById("notice-detail");
    box.innerHTML = `
        <img src="${n.photoUrl}" alt="notice" />
        <h2>${n.title}</h2>
        <p>${n.description}</p>
        <p class="location">📍 ${n.location}</p>
        <span class="type-tag ${n.type.toLowerCase()}">${n.type}</span>
    `;
}

async function loadComments() {
    const res = await authFetch(`/api/notices/${noticeId}/comments`);
    if (!res.ok) {
      console.error("Unauthorized or forbidden:", res.status);
      return res;  // Return to allow frontend to react
    }

    const comments = await res.json();

    const list = document.getElementById("comment-list");
    list.innerHTML = "";

    comments.forEach(c => {
        const div = document.createElement("div");
        div.className = "comment";

        div.innerHTML = `
            <div class="comment-header">
                <img src="${c.authorProfilePicUrl}" class="profile-pic" />
                <strong>${c.authorUsername}</strong>
            </div>
            <p>${c.text}</p>
            ${c.imageUrl ? `<img src="${c.imageUrl}" class="comment-image" />` : ""}
            <small>${new Date(c.submissionTime).toLocaleString()}</small>
        `;
        list.appendChild(div);
    });
}

function toggleComments() {
    const section = document.getElementById("comments-section");
    const icon = document.getElementById("toggle-icon");
    commentsVisible = !commentsVisible;

    section.style.display = commentsVisible ? "block" : "none";
    icon.className = commentsVisible ? "fa fa-chevron-up" : "fa fa-chevron-down";
    document.getElementById("comment-toggle").querySelector("span").textContent =
        commentsVisible ? "Hide Comments" : "View Comments";
}
