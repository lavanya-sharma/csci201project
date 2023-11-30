function submitSearchForm(event) {
	
	event.preventDefault();

	var xhr = new XMLHttpRequest();
	var url = "API"; // Servlet URL
	
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
            
            var resultDiv = document.getElementById('output');

            var content = "";

            //add to content from data to generate front-end

            
            resultDiv.innerHTML = content;
        }
