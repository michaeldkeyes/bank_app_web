async function loginCustomer(e) {
  e.preventDefault();

  let formData = new FormData(e.currentTarget);
  formData = Object.fromEntries(formData.entries());
  const url = `http://localhost:7000/customer/login`;

  try {
    const response = await fetch(url, {
      method: "POST",
      body: JSON.stringify(formData),
    });

    if (!response.ok) {
      const message = await response.json();
      document.getElementById("errorMsg").innerText = `${message}`;
      return;
    }

    const data = await response.json();
    localStorage.setItem("user", JSON.stringify(data));
    window.location.replace("http://127.0.0.1:5500/public/customer.html");
  } catch (error) {
    console.error(error);
  }
}

export default loginCustomer;
