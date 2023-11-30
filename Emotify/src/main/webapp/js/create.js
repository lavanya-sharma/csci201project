function submitSearchForm(event) {
	
	event.preventDefault();

	var xhr = new XMLHttpRequest();
	var url = "../../Api"; // Servlet URL
	
	// Collect form data
	var formData = new FormData(document.getElementById('Form'));
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" + queryString, true);
	
	xhr.onreadystatechange = function() {
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
            
    var resultDiv = document.getElementById('output');

    var content = "";

    //add to content from data to generate front-end
  
	for(var i = 0; i<data.songids.length; i++){
		content += "<p>" +data.songids[i] + "<\p>"; 
	}
    
    resultDiv.innerHTML = content;
}
