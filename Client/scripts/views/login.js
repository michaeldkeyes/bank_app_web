import elements from "../elements";

import customerPage from "./customer";

const loginPage = (content) => {
  content.innerHTML = "";

  const form = document.createElement("form");
  const div = document.createElement("div");
  const usernameLabel = elements.label();
  usernameLabel.innerText = "Username";
  usernameLabel.for = "username";
  const usernameInput = elements.input();
  usernameInput.type = "text";
  usernameInput.name = "username";
  usernameInput.placeholder = "Enter Username";
  usernameInput.required = true;
  const passwordLabel = elements.label();
  passwordLabel.innerText = "Password";
  passwordLabel.for = "password";
  const passwordInput = elements.input();
  passwordInput.type = "password";
  passwordInput.name = "password";
  passwordInput.placeholder = "Enter Password";
  passwordInput.required = true;
  const btn = elements.button();
  btn.type = "submit";
  btn.innerText = "Login";

  form.addEventListener("submit", (e) => {
    loginCustomer(e, content);
  });

  div.appendChild(usernameLabel);
  div.appendChild(usernameInput);
  div.appendChild(passwordLabel);
  div.appendChild(passwordInput);
  div.appendChild(btn);
  form.appendChild(div);
  content.appendChild(form);
};

async function loginCustomer(e, content) {
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
    console.log(data);
    customerPage(content, data);
  } catch (error) {
    console.error(error);
  }
}

export default loginPage;
