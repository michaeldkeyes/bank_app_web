let user = JSON.parse(localStorage.getItem("user"));

const greeting = document.getElementById("greeting");
const accountsDiv = document.getElementById("accounts");
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
      }">
        <div class="card-header py-3 ${
          account.pending ? "" : "text-white bg-primary border-primary"
        }">
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

sendBtn.addEventListener("click", (e) => {
  e.preventDefault();
  doTransaction(transactionData);
});

function doTransaction(transactionData) {
  const transactionForm = document.getElementById("transactionForm");
  const { index, accountId } = transactionData;
  let formData = new FormData(transactionForm);
  formData = Object.fromEntries(formData.entries());
  if (formData.amount > 0) {
    if (formData.type === "withdraw") {
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
  const url = `http://localhost:7000/transaction`;

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
    console.log(data);
    user.accounts[index] = data;
    localStorage.setItem("user", JSON.stringify(user));
    displayAccounts();
  } catch (error) {
    console.error(error);
  }
}
