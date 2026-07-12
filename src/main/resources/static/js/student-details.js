const token = localStorage.getItem("token");

const params = new URLSearchParams(window.location.search);

const id = params.get("id");

loadStudent();

async function loadStudent(){

    const response = await fetch(

        "http://localhost:8080/students/" + id,

        {

            headers:{

                "Authorization":"Bearer " + token

            }

        }

    );

    const student = await response.json();

    document.getElementById("regNo").innerHTML =
        student.registrationNumber;

    document.getElementById("firstName").innerHTML =
        student.firstName;

    document.getElementById("lastName").innerHTML =
        student.lastName;

    document.getElementById("email").innerHTML =
        student.email;

    document.getElementById("phone").innerHTML =
        student.phoneNumber;

    document.getElementById("dob").innerHTML =
        student.dateOfBirth;

    document.getElementById("gender").innerHTML =
        student.gender;

    document.getElementById("role" ).innerHTML =
        student.role;

}