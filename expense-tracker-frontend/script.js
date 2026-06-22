const API_URL = "http://localhost:8080/expenses";

let pieChart;
let barChart;
let editingExpenseId = null;

let currentPage = 0;
let pageSize = 5;
let totalPages = 0;



window.onload = function () {

    const savedTheme =
        localStorage.getItem("theme");

    if (savedTheme === "dark") {

        document.body.classList.add(
            "dark-mode"
        );

        document.getElementById(
            "themeToggle"
        ).innerText = "☀️ Light Mode";
    }
};

// Initial Load
loadPaginatedExpenses();
loadStatistics();
loadPieChart();
loadBarChart();
loadMonthComparison();


function loadStatistics() {

fetch(`${API_URL}/statistics`)
    .then(response => response.json())
    .then(data => {

        document.getElementById("highestExpense").innerText =
            "₹" + data.highestExpense;

        document.getElementById("averageExpense").innerText =
            "₹" + Number(data.averageExpense).toFixed(2);

        document.getElementById("topCategory").innerText =
            data.topSpendingCategory;
    });

fetch(`${API_URL}/total`)
    .then(response => response.json())
    .then(total => {

        document.getElementById("totalExpense").innerText =
            "₹" + total;
    });


}

function loadExpenses() {


fetch(API_URL)
    .then(response => response.json())
    .then(data => {

        const tableBody =
            document.getElementById("expenseTableBody");

        tableBody.innerHTML = "";

        data.forEach(expense => {

            tableBody.innerHTML += `
                <tr>
                    
                    <td>${expense.description}</td>
                    <td>${expense.amount}</td>
                    <td>${expense.categoryName}</td>
                    <td>${expense.expenseDate}</td>
                    <td>
                        <button onclick="editExpense(
                            ${expense.id},
                        
                            ${expense.amount},
                            '${expense.categoryName}',
                            '${expense.expenseDate}',
                            '${expense.description || ""}'
                        )">
                            Edit
                        </button>

                        <button onclick="deleteExpense(${expense.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
        });
    });


}

function loadPieChart() {


fetch(`${API_URL}/pie-chart`)
    .then(response => response.json())
    .then(data => {

        const labels = data.map(item => item.categoryName);
        const amounts = data.map(item => item.totalAmount);

        const ctx =
            document.getElementById("expensePieChart");

        if (pieChart) {
            pieChart.destroy();
        }

        pieChart = new Chart(ctx, {
            type: "pie",
            data: {
                labels: labels,
                datasets: [{
                    data: amounts
                }]
            }
        });
    });


}

function loadBarChart() {


const year = new Date().getFullYear();

fetch(`${API_URL}/bar-chart?year=${year}`)
    .then(response => response.json())
    .then(data => {

        const labels =
            data.map(item => item.month);

       const amounts =
    data.map(item => item.totalAmount);

        const ctx =
            document.getElementById("expenseBarChart");

        if (barChart) {
            barChart.destroy();
        }

        barChart = new Chart(ctx, {

            type: "bar",

            data: {
                labels: labels,

                datasets: [{
                    label: "Monthly Expenses",
                    data: amounts
                }]
            },

            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    })
    .catch(error => console.error(error));


}

function saveExpense() {


if (editingExpenseId === null) {
    addExpense();
} else {
    updateExpense();
}


}

function addExpense() {


const expense = getFormData();

fetch(API_URL, {

    method: "POST",

    headers: {
        "Content-Type": "application/json"
    },

    body: JSON.stringify(expense)

})
.then(response => response.json())
.then(() => {

    alert("Expense Added Successfully");

    refreshData();
    clearForm();
});


}

function editExpense(
id,
amount,
categoryName,
expenseDate,
description
) {


editingExpenseId = id;


document.getElementById("amount").value = amount;
document.getElementById("category").value = categoryName;
document.getElementById("expenseDate").value = expenseDate;
document.getElementById("description").value = description;

document.getElementById("saveButton").innerText =
    "Update Expense";


}


function updateExpense() {


const expense = getFormData();

fetch(`${API_URL}/${editingExpenseId}`, {

    method: "PUT",

    headers: {
        "Content-Type": "application/json"
    },

    body: JSON.stringify(expense)

})
.then(response => response.json())
.then(() => {

    alert("Expense Updated Successfully");

    editingExpenseId = null;

    document.getElementById("saveButton").innerText =
        "Add Expense";

    refreshData();
    clearForm();
});


}

function deleteExpense(id) {


fetch(`${API_URL}/${id}`, {
    method: "DELETE"
})
.then(() => {

    alert("Expense Deleted");

    refreshData();
});


}

function getFormData() {


return {

    amount:
        parseFloat(
            document.getElementById("amount").value
        ),

    categoryName:
        document.getElementById("category").value,

    expenseDate:
        document.getElementById("expenseDate").value,

    description:
        document.getElementById("description").value
};


}

function clearForm() {


document.getElementById("amount").value = "";
document.getElementById("category").value = "";
document.getElementById("expenseDate").value = "";
document.getElementById("description").value = "";


}

function refreshData() {


loadPaginatedExpenses();
    loadStatistics();
    loadPieChart();
    loadBarChart();
    loadMonthComparison();


}
function searchExpenses() {


const keyword =
    document.getElementById("searchKeyword").value;

fetch(`${API_URL}/search?keyword=${keyword}`)
    .then(response => response.json())
    .then(displayExpenses);


}

function filterExpenses() {


const startDate =
    document.getElementById("startDate").value;

const endDate =
    document.getElementById("endDate").value;

fetch(
    `${API_URL}/filter?startDate=${startDate}&endDate=${endDate}`
)
    .then(response => response.json())
    .then(displayExpenses);


}

function displayExpenses(data) {


const tableBody =
    document.getElementById("expenseTableBody");

tableBody.innerHTML = "";

data.forEach(expense => {

    tableBody.innerHTML += `
        <tr>
            
           
            <td>${expense.amount}</td>
            <td>${expense.categoryName}</td>
            <td>${expense.expenseDate}</td>
            <td>${expense.description}</td>
            <td>
                <button onclick="deleteExpense(${expense.id})">
                    Delete
                </button>
            </td>
        </tr>
    `;
});


}

function exportCSV() {


window.open(
    `${API_URL}/export/csv`,
    "_blank"
);


}

function exportExcel() {


window.open(
    `${API_URL}/export/excel`,
    "_blank"
);


}

function exportPDF() {


window.open(
    `${API_URL}/export/pdf`,
    "_blank"
);


}

function loadMonthComparison() {


fetch(`${API_URL}/month-comparison`)
    .then(response => response.json())
    .then(data => {

        document.getElementById(
            "currentMonthExpense"
        ).innerText =
            "₹" + data.currentMonthExpense;

        document.getElementById(
            "previousMonthExpense"
        ).innerText =
            "₹" + data.previousMonthExpense;

        const differenceElement =
    document.getElementById("expenseDifference");

differenceElement.innerText =
    "₹" + data.difference;

if (data.difference >= 0) {

    differenceElement.style.color = "green";

} else {

    differenceElement.style.color = "red";

}
    });


}
function loadPaginatedExpenses() {

fetch(
    `${API_URL}/paginated?page=${currentPage}&size=${pageSize}&sortBy=expenseDate`
)
    .then(response => response.json())
    .then(data => {

        totalPages = data.totalPages;

        displayExpenses(data.content);

        document.getElementById("pageInfo").innerText =
            `Page ${currentPage + 1} of ${totalPages}`;
    });


}

function nextPage() {


if (currentPage < totalPages - 1) {

    currentPage++;

    loadPaginatedExpenses();
}


}

function previousPage() {


if (currentPage > 0) {

    currentPage--;

    loadPaginatedExpenses();
}


}

function loadCategorySummary() {


const month =
    document.getElementById("summaryMonth").value;

const year =
    document.getElementById("summaryYear").value;

fetch(
    `${API_URL}/category-summary?month=${month}&year=${year}`
)
    .then(response => response.json())
    .then(data => {

        const tableBody =
            document.getElementById(
                "categorySummaryBody"
            );

        tableBody.innerHTML = "";

        data.forEach(item => {

            tableBody.innerHTML += `
                <tr>
                    <td>${item.categoryName}</td>
                    <td>₹${item.totalAmount}</td>
                </tr>
            `;
        });
    });


}

function loadCurrentMonthExpenses() {

fetch(`${API_URL}/current-month`)
    .then(response => response.json())
    .then(data => {

        const tableBody =
            document.getElementById("currentMonthBody");

        tableBody.innerHTML = "";

        data.forEach(expense => {

            tableBody.innerHTML += `
                <tr>
    <td>${expense.categoryName}</td>
    <td>₹${expense.amount}</td>
    <td>${expense.description}</td>
    <td>${expense.expenseDate}</td>
</tr>
            `;
        });
    });

}

function toggleTheme() {

    document.body.classList.toggle("dark-mode");

    const button =
        document.getElementById("themeToggle");

    if (
        document.body.classList.contains("dark-mode")
    ) {

        button.innerText = "☀️ Light Mode";

        localStorage.setItem(
            "theme",
            "dark"
        );

    } else {

        button.innerText = "🌙 Dark Mode";

        localStorage.setItem(
            "theme",
            "light"
        );
    }
}

