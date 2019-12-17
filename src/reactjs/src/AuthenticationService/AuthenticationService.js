class AuthenticationService {
    registerSuccesfulLogin(username, home) {
        console.log("registerSuccesfulLogin");
        sessionStorage.setItem('authenticatedUser', username);
        sessionStorage.setItem('userhome', home);
    }


     logout() {
        sessionStorage.removeItem('authenticatedUser');
         fetch("/logout",{
            method: "POST"
        })
            .then(responce => {
                console.log("logout",responce,responce.url);
             });
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('authenticatedUser');
        return user !== null
    }

    getUserHome() {
        let home = sessionStorage.getItem('userhome');
        return home;
    }
}

export default new AuthenticationService()