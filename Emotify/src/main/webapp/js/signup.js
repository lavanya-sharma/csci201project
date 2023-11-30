
function signIn() {
	fetchFile('signin.html');
	setUpBanner();
}

function signUp() {
	funcSignUp();
	fetchFile('home.html');
	setUpBanner();
}

function asGuest() {
	funcLoginGuest();
	fetchFile('home.html');
    setUpBanner();
}
