
function signIn() {
	funcLogin();
	fetchFile('home.html');
	showBackButton(false);
	setUpBanner();
}

function signUp() {
	fetchFile('signup.html');
	setUpBanner();
}

function asGuest() {
	funcLoginGuest();
	fetchFile('home.html');
	showBackButton(false);
    setUpBanner();
}

