async function fetchAccounts() {
  const url = "http://localhost:7000/accounts";

  const response = await fetch(url);
  const data = await response.json();
  return data;
}

async function displayAccountTable() {
  const data = await fetchAccounts();
  const tbody = document.getElementById("tbody");

  tbody.innerHTML = `
    ${data.map(accountTemplate).join("")}
  `;

  const approveButtons = document.querySelectorAll("#approveBtn");
  approveButtons.forEach((button) => {
    button.addEventListener("click", (e) => {
      e.preventDefault();
      approveAccount(button.dataset.id);
    });
  });
}

function accountTemplate(account) {
  const date = new Date(account.createdAt);

  return `
    <tr>
      <td></td>
      <td>${account.accountId}</td>
      <td>${account.type}</td>
      <td>$${account.balance.toFixed(2)}</td>
      <td>${account.ownerId}</td>
      <td>${account.pending}</td>
      <td>${date.getDate()}/${date.getMonth()}/${date.getFullYear()}</td>
      <td>
        <button class="btn btn-success" ${
          !account.pending ? "hidden" : "id=approveBtn"
        } data-id=${account.accountId}>
          <i class="fas fa-check" ></i>
        </button>
        <button class="btn btn-warning">
          <i class="fas fa-edit"></i>
        </button>
        <button class="btn btn-danger">
          <i class="fas fa-trash-alt"></i>
        </button>
      </td>
    </tr>
  `;
}

async function approveAccount(id) {
  const url = "http://localhost:7000/account";
  const body = { accountId: id, pending: false };

  const response = await fetch(url, {
    method: "PATCH",
    body: JSON.stringify(body),
  });

  displayAccountTable();
}

logoutBtn.addEventListener("click", () => {
  localStorage.removeItem("employee");
  window.location.replace("http://127.0.0.1:5500/public/employeeLogin.html");
});

displayAccountTable();
