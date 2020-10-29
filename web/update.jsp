<%-- 
    Document   : update
    Created on : Oct 5, 2020, 10:12:16 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Cake | Admin Page</title>
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="templates/admin/sidebar.jsp" flush="true">
                <jsp:param name="currentPage" value="Edit" />
            </jsp:include>
            <div id="content-wrapper" class="d-flex flex-column">
                <!-- Main Content -->
                <div id="content">
                    <jsp:include page="templates/admin/topnav.jsp" flush="true"/>
                    <div class="container-fluid">
                        <div class="col-md-8 mx-auto">
                            <c:set value="${requestScope.PRODUCT_INFO}" var="product" />
                            <c:set value="${requestScope.LIST_CATEGORIES}" var="listCategories" />
                            <c:set value="${requestScope.LIST_STATUS}" var="listStatus" />
                            <form action="loadCakes" method="POST">
                                <div class="form-group">
                                    <input 
                                        name="page" 
                                        type="hidden" 
                                        class="form-control" 
                                        value="${param.page}"
                                        />
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn text-dark">
                                        <strong>
                                            <i class="fas fa-chevron-left"></i>
                                            <span>Back</span>
                                        </strong>
                                    </button>
                                </div>
                            </form>
                            <form action="updateCake" method="POST">
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="cake-name" class="col-form-label">Name</label>
                                        <input  
                                            name="txtName" 
                                            type="text" 
                                            class="form-control" 
                                            id="cake-name" 
                                            value="${product.name}"
                                            placeholder="(from 10 to 100 chars)"
                                            required
                                            minlength="10"
                                            maxlength="100"
                                            />
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cake-price" class="col-form-label">Price (VND)</label>
                                        <input 
                                            name="txtPrice" 
                                            type="number" 
                                            class="form-control" 
                                            id="cake-price" 
                                            value="${product.price}"
                                            placeholder="(VND)"
                                            required
                                            min="50000"
                                            max="5000000"
                                            step="1000"
                                            />
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cake-quantity" class="col-form-label">Quantity</label>
                                        <input  
                                            name="txtQuantity" 
                                            type="number" 
                                            class="form-control" 
                                            id="cake-quantity" 
                                            value="${product.quantity}"
                                            placeholder="(cái)"
                                            required
                                            min="0"
                                            max="5000"
                                            />
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="cake-createDate" class="col-form-label">Create date</label>
                                        <input 
                                            name="txtCreateDate" 
                                            type="date" 
                                            class="form-control" 
                                            id="cake-createDate"
                                            value='<fmt:formatDate pattern = "yyyy-MM-dd" 
                                                            value = "${product.createDate}"/>'
                                            />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="cake-expireDate" class="col-form-label">Expire date</label>
                                        <input 
                                            name="txtExpireDate" 
                                            type="date" 
                                            class="form-control" 
                                            id="cake-expireDate"
                                            value='<fmt:formatDate pattern = "yyyy-MM-dd"
                                                            value = "${product.expirationDate}"/>'
                                            />
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-8">
                                        <label for="cake-category" class="col-form-label">Category</label>
                                        <select class="form-control" name="txtCategory" required id="cake-category">
                                            <option value="">Select category</option>
                                            <c:forEach items="${listCategories}" var="category">
                                                <option value="${category.categoryId}" 
                                                        <c:if test="${product.categoryId eq category.categoryId}">
                                                            selected
                                                        </c:if>
                                                        >${category.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="cake-status" class="col-form-label">Status</label>
                                        <select class="form-control" name="txtStatus" required id="cake-status">
                                            <option value="">Select status</option>
                                            <c:forEach items="${listStatus}" var="status">
                                                <option value="${status.statusId}" 
                                                        <c:if test="${product.statusId eq status.statusId}">
                                                            selected
                                                        </c:if>
                                                        >${status.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group custom-file col-md-8 mb-4">
                                        <input 
                                            id="fileInput" 
                                            type="file" 
                                            class="form-control-file" 
                                            accept="image/*" 
                                            style="display:none"
                                            />
                                        <a href="#" id="fileSelect">Select new image (.png, .jpg or .jpeg) - Size less than 2MB</a>
                                        <div class="invalid-feedback" id="fileErr">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input 
                                            name="txtFileLink"
                                            type="hidden" 
                                            class="form-control" 
                                            id="cake-image"
                                            value="${product.image}"
                                            />
                                    </div>
                                    <div id="previewImageContainer" class="col-md-4">
                                        <div id="progress" class="progress">
                                            <div id="progress-bar" class="progress-bar progress-bar-striped bg-success" role="progressbar"></div>
                                        </div>
                                        <div id="previewImage" class="d-flex justify-content-center align-items-center mt-3">
                                            <img src="${product.image}" width="100%"/>
                                        </div>
                                        <a id="deleteImage" href="#" class="d-flex justify-content-center align-items-center text-danger">Delete image</a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input 
                                        name="txtId" 
                                        type="hidden" 
                                        class="form-control" 
                                        id="cake-id"
                                        value="${product.productId}"
                                        />
                                </div>
                                <div class="form-group">
                                    <input 
                                        name="page" 
                                        type="hidden" 
                                        class="form-control" 
                                        value="${param.page}"
                                        />
                                </div>
                                <div class="form-group col-sm-3 mx-auto">
                                    <button type="submit" class="btn btn-block btn-primary text-light">
                                        Update cake
                                    </button>
                                </div>
                            </form>
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
        </div>
        <jsp:include page="templates/admin/footer.html" flush="true" />
        <script src="js/uploadimage.js"></script>
    </body>
</html>
