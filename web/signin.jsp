<%-- 
    Document   : signIn
    Created on : Oct 4, 2020, 10:12:37 AM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="google-signin-client_id" content="858265005924-k3mu82si52ikvmue1rdf50g7tpd66cpg.apps.googleusercontent.com">
        <title>Sign In | YMS</title>
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            />
        <link rel="stylesheet" href="css/common.css" />
        <style>
            .separator {
                width: 100%; 
                text-align: center; 
                border-bottom: 1px solid #F8F9FA; 
                line-height: 0.1em;
                margin: 0 auto; 
            }
            .separator span { 
                padding: 0 10px; 
            }
            .g-signin2 div {
                margin: 0 auto;
            }
        </style>
    </head>
    <body>
        <h2 class="text-center my-4 shop-name text-warning">
            <strong>
                Yellow Moon Shop
            </strong>
        </h2>
        <div class="container">
            <div class="row">
                <!-- SIGN IN -->
                <div id="signInForm" class="card col-sm-6 mx-auto bg-dark text-light">
                    <article class="card-body">
                        <h4 class="card-title mb-4 mt-1">Sign in</h4>
                        <c:set var="errorMsg" value="${requestScope.ERR_MSG}" />
                        <form class="needs-validation" action="signIn" method="POST">
                            <div class="form-group">
                                <label>Your email</label>
                                <input
                                    name="txtUserId"
                                    class="form-control 
                                    <c:if test="${not empty errorMsg}">
                                        is-invalid
                                    </c:if>"
                                    placeholder="Email"
                                    type="email"
                                    value="${param.txtUserId}"
                                    required
                                    />
                            </div>
                            <div class="form-group">
                                <label>Your password</label>
                                <input
                                    name="txtPassword"
                                    class="form-control 
                                    <c:if test="${not empty errorMsg}">
                                        is-invalid
                                    </c:if>"
                                    placeholder="******"
                                    type="password"
                                    required
                                    />
                                <div class="invalid-feedback">
                                    ${errorMsg}
                                </div>
                            </div>

                            <div class="form-group">
                                <button type="submit" class="btn btn-block bg-warning text-light">
                                    <strong>
                                        Sign in
                                    </strong>
                                </button>
                            </div>
                        </form>
                        <div class="separator col-md-6 my-4">
                            <span class="bg-dark">OR</span>
                        </div>

                        <div class="g-signin2" data-onsuccess="onSignIn" style="display: none"></div>
                        <div id="error_message"></div>
                        <div class="col-md-12 text-center my-4">
                            <a href="searchPage">&rarr; Click here to visit as guest</a>
                        </div>
                    </article>
                </div>
            </div>
        </div>
        <script
            src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"
        ></script>
        <script
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"
        ></script>
        <script src="https://apis.google.com/js/platform.js?onload=init" async defer gapi_processed="true"></script>
        <script src="js/googlesignin.js"></script>
    </body>
</html>