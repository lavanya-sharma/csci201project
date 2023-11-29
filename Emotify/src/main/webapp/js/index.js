
window.onload = function (){
    fetch('login.html')
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
        document.getElementById('body').innerHTML = externalBody.innerHTML;

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