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


document.addEventListener("DOMContentLoaded", async () => {
  const id = window.location.pathname.split("/").pop();
  if (!id || isNaN(id)) {
    document.getElementById("petInfo").innerHTML = "<p>Invalid pet ID.</p>";
    return;
  }

  try {
    const res = await authFetch(`/api/pets/${id}`);
    const pet = await res.json();

    document.getElementById("petInfo").innerHTML = `
      <h2>${pet.name}</h2>
      <img src="${pet.photoUrl}" alt="${pet.name}">
      <p>${pet.type}</p>
      <p>${pet.description}</p>
      <p><strong>Price:</strong> $${pet.price ?? 'Free'}</p>
    `;
  } catch (err) {
    console.error("Error loading pet:", err);
    document.getElementById("petInfo").innerHTML = "<p style='color:red;'>Pet not found or an error occurred.</p>";
  }
});