async function fetchAccounts() {
  const url = "http://localhost:7000/accounts";

  const response = await fetch(url);
  const data = await response.json();
  return data;
}

async function fetchEmployees() {
  const url = "http://localhost:7000/employees";

  const response = await fetch(url);
  const data = await response.json();
  return data;
}

async function displayAccountTable() {
  const data = await fetchAccounts();
  document.getElementById("manage").innerText = "Manage Accounts";
  document.getElementById("add").innerText = "Add new account";

  table.innerHTML = `
    ${accountHeadTemplate()}
    ${data.map(accountTemplate).join("")}
    ${tableEnd()}
  `;

  const approveButtons = document.querySelectorAll("#approveBtn");
  approveButtons.forEach((button) => {
    button.addEventListener("click", (e) => {
      e.preventDefault();
      approveAccount(button.dataset.id);
    });
  });
}

function tableEnd() {
  return `
    </tbody>
  </table>
  `;
}

function accountHeadTemplate() {
  return `
  <thead>
    <tr>
      <th></th>
      <th>Id</th>
      <th>Type</th>
      <th>Balance</th>
      <th>Owner</th>
      <th>Pending</th>
      <th>Created</th>
      <th>Approved By</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
  `;
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
      <td>${account.approvedBy}</td>
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
  const approvedBy = JSON.parse(localStorage.getItem("employee")).id;
  const url = `http://localhost:7000/account/${approvedBy}`;
  const body = { accountId: id, pending: false };

  const response = await fetch(url, {
    method: "PATCH",
    body: JSON.stringify(body),
  });

  displayAccountTable();
}

accounts.addEventListener("click", () => {
  displayAccountTable();
});

async function displayEmployeeTable() {
  const data = await fetchEmployees();
  document.getElementById("manage").innerText = "Manage Employees";
  document.getElementById("add").innerText = "Add new employee";

  table.innerHTML = `
    ${employeeHeadTemplate()}
    ${data.map(employeeTemplate).join("")}
    ${tableEnd()}
  `;
}

function employeeHeadTemplate() {
  return `
  <thead>
    <tr>
      <th></th>
      <th>username</th>
      <th>Created</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
  `;
}

function employeeTemplate(employee) {
  const date = new Date(employee.dateCreated);

  return `
    <tr>
      <td></td>
      <td>${employee.username}</td>
      <td>${date.getDate()}/${date.getMonth()}/${date.getFullYear()}</td>
      <td>
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

employees.addEventListener("click", () => {
  displayEmployeeTable();
});

logoutBtn.addEventListener("click", () => {
  localStorage.removeItem("employee");
  window.location.replace("http://127.0.0.1:5500/public/employeeLogin.html");
});

displayAccountTable();
