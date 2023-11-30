function signOutFromProfile(){
	funcLogout();
	fetchFile('login.html');
	setUpBanner();
}

function goBack(){
	fetchFile('home.html');
	showBackButton(false);
}