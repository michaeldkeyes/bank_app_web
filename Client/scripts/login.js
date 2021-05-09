import elements from "./elements";

const loginPage = (content) => {
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
  const btn = elements.button();
  btn.innerText = "Login";

  form.addEventListener("submit", (e) => {
    loginCustomer(e);
  });

  form.appendChild(usernameLabel);
  form.appendChild(usernameInput);
  form.appendChild(passwordLabel);
  form.appendChild(passwordInput);
  form.appendChild(btn);
  content.appendChild(form);
};

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
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

export default loginPage;
