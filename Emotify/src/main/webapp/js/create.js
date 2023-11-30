function submitSearchForm(event) {
	
	event.preventDefault();

	var xhr = new XMLHttpRequest();
	var url = "../Api"; // Servlet URL
	
	// Collect form data
	var formData = new FormData(document.getElementById('Form'));
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" +  queryString, true);
		
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		console.log(xhr.status);
		if (xhr.readyState == 4 && xhr.status == 200) {
			var response = JSON.parse(xhr.responseText);
			displayResult(response);
		}
	};
	xhr.send();
	
	//fetchFile('results.html');

}
function displayResult(data) {
	
	document.getElementById("createForm").style.display="none";
	document.getElementById("output").style.display="flex";
            
    const scrollContainer = document.getElementById('output');

    // Add items to the container
    for (let i = 0; i < data.songids.length; i++) {
         const iframe = document.createElement('iframe');
	    iframe.id = 'spotifyFrame';
	    iframe.src = 'https://open.spotify.com/embed/track/' + data.songids[i] + '?utm_source=generator&theme=0';
	    iframe.width = '100%';
	    iframe.allowFullscreen = true;
	    iframe.allow = 'autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture';
	    iframe.loading = 'lazy';

      
      scrollContainer.appendChild(iframe);
  	}
}
