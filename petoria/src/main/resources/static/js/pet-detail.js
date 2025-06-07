document.addEventListener("DOMContentLoaded", async () => {
    const id = window.location.pathname.split("/").pop();
    const res = await fetch(`/api/pets/${id}`);
    const pet = await res.json();

    const div = document.getElementById("petInfo");
    div.innerHTML = `
        <h2>${pet.name}</h2>
        <img src="${pet.photoUrl}" width="250" height="250" style="object-fit:cover; border:1px solid #ccc;"><br>
        <p><strong>Price:</strong> $${pet.price ?? 'Free'}</p>
        <p>${pet.description}</p>
        <p>Submitted: ${pet.submissionTime}</p>
    `;
});
