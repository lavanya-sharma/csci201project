function signOutFromProfile(){
	funcLogout();
	fetchFile('login.html');
	setUpBanner();
}

function goBack(){
	fetchFile('home.html');
	bshowBackButton(false);
}