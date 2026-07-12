const BASE_URL = window.location.origin;
async function askAI() {

    const question =
            document.getElementById("question").value;

    if(question=="")
        return;

    const chatBox =
            document.getElementById("chatBox");

    chatBox.innerHTML +=

        "<p><b>You :</b> " + question + "</p>";

    const response = await fetch(

       BASE_URL + "/ai/chat",

        {

            method:"POST",

            headers:{

                "Content-Type":"application/json"

            },

          body: JSON.stringify({

              prompt: question

          })

        }

    );

  const data = await response.json();

  chatBox.innerHTML +=
  "<p><b>AI :</b> " + data.answer + "</p>";

    document.getElementById("question").value="";

    chatBox.scrollTop = chatBox.scrollHeight;

}