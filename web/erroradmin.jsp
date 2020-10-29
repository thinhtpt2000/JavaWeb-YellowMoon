<%-- 
    Document   : erroradmin
    Created on : Oct 5, 2020, 10:46:11 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error | Admin Page</title>
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="templates/admin/sidebar.jsp" flush="true" />
            <div id="content-wrapper" class="d-flex flex-column">
                <!-- Main Content -->
                <div id="content">
                    <jsp:include page="templates/admin/topnav.jsp" flush="true"/>
                    <div class="container-fluid">
                        <div class="text-center">
                            <div class="error mx-auto" data-text="Error">Error</div>    
                            <p class="lead text-gray-800 mb-5">Something wrong...</p>
                            <a href="adminPage">&larr; Back to Admin Home</a>
                        </div>
                    </div>
                </div>
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>Copyright &copy; ThinhTPT 2020</span>
                        </div>
                    </div>
                </footer>
            </div>
            <jsp:include page="templates/admin/footer.html" flush="true" />
    </body>
</html>
