let user = JSON.parse(localStorage.getItem("user"));

const greeting = document.getElementById("greeting");
const accountsDiv = document.getElementById("accounts");
const transactionsTable = document.getElementById("transactions");
const logoutBtn = document.getElementById("logoutBtn");
let transactionData;

greeting.innerText = `Hello, ${user.username}!`;

const formatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
});

displayAccounts();

function displayAccounts() {
  accountsDiv.innerHTML = "";
  user = JSON.parse(localStorage.getItem("user"));
  user.accounts.forEach((account, index) => {
    const div = document.createElement("div");
    const html = `
    <div class="col">
      <div class="card mb-4 rounded-3 shadow-sm ${
        account.pending ? "" : "border-primary"
      }" >
        <div class="card-header py-3 ${
          account.pending ? "" : "text-white bg-primary border-primary"
        }" id="${account.pending ? "" : "accountCard"}" data-index="${index}">
          <h4 class="my-0 fw-normal">
            ${account.type}
          </h4>
        </div>
        <div class="card-body">
          <h1 class="card-title">
            ${formatter.format(account.balance)}
          </h1>
          <button class="w-100 btn btn-lg ${
            account.pending ? "btn-outline-primary" : "btn-primary"
          }" type="button" ${
      account.pending
        ? "disabled"
        : `data-toggle="modal" data-target="#transactionModal"`
    } data-id=${account.accountId} data-type=${
      account.type
    } data-index=${index} id="transactionBtn">
            ${account.pending ? "Account is pending" : "Withdraw/Deposit"} 
          </button>
        </div>
      </div>
    </div>`;
    div.innerHTML = html;
    accountsDiv.appendChild(div);
  });
}

async function displayTransactionsTable(index) {
  transactionsTable.innerHTML = "";
  const tableStart = `
    <div class="table-responsive">
      <table class="table text-center">
        <thead>
          <tr>
            <th>Type</th>
            <th>Amount</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>`;

  const tableEnd = `</tbody>
    </table>
  </div>`;
  const data = await getTransactions(index);

  transactionsTable.innerHTML = `<h2 class="display-6 text-center mb-4">${
    user.accounts[index].type
  }: ${user.accounts[index].accountId}</h2>
      ${tableStart}
      ${data.map(transactionTemplate).join("")}
      ${tableEnd}`;
}

function transactionTemplate(transaction) {
  return `
    <tr>
      <td>${transaction.type}</td>
      <td style="color: ${
        transaction.amount > 0 ? "green" : "red"
      }">$${transaction.amount.toFixed(2)}</td>
      <td>${new Date(transaction.timestamp)}</td>
    </tr>
  `;
}

const transactionBtns = document.querySelectorAll("#transactionBtn");
transactionBtns.forEach((button) => {
  if (!button.disabled) {
    button.addEventListener("click", () => {
      document.getElementById("transactionModalLabel").innerText =
        button.dataset.type + " : " + button.dataset.id;
      transactionData = {
        accountId: button.dataset.id,
        index: button.dataset.index,
      };
    });
  }
});

const accountCards = document.querySelectorAll("#accountCard");
function addAccountCardEvents() {
  accountCards.forEach((card) => {
    card.addEventListener("click", () => {
      displayTransactionsTable(card.dataset.index);
    });
  });
}

addAccountCardEvents();

function addSendBtnEvent() {
  sendBtn.addEventListener("click", (e) => {
    e.preventDefault();
    doTransaction(transactionData);
  });
}
addSendBtnEvent();

function doTransaction(transactionData) {
  const transactionForm = document.getElementById("transactionForm");
  const { index, accountId } = transactionData;
  let formData = new FormData(transactionForm);
  formData = Object.fromEntries(formData.entries());
  if (formData.amount > 0) {
    if (formData.type === "withdraw" || formData.type === "transfer") {
      if (formData.amount < user.accounts[index].balance) {
        formData.amount *= -1;
      } else {
        transactionModalLabel.innerHTML = `<span style="color: red;">Withdraw amount cannot exceed account balance</span>`;
        return;
      }
    }
    formData.amount = parseFloat(formData.amount).toFixed(2);
    formData = { ...formData, accountId };
    send(formData, index);
  } else {
    transactionModalLabel.innerHTML =
      '<span style="color: red;">Amount must be greater than 0</span>';
  }
}

async function send(formData, index) {
  let url = `http://localhost:7000/transaction`;

  if (formData.type === "transfer") {
    const { accountToTransferToId } = formData;
    url = `http://localhost:7000/transaction/${accountToTransferToId}`;
    formData.type = "withdraw";
    delete formData.accountToTransferToId;
  }

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
    document.getElementById(
      "transactionModalLabel"
    ).innerHTML = `<span style="color: green;">Successful ${formData.type} of ${formData.amount}</span>`;
    user.accounts[index] = data;
    localStorage.setItem("user", JSON.stringify(user));
    displayAccounts();
    addAccountCardEvents();
    addSendBtnEvent();
  } catch (error) {
    console.error(error);
  }
}

async function getTransactions(index) {
  const accountId = user.accounts[index].accountId;
  const url = `http://localhost:7000/transactions/${accountId}`;

  try {
    const response = await fetch(url);

    if (!response.ok) {
      const message = await response.json();
      console.error(message);
      return;
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
  }
}

logoutBtn.addEventListener("click", () => {
  localStorage.removeItem("user");
  window.location.replace("http://127.0.0.1:5500/public/index.html");
});

createSavingsBtn.addEventListener("click", () => {
  createNewAccount(createSavingsBtn.dataset.type);
});

createCheckingBtn.addEventListener("click", () => {
  createNewAccount(createCheckingBtn.dataset.type);
});

function check() {
  if (document.getElementById("transfer").checked === true) {
    document.getElementById("transferInput").hidden = false;
  } else {
    document.getElementById("transferInput").hidden = true;
  }
}

async function createNewAccount(type) {
  const url = `http://localhost:7000/account`;
  const account = {
    type,
    ownerId: user.id,
    balance: 0,
    pending: true,
  };

  try {
    const response = await fetch(url, {
      method: "POST",
      body: JSON.stringify(account),
    });

    if (!response.ok) {
      const message = await response.json();
      console.log(message);
      return;
    }

    const data = await response.json();
    user.accounts = [...user.accounts, account];
    localStorage.setItem("user", JSON.stringify(user));
    displayAccounts();
    addSendBtnEvent();
    addAccountCardEvents();
  } catch (error) {
    console.error(error);
  }
}
