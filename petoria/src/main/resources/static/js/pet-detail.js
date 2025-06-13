document.addEventListener("DOMContentLoaded", async () => {
    const id = window.location.pathname.split("/").pop();
    if (!id || isNaN(id)) {
        document.getElementById("petInfo").innerHTML = "<p>Invalid pet ID.</p>";
        return;
    }

    try {
        const res = await fetch(`/api/pets/${id}`);
        if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
        const pet = await res.json();

        document.getElementById("petInfo").innerHTML = `
            <h2>${pet.name}</h2>
            <img src="${pet.photoUrl}" alt="Pet Photo">
            <p>${pet.type}</p>
            <p>${pet.description}</p>
            <p><strong>Price:</strong> $${pet.price ?? 'Free'}</p>
        `;
    } catch (err) {
        console.error("Error loading pet:", err);
        document.getElementById("petInfo").innerHTML = "<p style='color:red;'>Unable to load pet info.</p>";
    }
});
