<%-- 
    Document   : checkoutinvalid
    Created on : Oct 8, 2020, 10:09:20 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Out | YMS</title>
    </head>
    <body>
        <!--HEADER-->
        <jsp:include page="templates/web/header.jsp" flush="true">
            <jsp:param name="currentPage" value="Cart" />
        </jsp:include>
        <c:set value="${sessionScope.CART}" var="cartObject" />
        <fmt:setLocale value = "vi_VN"/>
        <c:if test="${not empty cartObject.items}">
            <c:set value="${requestScope.USER_INFO}" var="userInfo" />
            <div class="container my-4">
                <div class="row">
                    <div class="col-md-4 order-md-2 mb-4">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Your cart</span>
                            <span class="badge badge-secondary badge-pill">${sessionScope.CART.amount}</span>
                        </h4>
                        <ul class="list-group mb-3">
                            <c:forEach items="${cartObject.items}" var="cart">
                                <li
                                    class="list-group-item d-flex justify-content-between lh-condensed"
                                    >
                                    <div>
                                        <h6 class="my-0">${cart.value.name}</h6>
                                        <small class="text-muted">Qty: ${cart.value.amount}</small>
                                    </div>
                                    <span class="text-muted"><fmt:formatNumber value="${cart.value.price}" type="currency"/></span>
                                </li>
                            </c:forEach>

                            <li class="list-group-item d-flex justify-content-between">
                                <span>Total (VND)</span>
                                <strong><fmt:formatNumber value = "${cartObject.total}" type = "currency"/></strong>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-8 order-md-1">
                        <h4 class="mb-3">Billing address</h4>
                        <c:set value="${requestScope.ERRORS}" var="errors" />
                        <form class="needs-validation" action="checkout" method="POST">
                            <div class="row">
                                <div class="col-md-8 mb-3">
                                    <label for="customer-name">Name</label>
                                    <input
                                        type="text"
                                        class='form-control 
                                        <c:if test="${not empty errors.nameErr}">
                                            is-invalid
                                        </c:if>
                                        <c:if test="${empty errors.nameErr}">
                                            is-valid
                                        </c:if>'
                                        id="customer-name"
                                        placeholder="Receiver's name"
                                        name="txtName"
                                        value="${param.txtName}"
                                        required
                                        maxlength="150"
                                        />
                                    <c:if test="${not empty errors.nameErr}">
                                        <div class="invalid-feedback">
                                            ${errors.nameErr}
                                        </div>
                                    </c:if>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label for="customer-phone">Phone</label>
                                    <input
                                        type="text"
                                        class='form-control 
                                        <c:if test="${not empty errors.phoneErr}">
                                            is-invalid
                                        </c:if>
                                        <c:if test="${empty errors.phoneErr}">
                                            is-valid
                                        </c:if>'
                                        id="customer-phone"
                                        placeholder="Receiver's phone number"
                                        name="txtPhone"
                                        value="${param.txtPhone}"
                                        required
                                        />
                                    <c:if test="${not empty errors.phoneErr}">
                                        <div class="invalid-feedback">
                                            ${errors.phoneErr}
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="customer-address">Address</label>
                                <input
                                    type="text"
                                    class='form-control 
                                    <c:if test="${not empty errors.addressErr}">
                                        is-invalid
                                    </c:if>
                                    <c:if test="${empty errors.addressErr}">
                                        is-valid
                                    </c:if>'
                                    id="customer-address"
                                    placeholder="Receiver's shipping address"
                                    name="txtAddress"
                                    value="${param.txtAddress}"
                                    required
                                    />
                                <c:if test="${not empty errors.addressErr}">
                                    <div class="invalid-feedback">
                                        ${errors.addressErr}
                                    </div>
                                </c:if>
                            </div>
                            <hr class="mb-4" />

                            <h4 class="mb-3">Payment method</h4>

                            <div class="d-block my-3">
                                <c:forEach items="${requestScope.LIST_METHOD}" var="method" varStatus="counter">
                                    <div  class="form-check">
                                        <input 
                                            class='form-check-input <c:if test="${not empty errors.methodErr}">
                                                is-invalid
                                            </c:if>
                                            <c:if test="${empty errors.methodErr}">
                                                is-valid
                                            </c:if>'
                                            type="radio" 
                                            id="method-${method.methodId}" 
                                            name="txtMethod" 
                                            value="${method.methodId}" 
                                            <c:if test="${param.txtMethod eq method.methodId}">
                                                checked
                                            </c:if>
                                            />
                                        <label class="form-check-label" for="method-${method.methodId}">
                                            ${method.name}
                                        </label>
                                        <c:if test="${counter.last}">
                                            <c:if test="${not empty errors.methodErr}">
                                                <div class="invalid-feedback">
                                                    ${errors.methodErr}
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </div>

                                </c:forEach>
                            </div>
                            <hr class="mb-4" />

                            <div class="row">
                                <div class="col-sm-12 col-md-6">
                                    <a class="btn btn-block btn-light" href="viewCart">Back to cart</a>
                                </div>
                                <div class="col-sm-12 col-md-6 text-right">
                                    <button class="btn btn-block btn-warning text-uppercase font-weight-bold" type="submit">
                                        Continue to checkout
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        
        <jsp:include page="templates/web/footer.html" flush="true" />
    </body>
</html>
