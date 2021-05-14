function registerCustomer(e) {
  e.preventDefault();
  let formData = new FormData(e.currentTarget);
  formData = Object.fromEntries(formData.entries());

  if (validatePasswords(formData)) {
    const { username, password } = formData;
    formData = {
      username,
      password,
      type: "Customer",
    };
    sendData(formData);
  } else {
    document.getElementById("successMsg").innerText = "Passwords Must Match!";
  }
}

function validatePasswords(plainFormData) {
  if (plainFormData.password === plainFormData.confirmPassword) {
    return true;
  }

  return false;
}

async function sendData(formData) {
  try {
    const response = await fetch("http://localhost:7000/customer", {
      method: "POST",
      body: JSON.stringify(formData),
    });

    if (!response.ok) {
      console.log(response.status + " " + response);
      return;
    }

    const data = await response.json();
    document.getElementById("successMsg").innerHTML = data;
  } catch (error) {
    console.error(error);
  }
}

export default registerCustomer;
