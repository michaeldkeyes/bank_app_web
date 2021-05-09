import loginPage from "./login";
import registerPage from "./register";

const homePage = (content) => {
  content.innerHTML = "";
  const registerPageLink = document.createElement("a");
  const loginPageLink = document.createElement("a");

  registerPageLink.innerText = "Register";
  loginPageLink.innerText = "Login";

  registerPageLink.onclick = () => {
    registerPage(content);
  };

  loginPageLink.onclick = () => {
    loginPage(content);
  };

  registerPageLink.classList.add("m-r-20");

  content.appendChild(registerPageLink);
  content.appendChild(loginPageLink);
};

export default homePage;
