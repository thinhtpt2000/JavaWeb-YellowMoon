<%-- 
    Document   : error
    Created on : Oct 8, 2020, 10:40:38 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error | YMS</title>
    </head>
    <body>
        <!--HEADER-->
        <jsp:include page="templates/web/header.jsp" flush="true"/>

        <!--CONTAINER-->
        <div class="container-fluid my-4">
            <div class="row">
                <div class="col-md-6 text-center mx-auto">
                    <img src="img/error.jpg" class="w-50 rounded-circle" />
                    <h2 class="error mx-auto" data-text="Error">Error</h2>    
                    <p class="lead text-gray-800 mb-5">Opps, something wrong...</p>
                    <a href="searchPage">&larr; Back to Home</a>
                </div>
            </div>
        </div>

        <jsp:include page="templates/web/footer.html" flush="true" />
    </body>
</html>
