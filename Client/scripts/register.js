import elements from "./elements";
import homePage from "./home";

const registerPage = (content) => {
  content.innerHTML = "";

  const form = elements.form();
  const usernameLabel = elements.label();
  usernameLabel.innerText = "Username";
  const usernameInput = elements.input();
  usernameInput.type = "text";
  usernameInput.name = "username";
  usernameInput.placeholder = "username";
  const passwordLabel = elements.label();
  passwordLabel.innerText = "Password";
  const passwordInput = elements.input();
  passwordInput.type = "password";
  passwordInput.name = "password";
  passwordInput.placeholder = "password";
  const confirmLabel = elements.label();
  confirmLabel.innerText = "Confirm Password";
  const confirmInput = elements.input();
  confirmInput.type = "password";
  confirmInput.name = "confirmPassword";
  confirmInput.placeholder = "confirm password";
  const submitBtn = document.createElement("button");
  submitBtn.type = "submit";
  submitBtn.innerText = "Register";

  form.addEventListener("submit", (e) => {
    registerCustomer(e);
    homePage(content);
  });

  form.appendChild(usernameLabel);
  form.appendChild(usernameInput);
  form.appendChild(passwordLabel);
  form.appendChild(passwordInput);
  form.appendChild(confirmLabel);
  form.appendChild(confirmInput);
  form.appendChild(submitBtn);

  content.appendChild(form);
};

function registerCustomer(e) {
  e.preventDefault();
  let formData = new FormData(e.currentTarget);
  formData = Object.fromEntries(formData.entries());

  if (validatePasswords(formData)) {
    const { username, password } = formData;
    formData = {
      username,
      password,
    };
    sendData(formData);
  } else {
    alert("Passwords do not match!");
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

    const data = await response;
    homePage(content);
  } catch (error) {
    console.error(error);
  }
}

export default registerPage;
