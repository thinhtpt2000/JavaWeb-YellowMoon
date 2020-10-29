<%-- 
    Document   : admin
    Created on : Oct 5, 2020, 9:24:31 AM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body id="page-top">
        <fmt:setLocale value = "vi_VN"/>
        <div id="wrapper">
            <jsp:include page="templates/admin/sidebar.jsp" flush="true">
                <jsp:param name="currentPage" value="Edit" />
            </jsp:include>
            <div id="content-wrapper" class="d-flex flex-column">
                <!-- Main Content -->
                <div id="content">
                    <jsp:include page="templates/admin/topnav.jsp" flush="true"/>
                    <div class="container-fluid">
                        <!-- Page Heading -->
                        <h1 class="h3 mb-2 text-gray-800">Edit cakes</h1>
                        <p class="mb-4">
                            Update cakes here.
                        </p>
                        <div class="row">
                            <form class="mb-4" action="loadCakes" method="POST">
                                <div class="form-group mx-sm-3">
                                    <input
                                        name="page"
                                        class="form-control"
                                        type="hidden"
                                        value="${requestScope.CURR_PAGE}"
                                        required
                                        />
                                </div>
                                <div class="form-group mx-sm-3">
                                    <button class="btn btn-primary">Load | Refresh Data</button>
                                </div>
                            </form>
                        </div>
                        <c:set value="${requestScope.LIST_PRODUCTS}" var="listProducts" />
                        <c:if test="${not empty listProducts}">
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">
                                        Cakes Table
                                    </h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table
                                            class="table table-bordered"
                                            width="100%"
                                            cellspacing="0"
                                            >
                                            <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>Description</th>
                                                    <th class="text-center">Image</th>
                                                    <th class="text-right">Price</th>
                                                    <th class="text-center">Quantity</th>
                                                    <th class="text-right">Action</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>Description</th>
                                                    <th class="text-center">Image</th>
                                                    <th class="text-right">Price</th>
                                                    <th class="text-center">Quantity</th>
                                                    <th class="text-right">Action</th>
                                                </tr>
                                            </tfoot>
                                            <tbody>
                                                <c:forEach items="${listProducts}" var="dto">
                                                    <tr>
                                                        <td>${dto.name}</td>
                                                        <td>${dto.description}</td>
                                                        <td class="text-center">
                                                            <img src="${dto.image}" width="150"/>
                                                        </td>
                                                        <td class="text-right">
                                                            <fmt:formatNumber value="${dto.price}" type="currency"/>
                                                        </td>
                                                        <td class="text-center">${dto.quantity}</td>
                                                        <td class="text-right">
                                                            <form action="renderUpdate" method="POST">
                                                                <input type="hidden" name="txtId" value="${dto.productId}" />
                                                                <input type="hidden" name="page" value="${requestScope.CURR_PAGE}" />
                                                                <button class="btn btn-primary">
                                                                    <i class="fas fa-edit"></i>
                                                                    Edit
                                                                </button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <nav>
                                        <form action="loadCakes" method="POST">
                                            <ul class="pagination justify-content-end">
                                                <c:set value="${requestScope.CURR_PAGE}" var="currentPageNum" />
                                                <c:set value="${requestScope.NUM_OF_PAGE}" var="numOfPage" />
                                                <c:set value="${requestScope.LIST_PAGES}" var="listPages" />
                                                <li class="page-item 
                                                    <c:if test="${currentPageNum eq 1}">disabled</c:if>">
                                                        <button name="page" value="1" class="page-link">
                                                            <i class="fas fa-fast-backward"></i>
                                                        </button>
                                                    </li>
                                                <c:forEach items="${listPages}" var="pageNum" >
                                                    <li class="page-item 
                                                        <c:if test="${pageNum eq currentPageNum}">active disabled</c:if>">
                                                        <button name="page" value="${pageNum}" class="page-link">${pageNum}</button>
                                                    </li>
                                                </c:forEach>
                                                <li class="page-item 
                                                    <c:if test="${currentPageNum eq numOfPage}">disabled</c:if>">
                                                    <button name="page" value="${numOfPage}" class="page-link">
                                                        <i class="fas fa-fast-forward"></i>
                                                    </button>
                                                </li>
                                            </ul> 
                                        </form>
                                    </nav>
                                </div>
                            </div>
                        </c:if>
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
        </div>

        <jsp:include page="templates/admin/footer.html" flush="true" />

    </body>
</html>
