function signUp() {
		console.log("got to signup");
		var email = document.getElementById('email').value;
		var username = document.getElementById('username').value;
		var password = document.getElementById('password').value;
		
	 	var servletUrl = "SignUp";

	  	// Use the fetch API to send a POST request
	  	fetch(servletUrl, {
	   	 method: "POST",
	   	 headers: {
	     	 "Content-Type": "application/x-www-form-urlencoded",
	   	 },
	   	 body: "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password) + "&email=" + encodeURIComponent(email),
	  	})
	    .then(response => response.text())
	    .then(responseText => {
	      // Display the response from the servlet
	      document.getElementById('response').innerHTML = responseText;
	    })
	    .catch(error => {
	      // Handle errors
	      console.error("Error:", error);
	    });
}