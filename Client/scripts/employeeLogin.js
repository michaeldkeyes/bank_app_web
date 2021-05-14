const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", (e) => {
  e.preventDefault();
  let formData = new FormData(e.currentTarget);
  loginEmployee(formData);
});

async function loginEmployee(formData) {
  formData = Object.fromEntries(formData.entries());
  console.log(formData);
  const url = `http://localhost:7000/customer/login`;

  try {
    const response = await fetch(url, {
      method: "POST",
      body: JSON.stringify(formData),
    });

    if (!response.ok) {
      const message = await response.json();
      document.getElementById("errorMessage").innerText = `${message}`;
      return;
    }

    const data = await response.json();
    localStorage.setItem("employee", JSON.stringify(data));
    window.location.replace("http://127.0.0.1:5500/public/employee.html");
  } catch (error) {
    console.error(error);
  }
}
