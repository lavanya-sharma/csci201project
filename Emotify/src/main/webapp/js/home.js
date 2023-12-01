
function toProfile() {
	fetchFile('profile.html');
	showBackButton(true);
}

async function toCreate() {
  try {
    await fetchFile('create.html');
    console.log('Fetch operation completed.');

    loadPage();
    showBackButton(true);
  } catch (error) {
    console.error('Error in toCreate:', error.message);
  }
}

function toFavorites() {
	fetchFile('favorites.html');
	loadPage();
	showBackButton(true);
}

function goBack(){
}
