const form = document.getElementById("form");

form.addEventListener("submit", (e) => {
  e.preventDefault();

  let FD = new FormData(e.currentTarget);
  FD = Object.fromEntries(FD.entries());

  registerLogin(FD);
});

async function registerLogin(data) {
  const XHR = new XMLHttpRequest();
  const method = form.method;
  const url = form.action + data.username + "/" + data.password;

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

  console.log(data);

  XHR.addEventListener("load", (e) => {
    console.log(e.target.responseText);
  });

  XHR.addEventListener("error", (e) => {
    console.log(e);
  });

  XHR.open(method, url);

  XHR.send();
}
