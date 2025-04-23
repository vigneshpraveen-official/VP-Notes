// Bank Account Object
const bankAccount = {
    customerName: "John Doe",
    accountType: "Savings",
    balance: 5000,
    dateOfCreation: "2024-01-15",
    bankName: "ABC Bank",
    branchName: "Downtown",
    panCardNumber: "ABCDE1234F"
};

// Function to list all entries of the object
function listEntries(obj) {
    let entries = Object.entries(obj);
    let result = "<strong>Bank Account Details:</strong><br>";
    for (let [key, value] of entries) {
        result += `<strong>${key}:</strong> ${value} <br>`;
    }
    return result;
}

// Function to check the existence of a key and return its value
function getKey(obj, key) {
    if (obj.hasOwnProperty(key)) {
        return `<strong>${key} exists.</strong> Value: ${obj[key]}`;
    } else {
        return `<strong>${key} does not exist.</strong>`;
    }
}

// Wait for DOM to load before running script
document.addEventListener("DOMContentLoaded", () => {
    const outputDiv = document.getElementById("output");

    // Display list of entries
    let entriesOutput = listEntries(bankAccount);

    // Check for a specific key (example: "balance")
    let keyCheckOutput = getKey(bankAccount, "balance");

    // Display all results
    outputDiv.innerHTML = entriesOutput + "<br>" + keyCheckOutput;
});
