
let signedIn = false;
let onBannerless = true;

window.onload = function (){	
	fetchFile('login.html');
     setUpBanner();
}

function onQuit(){
	fetchFile('login.html');
	funcLogout();
 	setUpBanner();
}

function onLogin(){
	fetchFile('login.html');
	funcLogout();
	setUpBanner();
}

function onHome(){
	fetchFile('home.html');
	setUpBanner();
}


function funcLogout(){
	signedIn = false;
	onBannerless = true;
}

function funcLogin(){
	signedIn = true;
	onBannerless = false;
}

function funcLoginGuest(){
	signedIn = false;
	onBannerless = false;
}

function funcSignUp() {
	signedIn = true;
	onBannerless = false;
}

function setUpBanner(){	
	if(onBannerless){
		document.getElementById("banner").style.display = "none";
	}
	else{
		document.getElementById("banner").style.display = "grid";
		if(signedIn){
			document.getElementById("quita").textContent = "Quit";			
		}
		else{
			document.getElementById("quita").textContent = "Login / Sign Up";
		}
	}
}

function fetchFile(file) {
  fetch(file)
    .then(response => response.text())
    .then(html => {
      // Create a temporary container to parse the external content
      const tempContainer = document.createElement('div');
      tempContainer.innerHTML = html;

      // Extract the body content, styles, and scripts from the temporary container
      const externalBody = tempContainer.querySelector('#body');

      const externalStyles = tempContainer.querySelectorAll('link[rel="stylesheet"]');
      const externalScripts = tempContainer.querySelectorAll('script');

      // Inject the external body content into the current document's body
      const currentBody = document.getElementById('body');
      currentBody.innerHTML = ''; // Clear existing content
      currentBody.appendChild(externalBody.cloneNode(true));

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
    .catch(error => console.error('Error loading external content:', error.message));
}
