// Sample customer and account arrays
const customers = [
    { id: 1, name: "Alice", balance: 5000 },
    { id: 2, name: "Bob", balance: 3000 }
];

const accounts = [
    { id: 3, name: "Charlie", balance: 7000 },
    { id: 4, name: "David", balance: 2000 }
];

// Function to merge customer and account arrays using the spread operator
function mergeAccounts() {
    const mergedArray = [...customers, ...accounts];
    console.log("Merged Accounts:", mergedArray);
    document.getElementById("output").innerText = `Merged Accounts: ${JSON.stringify(mergedArray, null, 2)}`;
}

// Function to update a customer’s details using the spread operator
function updateCustomer(newValues) {
    let index = customers.findIndex(c => c.id === newValues.id);
    
    if (index !== -1) {
        customers[index] = { ...customers[index], ...newValues };
    } else {
        customers.push(newValues);
    }

    console.log("Updated Customers:", customers);
    document.getElementById("output").innerText = `Updated Customers: ${JSON.stringify(customers, null, 2)}`;
}

// Function to calculate total balance using the spread operator
function calculateTotalBalance() {
    const allAccounts = [...customers, ...accounts];
    const totalBalance = allAccounts.reduce((sum, account) => sum + account.balance, 0);

    console.log("Total Balance:", totalBalance);
    document.getElementById("output").innerText = `Total Balance: $${totalBalance}`;
}

// Event Listeners for buttons
document.getElementById("mergeAccounts").addEventListener("click", mergeAccounts);
document.getElementById("calculateTotalBalance").addEventListener("click", calculateTotalBalance);

// Example update
setTimeout(() => {
    updateCustomer({ id: 1, name: "Alice Johnson", balance: 6000 });
}, 2000);
