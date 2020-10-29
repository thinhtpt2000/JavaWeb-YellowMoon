<%-- 
    Document   : cart
    Created on : Oct 6, 2020, 11:59:13 PM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart | YMS</title>
    </head>
    <body>
        <!--HEADER-->
        <jsp:include page="templates/web/header.jsp" flush="true">
            <jsp:param name="currentPage" value="Cart" />
        </jsp:include>

        <!--CONTAINER-->
        <div class="container my-4">
            <div class="row">
                <div class="col-md-8 mx-auto">
                    <c:if test="${not empty requestScope.CHECKOUT_ERR}">
                        <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
                            <strong>${requestScope.CHECKOUT_ERR}</strong>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.CHECKOUT_CANCEL}">
                        <div class="alert alert-info alert-dismissible fade show mt-2" role="alert">
                            <strong>${requestScope.CHECKOUT_CANCEL}</strong>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.CART_INFO}">
                        <div class="alert alert-info alert-dismissible fade show mt-2" role="alert">
                            <strong>${requestScope.CART_INFO}</strong>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${empty sessionScope.CART.items}">
                        <div class="alert alert-info fade show" role="alert">
                            <h4>Oops, your cart is empty</h4>
                            <a href="searchPage">&larr; Buy some cakes now !!!</a>
                        </div>
                    </c:if>
                </div>
            </div>
            <c:if test="${not empty sessionScope.CART.items}">
                <c:set value="${sessionScope.CART}" var="cartObject" />
                <fmt:setLocale value = "vi_VN"/>
                <div class="row">
                    <div class="col-12">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col"  class="text-right">No.</th>
                                        <th scope="col">Product</th>
                                        <th scope="col" class="text-center">Quantity</th>
                                        <th scope="col" class="text-right">Price</th>
                                        <th scope="col" class="text-right">Sub-total</th>
                                        <th class="text-right">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${cartObject.items}" var="cart" varStatus="counter">
                                        <tr>
                                    <form class="needs-validation" 
                                          id='update-form-${cart.key}'
                                          action="updateCart" 
                                          method="POST">
                                        <input 
                                            class="form-control" 
                                            type="hidden" 
                                            name="txtId"
                                            value="${cart.key}" 
                                            />
                                        <td  class="text-right">${counter.count}</td>
                                        <td>${cart.value.name}</td>
                                        <td>
                                            <input 
                                                class='form-control 
                                                <c:if test="${not empty cart.value.cartErr}">
                                                    is-invalid
                                                </c:if>
                                                ' 
                                                type="number"
                                                name="txtAmount"
                                                value="${cart.value.amount}" 
                                                min="1"
                                                target-id="${cart.key}"
                                                />
                                            <c:if test="${not empty cart.value.cartErr}">
                                                <div class="invalid-feedback">
                                                    ${cart.value.cartErr}
                                                </div>
                                            </c:if>
                                        </td>
                                        <td  class="text-right">
                                            <fmt:formatNumber value="${cart.value.price}" type="currency"/>
                                        </td>
                                        <td class="text-right">
                                            <fmt:formatNumber value="${cart.value.subTotal}" type="currency"/>
                                        </td>
                                        <td class="text-right">
                                            <button class="btn btn-sm btn-primary"><i class="fas fa-edit"></i> Edit</button>
                                            <a class="btn btn-sm btn-danger deleteItem" 
                                               id="delete-btn-${cart.key}" 
                                               href="#" 
                                               target="delete-form-${cart.key}">
                                                <i class="fa fa-trash"></i>
                                                Delete
                                            </a>
                                        </td>
                                    </form>
                                    <form id="delete-form-${cart.key}" action="deleteCart" method="GET">
                                        <input 
                                            class="form-control" 
                                            type="hidden" 
                                            name="txtId"
                                            value="${cart.key}" 
                                            />
                                    </form>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><strong>Total</strong></td>
                                    <td class="text-right">
                                        <strong>
                                            <fmt:formatNumber value = "${cartObject.total}" type = "currency"/>
                                        </strong>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col mb-2">
                        <div class="row">
                            <div class="col-sm-12 col-md-6">
                                <a class="btn btn-block btn-light" href="searchPage">Continue Shopping</a>
                            </div>
                            <div class="col-sm-12 col-md-6 text-right">
                                <a class="btn btn-block btn-warning text-uppercase font-weight-bold" 
                                   href='<c:if test="${cartObject.checkValidCart()}">
                                       checkoutPage
                                   </c:if>'>
                                    <i class="fas fa-money-bill-alt"></i>
                                    Checkout now
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <jsp:include page="templates/web/footer.html" flush="true" />
        <script src="js/cart.js">
        </script>
    </body>
</html>
