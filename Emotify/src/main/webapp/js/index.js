
let signedIn = false;
let onBannerless = true;

window.onload = function (){	
	fetchFile('login.html');
    setUpBanner();
    showBackButton(false);
}

function goBack(){
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
	showBackButton(false);
	setUpBanner();
}

function funcLogout(){
	signedIn = false;
	onBannerless = true;
}

function funcLogin(){
	var xhr = new XMLHttpRequest();
	var url = "../Login"; // Servlet URL
	
	// Collect form data
	var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
	var formData = new FormData();
	formData.append("username",username);
	formData.append("password",password);
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" +  queryString, true);
		
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		console.log(xhr.status);
		if (xhr.status == 302) {
			//var response = JSON.parse(xhr.responseText);
			//displayResult(response);
			var redirectUrl = xhr.getResponseHeader("Location");
            window.location.href = redirectUrl;
                signedIn = true;
				onBannerless = false;
			//showBackButton(false);
			//setUpBanner();
		}
	};
	xhr.send();

  
	
	//fetchFile('results.html');
}

	


function funcLoginGuest(){
	signedIn = false;
	onBannerless = false;
}

function funcSignUp() {
		var xhr = new XMLHttpRequest();
	var url = "../SignUp"; // Servlet URL
	
	// Collect form data
	var email = document.getElementById("username").value;
	var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
	var formData = new FormData();
	formData.append("email",email);
	formData.append("username",username);
	formData.append("password",password);
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" +  queryString, true);
		
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		console.log(xhr.status);
		if (xhr.status == 302) {
			//var response = JSON.parse(xhr.responseText);
			//displayResult(response);
			var redirectUrl = xhr.getResponseHeader("Location");
            window.location.href = redirectUrl;
                signedIn = true;
				onBannerless = false;
			//showBackButton(false);
			//setUpBanner();
		}
	};
	xhr.send();

  
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

function showBackButton(boolean){
	if(boolean){
		document.getElementById("backa").style.display = "block";
	}
	else{
		document.getElementById("backa").style.display = "none";
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
