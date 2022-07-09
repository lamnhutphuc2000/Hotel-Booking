<%-- 
    Document   : cartPage
    Created on : Dec 18, 2020, 4:35:30 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your cart</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>


        <c:set var="cart" value="${sessionScope.cart}"/> 
        <c:if test="${not empty cart}">
            <c:set var="itemInCart" value="${cart.cart}"/>
            <c:if test="${not empty itemInCart}">


                <h1>Your Cart</h1>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>RoomID</th> 
                            <th>Prices</th>  
                            <th>Removes</th>
                        </tr>
                    </thead>
                    <c:set var="sumOfMoney" value="0"/>

                    <tbody>
                        <c:set var="updateQuantityError" value="${requestScope.updateQuantityError}"/>
                        <c:forEach var="item" items="${itemInCart}" varStatus="counter">
                        <form action="removeRoomFromCart">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${item.roomID}</td>


                                <td>${item.price} $</td> 
                                <c:set var="sumOfMoney" value="${sumOfMoney + item.price }"/>


                                <td> 
                                    <input type="hidden" name="txtRoomID" value="${item.roomID}" />
                                    <input type="submit" value="Remove this room" onclick="return confirm('Are you sure you want to remove this book?')" />
                                </td>
                            </tr> 
                        </form>
                    </c:forEach> 
                </tbody> 

            </table>
            <h3>Total: ${sumOfMoney} $</h3>

            <c:set var="checkoutError" value="${requestScope.checkoutError}"/>
            <form action="checkout">
                <input type="hidden" name="txtTotalPrice" value="${sumOfMoney}" />
                Check in date: <input type="text" name="txtCheckInDate" value="${param.txtCheckInDate}" /> <br>

                <c:if test="${not empty checkoutError.checkInDateFormatError}">
                    <div class="text-danger">${checkoutError.checkInDateFormatError}</div>
                </c:if>  
                <c:if test="${not empty checkoutError.checkInDateBeforeNowError}">
                    <div class="text-danger">${checkoutError.checkInDateBeforeNowError}</div>
                </c:if>  

                Check out date: <input type="text" name="txtCheckoutDate" value="${param.txtCheckoutDate}" /> <br>

                <c:if test="${not empty checkoutError.checkOutDateFormatError}">
                    <div class="text-danger">${checkoutError.checkOutDateFormatError}</div>
                </c:if> 
                <c:if test="${not empty checkoutError.checkOutBeforeCheckInError}">
                    <div class="text-danger">${checkoutError.checkOutBeforeCheckInError}</div>
                </c:if> 
                <c:if test="${not empty checkoutError.checkOutDateBeforeNowtError}">
                    <div class="text-danger">${checkoutError.checkOutDateBeforeNowtError}</div>
                </c:if> 
                <c:if test="${not empty checkoutError.roomOutOfStockError}">
                    <div class="text-danger">${checkoutError.roomOutOfStockError}</div>
                </c:if> 

                <input type="submit" value="Checkout" />
            </form> 
            <c:set var="checkoutError" value="${requestScope.checkoutError}"/> 

        </c:if>
    </c:if>
    <c:if test="${ empty itemInCart}">
        <h1>Your cart is empty</h1>
    </c:if>
    Back to <a href="">Hotel</a> !
</body>
</html>
