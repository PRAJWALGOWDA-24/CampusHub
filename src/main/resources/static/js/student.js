
let selectedStudentId = null;
let currentPage = 0;
const BASE_URL = window.location.origin;


const pageSize = 5;
const token = localStorage.getItem("token");
const role = localStorage.getItem("role");

console.log("TOKEN:", token);

async function loadStudents() {



    const response = await fetch(

        BASE_URL +"/students/page?page="

        + currentPage +

        "&size=" + pageSize,

        {

            headers:{

                "Authorization":"Bearer " + token

            }

        }

    );
    if(!checkSession(response)){
        return;
    }
console.log(response.status);
    const page = await response.json();

console.log(page);

console.log(page.content);

    displayStudents(page.content);

    document.getElementById("pageNumber").innerHTML =

            "Page " + (currentPage + 1);

}

async function saveStudent() {



    const student = {

        registrationNumber:
            document.getElementById("registrationNumber").value,

        firstName:
            document.getElementById("firstName").value,

        lastName:
            document.getElementById("lastName").value,

        email:
            document.getElementById("email").value,

        phoneNumber:
            document.getElementById("phoneNumber").value,

        dateOfBirth:
            document.getElementById("dateOfBirth").value,

        gender:
            document.getElementById("gender").value,

        password:
            document.getElementById("password").value,

        role:
            document.getElementById("role").value

    };

    console.log(student);

    const response = await fetch(
        BASE_URL+ "/students",
        {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(student)

        });


if(!checkSession(response)){

    return;

}
    console.log(response.status);

const data = await response.json();

if (!response.ok) {

    alert(data.join("\n"));

    return;
}

alert("Student added successfully!");

clearTable();

loadStudents();
}

function clearTable() {

    const table = document.getElementById("studentTable");

table.innerHTML = `
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
    <th>Action</th>
</tr>
`;
}


async function deleteStudent(id){

if(role !== "ROLE_ADMIN"){

    alert("Only Admin can perform this action.");

    return;

}
    const token = localStorage.getItem("token");

    const confirmDelete =
        confirm("Delete this student?");

    if(!confirmDelete){

        return;

    }

    const response = await fetch(

        BASE_URL +"/students/" + id,

        {

            method:"DELETE",

            headers:{

                "Authorization":"Bearer " + token

            }

        }

    );


    if(!checkSession(response)){

        return;

    }

    console.log(response.status);

    clearTable();

    loadStudents();

}

async function editStudent(id){  //put
if(role !== "ROLE_ADMIN"){

    alert("Only Admin can perform this action.");

    return;

}

    const token = localStorage.getItem("token");

    const response = await fetch(

        BASE_URL +"/students/" + id,

        {

            headers:{

                "Authorization":"Bearer " + token

            }

        }

    );


    if(!checkSession(response)){

        return;

    }

    const student = await response.json();

    selectedStudentId = student.id;

    document.getElementById("registrationNumber").value =
        student.registrationNumber;

    document.getElementById("firstName").value =
        student.firstName;

    document.getElementById("lastName").value =
        student.lastName;

    document.getElementById("email").value =
        student.email;

    document.getElementById("phoneNumber").value =
        student.phoneNumber;

    document.getElementById("dateOfBirth").value =
        student.dateOfBirth;

    document.getElementById("gender").value =
        student.gender;

    document.getElementById("role").value =
        student.role;

document.getElementById("saveButton").innerHTML =
"Update Student";

document.getElementById("saveButton").onclick =
updateStudent;

}

async function updateStudent() {



    const student = {

        registrationNumber:
            document.getElementById("registrationNumber").value,

        firstName:
            document.getElementById("firstName").value,

        lastName:
            document.getElementById("lastName").value,

        email:
            document.getElementById("email").value,

        phoneNumber:
            document.getElementById("phoneNumber").value,

        dateOfBirth:
            document.getElementById("dateOfBirth").value,

        gender:
            document.getElementById("gender").value

    };

    const response = await fetch(

        BASE_URL +"/students/" + selectedStudentId,

        {

            method: "PUT",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(student)

        }

    );


    if(!checkSession(response)){

        return;

    }

    console.log(response.status);

    clearTable();

    loadStudents();

    clearForm();

}
function clearForm() {

    document.getElementById("registrationNumber").value = "";
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("email").value = "";
    document.getElementById("phoneNumber").value = "";
    document.getElementById("dateOfBirth").value = "";
    document.getElementById("password").value = "";

    document.getElementById("gender").selectedIndex = 0;
    document.getElementById("role").selectedIndex = 0;

    selectedStudentId = null;

    document.getElementById("saveButton").innerHTML =
            "Save Student";

    document.getElementById("saveButton").onclick =
            saveStudent;

}

function displayStudents(students) {

    clearTable();

    const table =
        document.getElementById("studentTable");

    students.forEach(student => {

        const row = table.insertRow();

        row.insertCell(0).innerHTML = student.id;

        row.insertCell(1).innerHTML =
                student.firstName + " " + student.lastName;

        row.insertCell(2).innerHTML =
                student.email;

        const actionCell =
                row.insertCell(3);

if(role === "ROLE_ADMIN"){

actionCell.innerHTML = `

<button onclick="viewStudent(${student.id})">

 View

</button>

<button onclick="editStudent(${student.id})">

 Edit

</button>

<button onclick="deleteStudent(${student.id})">

 Delete

</button>

<button onclick="resetPassword(${student.id})">
Reset Password
</button>

`;

}else{

actionCell.innerHTML = "";

}

    });


}

async function searchStudent() {



    const firstName =
            document.getElementById("searchName").value;

    const response = await fetch(

       BASE_URL +"/students/search?firstName=" + firstName,

        {

            method: "GET",

            headers: {

                "Authorization": "Bearer " + token

            }

        }

    );
    if(!checkSession(response)){
        return;
    }

    const students = await response.json();

    displayStudents(students);

}
function showAllStudents() {

    loadStudents();

}

function nextPage(){

    currentPage++;

    loadStudents();

}

function previousPage(){

    if(currentPage > 0){

        currentPage--;

    }

    loadStudents();

}



window.onload = function () {

    if(role !== "ROLE_ADMIN"){

        document.getElementById("studentFormCard").style.display = "none";

    }

    loadStudents();

}

function viewStudent(id){

    window.location.href =
        "student-details.html?id=" + id;

}


//reset password
async function resetPassword(id){

    const newPassword =
        prompt("Enter New Password");

    if(!newPassword){

        return;

    }

    const response = await fetch(

       BASE_URL +"/students/"
        + id +
        "/reset-password",

        {

            method:"PUT",

            headers:{

                "Content-Type":"application/json",

                "Authorization":"Bearer " + token

            },

            body:JSON.stringify({

                password:newPassword

            })

        }

    );


    if(!checkSession(response)){

        return;

    }

    alert(await response.text());

}


//just give message to user that expries token , needd to login in
//instaed of getting error like 401 or 403 in console so user cant understand so this is good way
function checkSession(response){

    if(response.status === 401 || response.status === 403){

        alert("Session expired. Please login again.");

        localStorage.clear();

        window.location.href = "login.html";

        return false;

    }

    return true;

}