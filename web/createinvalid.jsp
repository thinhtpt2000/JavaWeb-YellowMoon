<%-- 
    Document   : createinvalid
    Created on : Oct 6, 2020, 09:00:41 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Cake | Admin Page</title>
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
                            <c:set value="${requestScope.ERRORS}" var="errors" />
                            <c:set value="${requestScope.LIST_CATEGORIES}" var="listCategories" />
                            <a href="adminPage" class="btn text-dark">
                                <strong>
                                    <i class="fas fa-chevron-left"></i>
                                    <span>Back</span>
                                </strong>
                            </a>
                            <c:if test="${not empty requestScope.MSG}">
                                <div class="alert alert-success alert-dismissible fade show mt-2" role="alert">
                                    <strong>${requestScope.MSG}</strong>
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </c:if>
                            <form class="needs-validation" action="createCake" method="POST">
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="cake-name" class="col-form-label">Name</label>
                                        <input  
                                            name="txtName" 
                                            type="text" 
                                            class='form-control
                                            <c:if test="${not empty errors.nameErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.nameErr && not empty param.txtName}">
                                                is-valid
                                            </c:if>
                                            '
                                            id="cake-name" 
                                            value="${param.txtName}"
                                            placeholder="(from 10 to 100 chars)"
                                            required
                                            minlength="10"
                                            maxlength="100"
                                            />
                                        <c:if test="${not empty errors.nameErr}">
                                            <div class="invalid-feedback">
                                                ${errors.nameErr}
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cake-price" class="col-form-label">Price (VND)</label>
                                        <input 
                                            name="txtPrice" 
                                            type="number" 
                                            class='form-control
                                            <c:if test="${not empty errors.priceErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.priceErr && not empty param.txtPrice}">
                                                is-valid
                                            </c:if>
                                            '
                                            id="cake-price" 
                                            value="${param.txtPrice}"
                                            placeholder="(VND)"
                                            required
                                            min="50000"
                                            max="5000000"
                                            step="1000"
                                            />
                                        <c:if test="${not empty errors.priceErr}">
                                            <div class="invalid-feedback">
                                                ${errors.priceErr}
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cake-quantity" class="col-form-label">Quantity</label>
                                        <input  
                                            name="txtQuantity" 
                                            type="number" 
                                            class='form-control
                                            <c:if test="${not empty errors.quantityErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.quantityErr && not empty param.txtQuantity}">
                                                is-valid
                                            </c:if>
                                            '
                                            id="cake-quantity" 
                                            value="${param.txtQuantity}"
                                            placeholder="(cái)"
                                            required
                                            min="0"
                                            max="5000"
                                            />
                                        <c:if test="${not empty errors.quantityErr}">
                                            <div class="invalid-feedback">
                                                ${errors.quantityErr}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-12">
                                        <label for="cake-description" class="col-form-label">Description</label>
                                        <input  
                                            name="txtDescription" 
                                            value="${param.txtDescription}"
                                            type="text" 
                                            class='form-control
                                            <c:if test="${not empty errors.descriptionErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.descriptionErr && not empty param.txtPrice}">
                                                is-valid
                                            </c:if>
                                            '
                                            id="cake-description" 
                                            placeholder="(from 20 to 250 chars)"
                                            required
                                            minlength="20"
                                            maxlength="250"
                                            />
                                        <c:if test="${not empty errors.descriptionErr}">
                                            <div class="invalid-feedback">
                                                ${errors.descriptionErr}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="cake-category" class="col-form-label">Category</label>
                                        <select 
                                            class='form-control
                                            <c:if test="${not empty errors.categoryErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.categoryErr && not empty param.txtCategory}">
                                                is-valid
                                            </c:if>
                                            '
                                            name="txtCategory" 
                                            required 
                                            id="cake-category">
                                            <option value="">Select category</option>
                                            <c:forEach items="${listCategories}" var="category">
                                                <option value="${category.categoryId}" 
                                                        <c:if test="${param.txtCategory eq category.categoryId}">
                                                            selected
                                                        </c:if>
                                                        >${category.name}</option>
                                            </c:forEach>
                                        </select>
                                        <c:if test="${not empty errors.categoryErr}">
                                            <div class="invalid-feedback">
                                                ${errors.categoryErr}
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cake-createDate" class="col-form-label">Create date</label>
                                        <input 
                                            name="txtCreateDate" 
                                            type="date" 
                                            class='form-control
                                            <c:if test="${not empty errors.createDateErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.createDateErr && not empty param.txtCreateDate}">
                                                is-valid
                                            </c:if>
                                            '
                                            id="cake-createDate"
                                            value = "${param.txtCreateDate}"
                                            />
                                        <c:if test="${not empty errors.createDateErr}">
                                            <div class="invalid-feedback">
                                                ${errors.createDateErr}
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cake-expireDate" class="col-form-label">Expire date</label>
                                        <input 
                                            name="txtExpireDate" 
                                            type="date" 
                                            class='form-control
                                            <c:if test="${not empty errors.expDateErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${ empty errors.expDateErr && not empty param.txtExpireDate}">
                                                is-valid
                                            </c:if>
                                            '
                                            id="cake-expireDate"
                                            value = "${param.txtExpireDate}"
                                            />
                                        <c:if test="${not empty errors.expDateErr}">
                                            <div class="invalid-feedback">
                                                ${errors.expDateErr}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group custom-file col-md-8 mb-4">
                                        <input 
                                            id="fileInput" 
                                            type="file" 
                                            class='form-control-file
                                            <c:if test="${not empty errors.imgErr}">
                                                is-invalid
                                            </c:if>
                                            '
                                            accept="image/*" 
                                            style="display:none" >
                                        <a href="#" id="fileSelect">Select new image (.png, .jpg or .jpeg) - Size less than 2MB</a>
                                        <c:if test="${not empty errors.imgErr}">
                                            <div class="invalid-feedback">
                                                ${errors.imgErr}
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <input 
                                            name="txtFileLink"
                                            type="hidden" 
                                            class="form-control" 
                                            id="cake-image"
                                            value="${param.txtFileLink}"
                                            >
                                    </div>
                                    <div id="previewImageContainer" class="col-md-4">
                                        <div id="progress" class="progress">
                                            <div id="progress-bar" class="progress-bar progress-bar-striped bg-success" role="progressbar"></div>
                                        </div>
                                        <div id="previewImage" class="d-flex justify-content-center align-items-center mt-3">
                                            <img src="${param.txtFileLink}" width="100%"/>
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
                                        value="${param.txtId}"
                                        >
                                </div>
                                <div class="form-group">
                                    <input 
                                        name="page" 
                                        type="hidden" 
                                        class="form-control" 
                                        value="${param.page}"
                                        >
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
