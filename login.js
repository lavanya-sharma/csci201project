function signIn() {
		var username = document.getElementById('username').value;
		var password = document.getElementById('password').value;
		
	  var servletUrl = "Login";

	  // Use the fetch API to send a POST request
	  fetch(servletUrl, {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/x-www-form-urlencoded",
	    },
	    body: "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password),
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
		
		
/**
	fetch('home.html')
      .then(response => response.text())
      .then(html => {
        // Create a temporary container to parse the external content
        const tempContainer = document.createElement('div');
        tempContainer.innerHTML = html;

        // Extract the body content, styles, and scripts from the temporary container
        const externalBody = tempContainer.querySelector('body');
        const externalStyles = tempContainer.querySelectorAll('link[rel="stylesheet"]');
        const externalScripts = tempContainer.querySelectorAll('script');

        // Inject the external body content into the current document
        document.getElementById('content-container').innerHTML = externalBody.innerHTML;

        // Append the external styles to the head of the current document
        externalStyles.forEach(style => document.head.appendChild(style.cloneNode(true)));

        // Append the external scripts to the body of the current document
        externalScripts.forEach(script => {
          const newScript = document.createElement('script');
          newScript.src = script.src;
          newScript.defer = true; // Assuming you want to defer script execution
          document.body.appendChild(newScript);
        });
      })
      .catch(error => console.error('Error loading external content:', error));
      
       */

}

function signUp() {
		
	fetch('signup.html')
      .then(response => response.text())
      .then(html => {
        // Create a temporary container to parse the external content
        const tempContainer = document.createElement('div');
        tempContainer.innerHTML = html;

        // Extract the body content, styles, and scripts from the temporary container
        const externalBody = tempContainer.querySelector('body');
        const externalStyles = tempContainer.querySelectorAll('link[rel="stylesheet"]');
        const externalScripts = tempContainer.querySelectorAll('script');

        // Inject the external body content into the current document
        document.getElementById('content-container').innerHTML = externalBody.innerHTML;

        // Append the external styles to the head of the current document
        externalStyles.forEach(style => document.head.appendChild(style.cloneNode(true)));

        // Append the external scripts to the body of the current document
        externalScripts.forEach(script => {
          const newScript = document.createElement('script');
          newScript.src = script.src;
          newScript.defer = true; // Assuming you want to defer script execution
          document.body.appendChild(newScript);
        });
      })
      .catch(error => console.error('Error loading external content:', error));

}

function asGuest() {
		
	fetch('home.html')
      .then(response => response.text())
      .then(html => {
        // Create a temporary container to parse the external content
        const tempContainer = document.createElement('div');
        tempContainer.innerHTML = html;

        // Extract the body content, styles, and scripts from the temporary container
        const externalBody = tempContainer.querySelector('body');
        const externalStyles = tempContainer.querySelectorAll('link[rel="stylesheet"]');
        const externalScripts = tempContainer.querySelectorAll('script');

        // Inject the external body content into the current document
        document.getElementById('content-container').innerHTML = externalBody.innerHTML;

        // Append the external styles to the head of the current document
        externalStyles.forEach(style => document.head.appendChild(style.cloneNode(true)));

        // Append the external scripts to the body of the current document
        externalScripts.forEach(script => {
          const newScript = document.createElement('script');
          newScript.src = script.src;
          newScript.defer = true; // Assuming you want to defer script execution
          document.body.appendChild(newScript);
        });
      })
      .catch(error => console.error('Error loading external content:', error));
}