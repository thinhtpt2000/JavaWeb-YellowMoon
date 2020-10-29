/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global gapi */

let isGoogleLoginValid = false;

function signOut() {
    if (isGoogleLoginValid) {
        if (gapi.auth2) {
            let auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
                // call logout from server
                window.location.href = "signOut";
            });
        } else {
            window.location.href = "signOut";
        }
    } else {
        window.location.href = "signOut";
    }
}
function onLoad() {
    gapi.load('auth2', function () {
        gapi.auth2.init().then(onInit, onError);
    });
}

function onInit() {
    isGoogleLoginValid = true;
}

function onError() {
    isGoogleLoginValid = false;
}
