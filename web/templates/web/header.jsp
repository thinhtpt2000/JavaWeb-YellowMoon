<%-- 
    Document   : header
    Created on : Oct 4, 2020, 12:06:10 PM
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
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"/>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/common.css" />
    </head>
    <body>
        <!-- HEADER -->
        <nav class="navbar navbar-expand-lg navbar-light bg-warning sticky-top">
            <a class="navbar-brand">
                <!--<i class="fas fa-laugh-wink"></i>-->
                <img src="img/logo.jpg" width="30" height="30" class="d-inline-block align-top rounded" alt="" loading="lazy">
                <strong>Yellow Moon Shop</strong>
            </a>
            <button
                class="navbar-toggler"
                type="button"
                data-toggle="collapse"
                data-target="#navbarNavDropdown"
                aria-controls="navbarNavDropdown"
                aria-expanded="false"
                aria-label="Toggle navigation"
                >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav mr-auto">
                    <li class='nav-item
                        <c:if test='${param.currentPage eq "Home"}'>
                            active 
                        </c:if>
                        '>
                        <a class="nav-link" href="searchPage">
                            <i class="fas fa-home"></i>
                            Home <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <c:if test='${sessionScope.USER_ROLE eq "Admin"}'>
                        <li class='nav-item'>
                            <a class="nav-link" href="adminPage">
                                <i class="fas fa-users-cog"></i>
                                Admin Page
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${(empty sessionScope.USER_ROLE) or (not (sessionScope.USER_ROLE eq "Admin"))}'>
                        <li class='nav-item
                            <c:if test='${param.currentPage eq "Cart"}'>
                                active 
                            </c:if>
                            '>
                            <c:if test="${not empty sessionScope.CART.items}">
                                <a class="nav-link" href="viewCart">
                                    <i class="fas fa-shopping-cart"></i>
                                    Cart (${sessionScope.CART.amount})
                                </a>
                            </c:if>
                            <c:if test="${empty sessionScope.CART.items}">
                                <a class="nav-link" href="viewCart">
                                    <i class="fas fa-shopping-cart"></i>
                                    Cart (0)
                                </a>
                            </c:if>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.USER_FULLNAME}">
                        <li class='nav-item
                            <c:if test='${param.currentPage eq "Tracking"}'>
                                active 
                            </c:if>
                            '>
                            <a class="nav-link" href="trackOrderPage">
                                <i class="fas fa-file-invoice"></i>
                                Order Tracking
                            </a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <c:if test="${not empty sessionScope.USER_FULLNAME}">
                        <span class="nav-item navbar-text text-dark mr-2">
                            <strong>
                                <i class="fas fa-user"></i>
                                ${sessionScope.USER_FULLNAME}
                            </strong>
                        </span>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="signOut();">
                                <i class="fas fa-sign-out-alt"></i>
                                Sign Out
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${empty sessionScope.USER_FULLNAME}">
                        <li class="nav-item">
                            <a class="nav-link" href="signInPage">
                                <i class="fas fa-sign-in-alt"></i>
                                Sign In
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </nav>
        <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
        <script src="js/googlesignout.js">
        </script>
    </body>
</html>