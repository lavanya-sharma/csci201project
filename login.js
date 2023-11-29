function signIn() {
		
	fetch('home.html')
      .then(response => response.text())
      .then(html => {
      	// Replace the entire body with the content from the external HTML file
      	document.body.innerHTML = html;
      })
 	.catch(error => console.error('Error loading external content:', error));

}

function signUp() {
		
	fetch('signup.html')
      .then(response => response.text())
      .then(html => {
      	// Replace the entire body with the content from the external HTML file
      	document.body.innerHTML = html;
      })
 	.catch(error => console.error('Error loading external content:', error));

}

function asGuest() {
		
	fetch('home.html')
      .then(response => response.text())
      .then(html => {
      	// Replace the entire body with the content from the external HTML file
      	document.body.innerHTML = html;
      })
 	.catch(error => console.error('Error loading external content:', error));

}
