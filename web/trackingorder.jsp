<%-- 
    Document   : trackingorder
    Created on : Oct 9, 2020, 1:05:53 AM
    Author     : ThinhTPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Tracking | YMS</title>
        <link rel="stylesheet" href="css/trackingorder.css" />
    </head>
    <body>
        <fmt:setLocale value="vi_VN" />
        <jsp:include page="templates/web/header.jsp" flush="true">
            <jsp:param name="currentPage" value="Tracking" />
        </jsp:include>
        
        <!--CONTAINER-->
        <div class="container-fluid my-4">
            <div class="col-md-8 mx-auto">
                <c:if test="${not empty sessionScope.USER_NAME}">
                    <div class="row my-4">
                        <form class="col-md-6 my-lg-0 mx-auto" action="trackOrder" method="POST">
                            <div class="form-row">
                                <div class="form-group col-md-9">
                                    <input
                                        class="form-control mr-sm-2"
                                        type="search"
                                        placeholder="Enter your order ID"
                                        aria-label="Search"
                                        name="txtOrderId"
                                        value="${param.txtOrderId}"
                                        required
                                        />
                                </div>
                                <div class="form-group" >
                                    <button class="btn btn-warning" type="submit">
                                        <i class="fas fa-search"></i>
                                        <strong>
                                            Find
                                        </strong> 
                                    </button>
                                </div>
                            </div>  
                        </form>
                    </div>
                </c:if>

                <c:if test="${not empty requestScope.CHECKOUT_MSG}">
                    <div class="alert alert-success alert-dismissible fade show mt-2" role="alert">
                        <strong>${requestScope.CHECKOUT_MSG}</strong>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <c:if test="${not empty requestScope.ERROR_MSG}">
                    <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
                        <strong>${requestScope.ERROR_MSG}</strong>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <c:if test="${not empty param.txtOrderId}">
                    <c:if test="${empty requestScope.ERROR_MSG}">
                        <c:set value="${requestScope.ORDER_INFO}" var="orderInfo" />
                        <div class="row bg-light p-3 rounded-top">
                            <div class="col-md-12">
                                <h3><strong>Order ID: ${orderInfo.orderId}</strong></h3>
                                <strong>Receive's info</strong>
                                <ul>
                                    <li>Name: ${orderInfo.userName}</li>
                                    <li>Phone: ${orderInfo.userPhone}</li>
                                    <li>Shipping address: ${orderInfo.userAddress}</li>
                                </ul>
                                <strong>Payment method:</strong>
                                <span>${requestScope.METHOD_NAME}</span>
                                <br>
                                <strong>Payment status:</strong>
                                <span class="badge
                                      <c:set value="${requestScope.STATUS_NAME}" var="statusName" />
                                      <c:choose>
                                          <c:when test='${statusName eq "Success"}'>badge-success</c:when>
                                          <c:when test='${statusName eq "Pending"}'>badge-primary</c:when>
                                          <c:when test='${statusName eq "Error"}'>badge-danger</c:when>
                                          <c:when test='${statusName eq "Cancel"}'>badge-danger</c:when>
                                      </c:choose>
                                      ">
                                    ${statusName}
                                </span>
                                <br/>
                                <strong>Order Date:</strong>
                                <span>
                                    <fmt:formatDate value = "${orderInfo.date}"/>
                                </span>
                            </div>
                        </div>
                        <div class="row bg-light p-3  rounded-bottom">
                            <div class="col-md-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><strong>Order summary</strong></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="table-responsive">
                                            <table class="table table-condensed">
                                                <thead>
                                                    <tr>
                                                        <td><strong>Item</strong></td>
                                                        <td class="text-center"><strong>Price</strong></td>
                                                        <td class="text-center"><strong>Quantity</strong></td>
                                                        <td class="text-right"><strong>Totals</strong></td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:set value="${requestScope.LIST_ITEMS}" var="listItem" />
                                                    <c:forEach items="${listItem}" var="item">
                                                        <tr>
                                                            <td>${item.productName}</td>
                                                            <td class="text-center">
                                                                <fmt:formatNumber value="${item.price}" type="currency"/>
                                                            </td>
                                                            <td class="text-center">${item.quantity}</td>
                                                            <td class="text-right">
                                                                <fmt:formatNumber value="${item.subTotal}" type="currency"/>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    <tr>
                                                        <td class="no-line"></td>
                                                        <td class="no-line"></td>
                                                        <td class="no-line text-center"><strong>Total</strong></td>
                                                        <td class="no-line text-right">
                                                            <fmt:formatNumber value="${orderInfo.total}" type="currency"/>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </div>

        <jsp:include page="templates/web/footer.html" flush="true" />
    </body>
</html>
