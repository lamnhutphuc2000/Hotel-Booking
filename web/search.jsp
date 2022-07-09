<%-- 
    Document   : search
    Created on : Dec 16, 2020, 3:17:24 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hotel's rooms</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <h1>Welcome to Chop Chep's Hotel</h1>
        <c:set var="googleAccount" value="${sessionScope.googleAccount}"/> 
        <c:set var="account" value="${sessionScope.account}"/>
        <c:set var="cart" value="${sessionScope.cart}"/>
        <c:set var="role" value="${sessionScope.role}"/>
        <c:if test="${not empty googleAccount}">
            <form action="bookingHistory">
                <input type="submit" value="Watch booking history" />
            </form>
            <h2>Welcome, ${googleAccount.email}</h2>
        </c:if>
        <c:if test="${not empty account }">
            <c:if test="${role ne 'Admin'}">
                <form action="bookingHistory">
                    <input type="submit" value="Watch booking history" />
                </form>
            </c:if>

            <h2>Welcome, ${account.name}</h2>
        </c:if>
        <c:if test="${empty account && empty googleAccount}">
            <a href="loginPage">Login</a>
        </c:if>
        <c:if test="${not empty account || not empty googleAccount}">
            <form action="logout">
                <input type="submit" value="Logout" />
            </form>
        </c:if>
        <c:set var="typeOfRooms" value="${requestScope.typeOfRooms}"/>
        <c:if test="${not empty cart}">

            <form action="cartPage">
                <input type="submit" value="Vew cart" />
            </form>
        </c:if>
        <c:set var="searchError" value="${requestScope.searchError}"/>
        <form action="searchRoom"> 

            Check in date: <input type="text" name="txtCheckInDate" value="${param.txtCheckInDate}" /> <br>
            <c:if test="${not empty searchError.checkInDateFormatError}">
                <div class="text-danger">${searchError.checkInDateFormatError}</div>
            </c:if>  
            <c:if test="${not empty searchError.checkInDateBeforeNowError}">
                <div class="text-danger">${searchError.checkInDateBeforeNowError}</div>
            </c:if>  



            Check out date: <input type="text" name="txtCheckoutDate" value="${param.txtCheckoutDate}" /> <br>
            <c:if test="${not empty searchError.checkOutDateFormatError}">
                <div class="text-danger">${searchError.checkOutDateFormatError}</div>
            </c:if> 
            <c:if test="${not empty searchError.checkOutBeforeCheckInError}">
                <div class="text-danger">${searchError.checkOutBeforeCheckInError}</div>
            </c:if> 
            <c:if test="${not empty searchError.checkOutDateBeforeNowtError}">
                <div class="text-danger">${searchError.checkOutDateBeforeNowtError}</div>
            </c:if> 

            <c:if test="${not empty typeOfRooms}">
                Type of room:
                <select name="txtTypeOfRoom"> 
                    <option>All</option> 
                    <c:forEach var="typeOfRoom" step="1" items="${typeOfRooms}">  
                        <c:if test="${param.txtTypeOfRoom == typeOfRoom.typeOfRoomName}">
                            <option selected>${typeOfRoom.typeOfRoomName}</option>  
                        </c:if>
                        <c:if test="${param.txtTypeOfRoom ne typeOfRoom.typeOfRoomName}">
                            <option>${typeOfRoom.typeOfRoomName}</option>  
                        </c:if> 

                    </c:forEach>
                </select> 
            </c:if> 

            <input type="submit" value="Search" />
        </form>


        <c:set var="listRoom" value="${requestScope.listRoom}"/>
        <c:forEach var="room" step="1" items="${listRoom}">
            <form action="addRoomToCart">
                <img src="${room.image}"/></br> 
                <div>
                    Room ID: ${room.roomID}
                </div> 
                <div>
                    Price: ${room.price} 
                </div> 
                <c:if test="${not empty googleAccount || not empty account}">
                    <input type="hidden" name="txtRoomID" value="${room.roomID}" /> 
                    <c:forEach var="typeOfRoom" step="1" items="${typeOfRooms}">
                        <c:if test="${typeOfRoom.typeOfRoomID == room.typeOfRoomID}">
                            <input type="hidden" name="txtTypeOfRoomName" value="${typeOfRoom.typeOfRoomName}" />
                        </c:if>
                    </c:forEach>
                    <input type="hidden" name="txtRoomprice" value="${room.price}" />
                    <input type="hidden" name="txtCheckInDate" value="${param.txtCheckInDate}" />
                    <input type="hidden" name="txtCheckoutDate" value="${param.txtCheckoutDate}" /> 
                    <input type="hidden" name="txtTypeOfRoom" value="${param.txtTypeOfRoom}" /> 
                    <c:if test="${role ne 'Admin'}">
                        <input type="submit" value="Add" />
                    </c:if>
                </c:if> 
            </form>
            <div></div>
            <div></div>
            <div></div>
        </c:forEach>
    </body>
</html>
