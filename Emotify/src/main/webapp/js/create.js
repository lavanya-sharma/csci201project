function submitSearchForm(event) {
	
	event.preventDefault();
	fetchFile('results.html');
}

function goBack(){
	fetchFile('home.html');
	showBackButton(false);
	setUpBanner();
}