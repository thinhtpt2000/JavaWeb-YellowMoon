<%-- 
    Document   : sidebar
    Created on : Oct 5, 2020, 10:49:00 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"/>
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet"
            />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/4.0.7/css/sb-admin-2.min.css" />
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            />
    </head>
    <body>
        <ul
            class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
            id="accordionSidebar"
            >
            <a
                class="sidebar-brand d-flex align-items-center justify-content-center"
                href="adminPage"
                >
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">YMS - Admin</div>
            </a>
            <div class="sidebar-heading">Cake management</div>
            <li class="nav-item 
                <c:if test='${param.currentPage eq "Edit"}'>
                    active
                </c:if>
                ">
                <a class="nav-link" href="adminPage">
                    <i class="fas fa-edit"></i>
                    <span>Edit</span></a
                >
            </li>
            <li class="nav-item
                <c:if test='${param.currentPage eq "Create"}'>
                    active
                </c:if>">
                <a class="nav-link" href="createPage">
                    <i class="fas fa-plus-square"></i>
                    <span>Create</span></a
                >
            </li>
            <hr class="sidebar-divider d-none d-md-block" />
            <li class="nav-item">
                <a class="nav-link" href="searchPage">
                    <i class="fas fa-store"></i>
                    <span>Visit Store</span></a
                >
            </li>
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>
        </ul>
    </body>
</html>
