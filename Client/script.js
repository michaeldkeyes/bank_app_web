const form = document.getElementById("example-form");

form.addEventListener("submit", (e) => {
  e.preventDefault();

  handleFormSubmit();
});

// form.addEventListener("submit", handleFormSubmit);

// async function handleFormSubmit(event) {
//   event.preventDefault();

//   const form = event.currentTarget;

//   const url = form.action;

//   try {
//     const formData = new FormData(form);

//     const responseData = await postFormDataAsJson({ url, formData });

//     console.log({ responseData });
//   } catch (error) {
//     console.error("In handleFormSubmit: " + error);
//   }
// }

// async function postFormDataAsJson({ url, formData }) {
//   const plainFormData = Object.fromEntries(formData.entries());
//   const formDataJsonString = JSON.stringify(plainFormData);

//   const fetchOptions = {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//       Accept: "application/json",
//     },
//     body: formDataJsonString,
//   };

//   const response = await fetch(url, fetchOptions);

//   if (!response.ok) {
//     const errorMessage = await response.text();
//     throw new Error(errorMessage);
//   }

//   return response.json();
//}

function handleFormSubmit() {
  const XHR = new XMLHttpRequest();
  let FD = new FormData(form);

  FD = Object.fromEntries(FD.entries());

  let urlEncodedData = "",
    urlEncodedDataPairs = [],
    name;

  for (name in FD) {
    urlEncodedDataPairs.push(
      encodeURIComponent(name) + "=" + encodeURIComponent(FD[name])
    );
  }

  urlEncodedData = urlEncodedDataPairs.join("&").replace(/%20/g, "+");
  console.log(urlEncodedData);

  FD = JSON.stringify(FD);

  XHR.addEventListener("load", (e) => {
    alert(e.target.responseText);
  });

  XHR.addEventListener("error", (e) => {
    console.log(e);
  });

  XHR.open("POST", "http://localhost:7000/");

  XHR.send(FD);
}
