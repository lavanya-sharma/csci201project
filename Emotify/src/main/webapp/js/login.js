
function signIn() {
	funcLogin();
	fetchFile('home.html');
	setUpBanner();
}

function signUp() {
	fetchFile('signup.html');
	setUpBanner();
}

function asGuest() {
	funcLoginGuest();
	fetchFile('home.html');
    setUpBanner();
}
