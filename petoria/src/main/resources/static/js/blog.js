document.addEventListener("DOMContentLoaded", () => {
    loadAllBlogPosts();
});

async function loadAllBlogPosts() {
    const container = document.getElementById("post-list");
    container.innerHTML = "";

    try {
        const res = await authFetch("api/blog");
        const posts = await res.json();

        posts.forEach(post => {
            const postBox = document.createElement("div");
            postBox.className = "blog-post";

            postBox.innerHTML = `
                <div class="post-header">
                    <img src="${post.profilePicture}" class="profile-pic" />
                    <strong>${post.username}</strong>
                    <small>${new Date(post.createdAt).toLocaleString()}</small>
                </div>
                <p>${post.content}</p>
                <div class="media-container">
                    ${post.imagePaths.map(path => `<img src="${path}" class="media-img">`).join("")}
                    ${post.videoPaths.map(path => `<video src="${path}" class="media-video" controls></video>`).join("")}
                </div>
                <button class="comment-toggle" onclick="toggleComments(${post.id})">
                    💬 View Comments
                </button>
                <div class="comments-section" id="comments-${post.id}" style="display:none;"></div>
                <div class="comment-input" id="comment-form-${post.id}" style="display:none;">
                  <form onsubmit="submitComment(event, ${post.id})" class="comment-form">
                      <input name="text" placeholder="Write a comment..." required>
                      <input name="imageUrl" placeholder="(optional image URL)">
                      <button type="submit">Post</button>
                  </form>
                </div>
            `;

            container.appendChild(postBox);
        });
    } catch (err) {
        console.error("Error loading blog posts:", err);
    }
}

//async function toggleComments(postId) {
//    const section = document.getElementById(`comments-${postId}`);
//    if (section.style.display === "none") {
//        await loadComments(postId);
//        section.style.display = "block";
//    } else {
//        section.style.display = "none";
//    }
//}

async function toggleComments(postId) {
    const commentsSection = document.getElementById(`comments-${postId}`);
    const formSection = document.getElementById(`comment-form-${postId}`);
    const isVisible = commentsSection.style.display === "block";

    if (!isVisible) {
        await loadComments(postId);
        commentsSection.style.display = "block";
        formSection.style.display = "block";
    } else {
        commentsSection.style.display = "none";
        formSection.style.display = "none";
    }
}


async function loadComments(postId) {
    const section = document.getElementById(`comments-${postId}`);
    section.innerHTML = "";

    try {
        const res = await authFetch(`api/blog/${postId}/comments`);
        const comments = await res.json();

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

            section.appendChild(div);
        });
    } catch (err) {
        console.error("Failed to load comments:", err);
    }
}

async function submitPost() {
    const text = document.getElementById("post-text").value;
    const files = document.getElementById("post-media").files;

    const formData = new FormData();
    formData.append("content", text);

    for (let file of files) {
        if (file.type.startsWith("image")) formData.append("images", file);
        else if (file.type.startsWith("video")) formData.append("videos", file);
    }

    try {
        await authFetch("api/blog", {
            method: "POST",
            body: formData
        });

        document.getElementById("post-text").value = "";
        document.getElementById("post-media").value = "";
        await loadAllBlogPosts(); // refresh
    } catch (err) {
        console.error("Failed to submit post:", err);
    }
}

async function submitComment(event, postId) {
    event.preventDefault();
    const form = event.target;

    const body = {
        text: form.text.value,
        imageUrl: form.imageUrl.value || null
    };

    try {
        await authFetch(`/api/blog/${postId}/comments`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        form.reset();
        await loadComments(postId);
    } catch (err) {
        console.error("Failed to post comment:", err);
    }
}
