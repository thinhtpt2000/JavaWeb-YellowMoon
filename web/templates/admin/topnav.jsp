<%-- 
    Document   : topnav
    Created on : Oct 5, 2020, 9:36:16 AM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="google-signin-client_id" content="858265005924-k3mu82si52ikvmue1rdf50g7tpd66cpg.apps.googleusercontent.com">
    </head>
    <body>
        <!-- Topbar -->
        <nav
            class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow"
            >
            <!-- Sidebar Toggle (Topbar) -->
            <form class="form-inline">
                <button
                    id="sidebarToggleTop"
                    class="btn btn-link d-md-none rounded-circle mr-3"
                    >
                    <i class="fa fa-bars"></i>
                </button>
            </form>

            <!-- Topbar Navbar -->
            <ul class="navbar-nav ml-auto">
                <!-- Nav Item - User Information -->
                <li class="nav-item dropdown no-arrow">
                    <a
                        class="nav-link dropdown-toggle"
                        href="#"
                        id="userDropdown"
                        role="button"
                        data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false"
                        >
                        <span class="mr-2 d-none d-lg-inline text-gray-600 small"
                              >${sessionScope.USER_FULLNAME}</span
                        >
                        <img
                            class="img-profile rounded-circle"
                            src="img/logo.jpg"
                            />
                    </a>
                    <!-- Dropdown - User Information -->
                    <div
                        class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                        aria-labelledby="userDropdown"
                        >
                        <a class="dropdown-item" href="#">
                            <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                            Activity Log
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" onclick="signOut();" href="#">
                            <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                            Sign out
                        </a>
                    </div>
                </li>
            </ul>
        </nav>
        <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
        <script src="js/googlesignout.js">
        </script>
    </body>
</html>
