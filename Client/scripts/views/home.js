import loginPage from "./login";

const homePage = (content) => {
  content.innerHTML = "";

  const gradientDiv = document.createElement("div");
  const div = document.createElement("div");
  const containerDiv = document.createElement("div");
  const rowDiv = document.createElement("div");
  gradientDiv.classList.add("gradient-background");
  div.classList.add("d-flex", "align-items-center", "height-85vh");
  containerDiv.classList.add("container", "mt-lg-8");
  rowDiv.classList.add("row", "justify-content-between", "align-items-center");

  // Text on the left of the screen
  const textDiv = document.createElement("div");
  const joinSpan = document.createElement("span");
  joinSpan.innerText = "join bank app";
  const h1 = document.createElement("h1");
  h1.innerText = "Banking service that works for you";
  textDiv.classList.add("col-lg-5", "mb-0");
  joinSpan.classList.add("d-block", "text-white", "text-uppercase", "mb-2");
  h1.classList.add("display-4", "text-white");

  // Register form
  const signupDiv = document.createElement("div");
  const formDiv = document.createElement("div");
  const form = document.createElement("form");
  const getStartedDiv = document.createElement("div");
  const h2 = document.createElement("h2");
  h2.innerText = "Sign up for online banking";
  signupDiv.classList.add("col-lg-5");
  formDiv.classList.add("bg-white", "shadow-sm", "rounded", "p-6");
  getStartedDiv.classList.add("mb-4");
  h2.classList.add("h4");

  // Username Input
  const formMessageDiv = document.createElement("div");
  const inputGroupDiv = document.createElement("div");
  const usernameInput = document.createElement("input");
  usernameInput.type = "text";
  usernameInput.name = "username";
  usernameInput.required = true;
  usernameInput.placeholder = "Enter your username";
  usernameInput.classList.add("form-control", "form__input");

  formMessageDiv.classList.add("js-form-message", "mb-3");
  inputGroupDiv.classList.add("js-focus-state", "input-group", "form");

  inputGroupDiv.appendChild(usernameInput);
  formMessageDiv.appendChild(inputGroupDiv);

  // Password Input
  const formMessageDiv2 = document.createElement("div");
  const inputGroupDiv2 = document.createElement("div");
  const passwordInput = document.createElement("input");
  passwordInput.type = "password";
  passwordInput.name = "password";
  passwordInput.required = true;
  passwordInput.placeholder = "Enter your password";
  passwordInput.classList.add("form-control", "form__input");

  formMessageDiv2.classList.add("js-form-message", "mb-3");
  inputGroupDiv2.classList.add("js-focus-state", "input-group", "form");

  inputGroupDiv2.appendChild(passwordInput);
  formMessageDiv2.appendChild(inputGroupDiv2);

  // Confirm password Input
  const formMessageDiv3 = document.createElement("div");
  const inputGroupDiv3 = document.createElement("div");
  const confirmPasswordInput = document.createElement("input");
  confirmPasswordInput.type = "password";
  confirmPasswordInput.name = "confirmPassword";
  confirmPasswordInput.required = true;
  confirmPasswordInput.placeholder = "Reenter Password";
  confirmPasswordInput.classList.add("form-control", "form__input");

  formMessageDiv3.classList.add("js-form-message", "mb-3");
  inputGroupDiv3.classList.add("js-focus-state", "input-group", "form");

  inputGroupDiv3.appendChild(confirmPasswordInput);
  formMessageDiv3.appendChild(inputGroupDiv3);

  const button = document.createElement("button");
  button.type = "submit";
  button.innerText = "Get Started";
  button.classList.add("btn", "btn-block", "btn-primary");

  // Log in
  const loginDiv = document.createElement("div");
  loginDiv.classList.add("text-center", "w-100");
  const p = document.createElement("p");
  p.innerText = "Already registered?";
  p.classList.add("text-muted", "font-weight-bold");
  const a = document.createElement("a");
  a.innerText = "Login";
  a.href = "#";
  a.classList.add("text-primary", "ml-2");
  a.addEventListener("click", () => {
    loginPage(content);
  });

  loginDiv.appendChild(p);
  loginDiv.appendChild(a);

  // const registerPageLink = document.createElement("a");
  // const loginPageLink = document.createElement("a");

  // registerPageLink.innerText = "Register";
  // loginPageLink.innerText = "Login";

  // registerPageLink.onclick = () => {
  //   registerPage(content);
  // };

  // loginPageLink.onclick = () => {
  //   loginPage(content);
  // };

  // registerPageLink.classList.add("m-r-20");

  // content.appendChild(registerPageLink);
  // content.appendChild(loginPageLink);

  form.addEventListener("submit", (e) => {
    registerCustomer(e, content);
  });

  getStartedDiv.appendChild(h2);
  form.appendChild(getStartedDiv);
  form.appendChild(formMessageDiv);
  form.appendChild(formMessageDiv2);
  form.appendChild(formMessageDiv3);
  form.appendChild(button);
  form.appendChild(loginDiv);
  formDiv.appendChild(form);
  signupDiv.appendChild(formDiv);
  textDiv.appendChild(joinSpan);
  textDiv.appendChild(h1);
  rowDiv.appendChild(textDiv);
  rowDiv.appendChild(signupDiv);
  containerDiv.appendChild(rowDiv);
  div.appendChild(containerDiv);
  gradientDiv.appendChild(div);
  content.appendChild(gradientDiv);
};

function registerCustomer(e, content) {
  e.preventDefault();
  let formData = new FormData(e.currentTarget);
  formData = Object.fromEntries(formData.entries());
  console.log(formData);

  if (validatePasswords(formData)) {
    const { username, password } = formData;
    formData = {
      username,
      password,
    };
    sendData(formData, content);
  } else {
    console.log(formData);
  }
}

function validatePasswords(plainFormData) {
  if (plainFormData.password === plainFormData.confirmPassword) {
    return true;
  }

  return false;
}

async function sendData(formData, content) {
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
    loginPage(content);
  } catch (error) {
    console.error(error);
  }
}

export default homePage;
