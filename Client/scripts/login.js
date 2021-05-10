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
      console.log(message);
      return;
    }

    const data = await response.json();
    localStorage.setItem("user", JSON.stringify(data));
    const user = JSON.parse(localStorage.getItem("user"));
    console.log(user);
  } catch (error) {
    console.error(error);
  }
}

export default loginCustomer;
