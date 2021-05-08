const form = document.getElementById("registration-form");

form.addEventListener("submit", (e) => {
  e.preventDefault();

  let FD = new FormData(e.currentTarget);
  FD = Object.fromEntries(FD.entries());

  if (validatePasswords(FD)) {
    const { username, password } = FD;
    let data = {
      username,
      password,
    };
    registerSubmit(data);
  } else {
    alert("Passwords do not match!");
  }
});

async function registerSubmit(data) {
  const XHR = new XMLHttpRequest();
  const method = form.method;
  const url = form.action;

  let urlEncodedData = "",
    urlEncodedDataPairs = [],
    name;

  for (name in data) {
    urlEncodedDataPairs.push(
      encodeURIComponent(name) + "=" + encodeURIComponent(data[name])
    );
  }

  urlEncodedData = urlEncodedDataPairs.join("&").replace(/%20/g, "+");

  data = JSON.stringify(data);

  XHR.addEventListener("load", (e) => {
    alert(e.target.responseText);
  });

  XHR.addEventListener("error", (e) => {
    console.log(e);
  });

  XHR.open(method, url);

  XHR.send(data);
}

function validatePasswords(plainFormData) {
  if (plainFormData.password === plainFormData.confirmPassword) {
    return true;
  }

  return false;
}
