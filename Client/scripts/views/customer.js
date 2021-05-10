import accountPage from "./account";

const customerPage = (content, props) => {
  content.innerHTML = "";
  const { username, password, accounts } = props;

  const h1 = document.createElement("h1");
  h1.innerText = `Greetings ${username}!`;
  const ul = document.createElement("ul");

  accounts.forEach((account) => {
    const li = document.createElement("li");
    li.innerText = `${account.accountId} ${
      account.type
    } - ${currencyFormatter.format(account.balance)}`;

    li.addEventListener("click", () => {
      accountPage(content, account);
    });

    ul.appendChild(li);
  });

  content.appendChild(h1);
  content.appendChild(ul);
};

const currencyFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
});

export default customerPage;
