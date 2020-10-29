/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global gapi */

function init() {
    gapi.load('auth2', function () {
        gapi.auth2.init().then(onInit, onError);
    });
}

function onInit() {
    $(".g-signin2").show();
}

function onError(response) {
    if (response.error === 'idpiframe_initialization_failed') {
        $(".g-signin2").hide();
//        showErrorMessage("Google Login is not supported in private browser.");
    }
}

function showErrorMessage(message) {
    let messageHtml = `<div class="alert alert-danger fade show mt-2" role="alert">
                            <strong>
                                <span>&times;</span>
                                ${message}
                            </strong>
                        </div>`;
    document.getElementById("error_message").innerHTML = messageHtml;
}

function onSignIn(googleUser) {
    const profile = googleUser.getBasicProfile();
    const token_id = googleUser.getAuthResponse().id_token;
    let auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect();
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'signInGoogle');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            let status = data.status;
            if (status) {
                window.location.href = data.redirect_url;
            } else {
                showErrorMessage(data.error_message);
            }
        }
    };
    xhr.send('token_id=' + token_id);
}