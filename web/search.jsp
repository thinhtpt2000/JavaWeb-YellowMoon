<%-- 
    Document   : search
    Created on : Oct 4, 2020, 9:46:25 AM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yellow Moon Shop</title>
        <link 
            rel="stylesheet" 
            href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/11.0.2/css/bootstrap-slider.min.css" 
            />
        <style>
            .slider {
                width: 100% !important;
            }
            .slider-handle {
                background: #000;
            }
            .slider-selection {
                background: #FFC107;
            }
            .page-item.active > button {
                background: #FFC107 !important;
                color: #212529 !important;
                border-color: #FFC107 !important;
            }
            .page-item.active > button:hover{
                color: #FFC107 !important;
            }
        </style>
    </head>
    <body>
        <fmt:setLocale value = "vi_VN"/>
        <!--HEADER-->
        <jsp:include page="templates/web/header.jsp" flush="true">
            <jsp:param name="currentPage" value="Home" />
        </jsp:include>

        <!--CONTAINER-->
        <div class="container-fluid">
            <c:if test="${not empty requestScope.ERROR_ADD}">
                <div class="row mt-4">
                    <div class="alert alert-danger alert-dismissible fade show col-md-6 mx-auto" role="alert">
                        <strong> ${requestScope.ERROR_ADD}</strong>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </c:if>
            <div class="row my-4">
                <div class="col-md-3">
                    <article class="card-body bg-light rounded">
                        <h4 class="card-title mb-4 mt-1 text-uppercase text-warning">
                            <strong>
                                Search cake
                            </strong>
                        </h4>
                        <c:if test="${not empty requestScope.ERROR_SEARCH}">
                            <div class="row mt-4">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <strong> ${requestScope.ERROR_SEARCH}</strong>
                                </div>
                            </div>
                        </c:if>
                        <form class="my-4 mx-auto" action="search" method="POST">
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="search-name">
                                        <strong>Name</strong>
                                    </label>
                                    <input
                                        id="search-name"
                                        type="text"
                                        class="form-control"
                                        name="txtName"
                                        placeholder="Search name"
                                        value="${param.txtName}"
                                        />
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="search-category"><strong>Category</strong></label>
                                    <select id="search-category" class="form-control" name="txtCategory">
                                        <option value="">Select category</option>
                                        <c:if test="${not empty param.txtCategory}">
                                            <c:if test="${param.txtCategory.matches('[0-9]+')}">
                                                <c:set var="categoryId" value="${param.txtCategory}"/>
                                            </c:if>
                                            <c:if test="${not param.txtCategory.matches('[0-9]+')}">
                                                <c:set var="categoryId" value="-1"/>
                                            </c:if>
                                        </c:if>
                                        <c:set var="listCategories" value="${requestScope.LIST_CATEGORIES}"/>
                                        <c:forEach items="${listCategories}" var="category">
                                            <option value="${category.categoryId}" 
                                                    <c:if test="${categoryId eq category.categoryId}">
                                                        selected
                                                    </c:if>
                                                    >${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="price-slider"><strong>Range of price</strong></label>
                                    <input id="price-slider" type="text" value="" />
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <input 
                                        id="min-price"
                                        class='form-control 
                                        <c:if test="${not empty requestScope.ERROR_PRICE}">
                                            is-invalid
                                        </c:if>
                                        '
                                        type="number" 
                                        name="txtMinPrice" 
                                        value="${param.txtMinPrice}"
                                        class="w-100"
                                        step="1000"
                                        />
                                </div>
                                <div class="form-group col-md-6">
                                    <input 
                                        id="max-price"
                                        class='form-control 
                                        <c:if test="${not empty requestScope.ERROR_PRICE}">
                                            is-invalid
                                        </c:if>
                                        '
                                        type="number" 
                                        name="txtMaxPrice" 
                                        value="${param.txtMaxPrice}"
                                        class="w-100"
                                        step="1000"
                                        />
                                </div>
                                <c:if test="${not empty requestScope.ERROR_PRICE}">
                                    <div class="col-md-12 invalid-feedback" style='display: block'>
                                        ${requestScope.ERROR_PRICE}
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12 mx-auto">
                                    <button class="btn btn-block btn-warning">
                                        <i class="fas fa-search"></i>
                                        <strong>Search</strong>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </article>
                </div>
                <c:if test="${(not empty param.txtName) 
                              or (not empty param.txtCategory) 
                              or ((not empty param.txtMinPrice) and (not empty param.txtMaxPrice))}">
                    <c:set value="${requestScope.LIST_PRODUCTS}" var="listProducts" />

                    <div class="col-md-9">
                        <div class="row">
                            <c:if test="${not empty listProducts}"> 
                                <c:forEach items="${listProducts}" var="dto">
                                    <div class="col-md-3 mb-4">
                                        <div class="card h-100">
                                            <a href="#"
                                               ><img
                                                    class="card-img-top"
                                                    src="${dto.image}"
                                                    alt=""
                                                    /></a>
                                            <div class="card-body">
                                                <h6 class="card-title mb-1">
                                                    ${dto.name}
                                                </h6>
                                                <h6 class="text-danger">
                                                    <fmt:formatNumber value="${dto.price}" type="currency"/>
                                                </h6>
                                                <p class="card-text my-1">
                                                    ${dto.description}
                                                </p>
                                                <small>
                                                    <ul class="list-unstyled my-1">
                                                        <li>Category: 
                                                            <c:forEach items="${listCategories}" var="category">
                                                                <c:if test="${dto.categoryId eq category.categoryId}">
                                                                    ${category.name}
                                                                </c:if>
                                                            </c:forEach>
                                                        </li>
                                                        <li>
                                                            Create:
                                                            <fmt:formatDate value = "${dto.createDate}"/>
                                                        </li>
                                                        <li>
                                                            Expire:
                                                            <fmt:formatDate value = "${dto.expirationDate}"/>
                                                        </li>
                                                    </ul>
                                                </small>
                                            </div>
                                            <div class="card-footer">
                                                <c:if test='${(empty sessionScope.USER_ROLE) or (not (sessionScope.USER_ROLE eq "Admin"))}'>
                                                    <form action="addToCart" method="POST">
                                                        <input
                                                            type="hidden"
                                                            class="form-control"
                                                            name="txtId"
                                                            value="${dto.productId}"
                                                            />
                                                        <input
                                                            type="hidden"
                                                            class="form-control"
                                                            name="txtName"
                                                            value="${param.txtName}"
                                                            />
                                                        <input
                                                            type="hidden"
                                                            class="form-control"
                                                            name="txtCategory"
                                                            value="${param.txtCategory}"
                                                            />
                                                        <input  
                                                            type="hidden"
                                                            class="form-control"
                                                            name="txtMinPrice"
                                                            value="${param.txtMinPrice}"
                                                            />
                                                        <input  
                                                            type="hidden"
                                                            class="form-control"
                                                            name="txtMaxPrice"
                                                            value="${param.txtMaxPrice}"
                                                            />
                                                        <input  
                                                            type="hidden"
                                                            class="form-control"
                                                            name="page"
                                                            value="${requestScope.CURR_PAGE}"
                                                            />
                                                        <button class="btn btn-block btn-warning">
                                                            <i class="fas fa-cart-plus"></i>
                                                            <strong>Add to cart</strong>
                                                        </button>
                                                    </form>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <nav>
                                <form action="search" method="POST">
                                    <ul class="pagination justify-content-center">
                                        <c:set value="${requestScope.CURR_PAGE}" var="currentPageNum" />
                                        <c:set value="${requestScope.NUM_OF_PAGE}" var="numOfPage" />
                                        <c:set value="${requestScope.LIST_PAGES}" var="listPages" />
                                        <li class="page-item 
                                            <c:if test="${currentPageNum eq 1}">disabled</c:if>">
                                                <button name="page" value="1" class="page-link bg-dark text-warning">
                                                    <i class="fas fa-fast-backward"></i>
                                                </button>
                                            </li>
                                        <c:forEach items="${listPages}" var="pageNum" >
                                            <li class="page-item 
                                                <c:if test="${pageNum eq currentPageNum}">active disabled</c:if>">
                                                <button name="page" value="${pageNum}" class="page-link bg-dark text-warning">${pageNum}</button>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item 
                                            <c:if test="${currentPageNum eq numOfPage}">disabled</c:if>">
                                            <button name="page" value="${numOfPage}" class="page-link bg-dark text-warning">
                                                <i class="fas fa-fast-forward"></i>
                                            </button>
                                        </li>
                                    </ul>
                                    <input
                                        type="hidden"
                                        class="form-control"
                                        name="txtName"
                                        value="${param.txtName}"
                                        />
                                    <input
                                        type="hidden"
                                        class="form-control"
                                        name="txtCategory"
                                        value="${param.txtCategory}"
                                        />
                                    <input  
                                        type="hidden"
                                        class="form-control"
                                        name="txtMinPrice"
                                        value="${param.txtMinPrice}"
                                        />
                                    <input  
                                        type="hidden"
                                        class="form-control"
                                        name="txtMaxPrice"
                                        value="${param.txtMaxPrice}"
                                        />
                                </form>
                            </nav>
                        </c:if>
                        <c:if test="${empty listProducts}">
                            <div class="alert alert-info fade show" role="alert">
                                <strong>No cake is found, please try another search value ! ! !</strong>
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
        <jsp:include page="templates/web/footer.html" flush="true" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/11.0.2/bootstrap-slider.min.js">
        </script>
        <script src="js/slider.js">
        </script>
    </body>
</html>
