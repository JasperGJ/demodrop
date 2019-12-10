class AuthenticationService {
    registerSuccesfulLogin(username, password) {
        console.log("registerSuccesfulLogin");
        sessionStorage.setItem('authenticatedUser', username);
    }


    logout() {
        sessionStorage.removeItem('authenticatedUser');
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('authenticatedUser');
        return user !== null
    }
}

export default new AuthenticationService()