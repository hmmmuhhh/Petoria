document.addEventListener("DOMContentLoaded", async () => {
  const id = window.location.pathname.split("/").pop();
  if (!id || isNaN(id)) {
    document.getElementById("petInfo").innerHTML = "<p>Invalid pet ID.</p>";
    return;
  }

  try {
    const res = await authFetch(`/api/pets/${id}`);
    if (!res.ok) throw new Error("Fetch failed");
    const pet = await res.json();

    document.getElementById("posterPic").src = pet.posterProfilePicUrl;
    document.getElementById("posterName").innerText = pet.posterUsername;
    document.getElementById("petName").innerText = pet.name;
    document.getElementById("petDescription").innerText = pet.description ?? "";
    document.getElementById("petType").innerText = pet.type;
    document.getElementById("petPrice").innerText = pet.price ?? "Free";

    const buyBtn = document.getElementById("buyBtn");
    if (pet.sold) {
      document.getElementById("soldLabel").style.display = "inline-block";
      buyBtn.disabled = true;
      buyBtn.style.opacity = "0.5";
      buyBtn.style.cursor = "not-allowed";
    }

    let current = 0;
    const photos = pet.photoPaths || [];
    const carouselImg = document.getElementById("carouselImg");
    if (photos.length > 0) carouselImg.src = photos[0];
    else carouselImg.src = "/img/default.png";

    document.getElementById("prevBtn").addEventListener("click", () => {
      current = (current - 1 + photos.length) % photos.length;
      carouselImg.src = photos[current];
    });

    document.getElementById("nextBtn").addEventListener("click", () => {
      current = (current + 1) % photos.length;
      carouselImg.src = photos[current];
    });

  } catch (err) {
    console.error("Error loading pet:", err);
    document.getElementById("petInfo").innerHTML = "<p style='color:red;'>Pet not found or an error occurred.</p>";
  }
});
