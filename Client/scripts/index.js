import registerCustomer from "./register";
import loginCustomer from "./login";

const registerForm = document.getElementById("registerForm");
const loginForm = document.getElementById("loginForm");

registerForm.addEventListener("submit", (e) => {
  registerCustomer(e);
});

loginForm.addEventListener("submit", (e) => {
  loginCustomer(e);
});
// $("#loginForm").on("submit", function (e) {
//   console.log(e);
//   loginCustomer(e);
// });
