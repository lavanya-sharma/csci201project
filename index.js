
window.onload = function (){
	fetch('login.html')
      .then(response => response.text())
      .then(html => {
      	// Replace the entire body with the content from the external HTML file
      	document.body.innerHTML = html;
      })
 	.catch(error => console.error('Error loading external content:', error));

}