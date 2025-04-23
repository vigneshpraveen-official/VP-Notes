document.addEventListener("DOMContentLoaded", () => {
    // Creating a bank account object
    const bankAccount = {
        customerName: "John Doe",
        accountType: "Savings",
        balance: 5000,
        dateOfCreation: "2023-06-15",
        bankName: "ABC Bank",
        branchName: "Downtown Branch",
        panCardNumber: "ABCDE1234F"
    };

    // Function to list all key-value pairs of the object
    function listEntries(obj) {
        return Object.entries(obj)
            .map(([key, value]) => `<strong>${key}:</strong> ${value}`)
            .join("<br>");
    }

    // Function to check if a key exists and return its value
    function getKey(obj, key) {
        return obj.hasOwnProperty(key) 
            ? `<strong>${key} exists:</strong> ${obj[key]}`
            : `<strong>${key} not found.</strong>`;
    }

    // Displaying the results in the output div
    const outputDiv = document.getElementById("output");
    outputDiv.innerHTML = `
        <h3>Bank Account Entries:</h3>
        ${listEntries(bankAccount)}
        <br><br>
        <h3>Checking Key Existence:</h3>
        ${getKey(bankAccount, "balance")} <br>
        ${getKey(bankAccount, "ifscCode")}
    `;
});
