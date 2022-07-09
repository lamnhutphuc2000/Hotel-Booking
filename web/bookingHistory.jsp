<%-- 
    Document   : bookingHistory
    Created on : Dec 18, 2020, 6:30:39 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking History</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:set var="role" value="${sessionScope.role}"/>
        <c:if test="${empty role}">
            <c:redirect url=""/>
        </c:if>
        <c:if test="${role == 'Admin'}">
            <c:redirect url=""/>
        </c:if>

        <c:set var="bookingHistoryError" value="${requestScope.bookingHistoryError}"/>
        <h1>This is your Booking History</h1>
        <form action="bookingHistory">
            Name: <input type="text" name="txtBookingHistoryName" value="${param.txtBookingHistoryName}" /> </br>
            Shopping date : <input type="text" name="txtBookingHistoryDate" value="${param.txtBookingHistoryDate}" /> </br>
            <c:if test="${not empty bookingHistoryError.dateFormatError}">
                <div class="text-danger"> ${bookingHistoryError.dateFormatError}  </div> </br>
            </c:if>
            <input type="submit" value="Search history"/>
        </form>
        Back to           <a href="">Hotel's page</a>
        <c:set var="listBooking" value="${requestScope.listBooking}"/>
        <c:set var="listBookingDetail" value="${requestScope.listBookingDetail}"/>


        <c:set var="feedbackError" value="${requestScope.feedbackError}"/>
                        <c:if test="${not empty feedbackError.dateNotYetError}">
                            <div class="text-danger">${feedbackError.dateNotYetError}</div>
                        </c:if>  
        <c:forEach var="booking" items="${listBooking}" varStatus="counter"> 
            
                <c:forEach var="bookingDetail" items="${listBookingDetail}" varStatus="counter">
                <c:if test="${booking.bookingID == bookingDetail.bookingID}">     
                        <div> BookingID :  ${booking.bookingID}  </div> 
                        <div>  totalPrice: ${booking.price}  $   </div> 
                        <div>  date: ${booking.bookingDate}  </div> </br>
                        Booking detail
                     

                     
                        <div> Type of room: ${bookingDetail.typeOfRoom}  </div>  
                        <div> Price: ${bookingDetail.price}  $</div> 
                     
                    <c:if test="${bookingDetail.rating == null}">
                        <form action="sendFeedBack">
                            <input type="hidden" name="txtBookingID" value="${booking.bookingID}" />
                            <input type="hidden" name="txtRoomID" value="${bookingDetail.roomID}" />
                            <select name="txtFeedBack"> 
                                <option>0</option>  
                                <option>1</option>  
                                <option>2</option>  
                                <option selected>3</option>  
                                <option>4</option>  
                                <option>5</option>  
                            </select> 

                            <input type="submit" value="Send feedback" />
                        </form>
                    </c:if>

                    </br>
                    </c:if>
                </c:forEach>
                    <form action="deleteBookingHistory">
                <input type="hidden" name="txtBookingID" value="${booking.bookingID}" />
                <input type="hidden" name="txtUserID" value="${booking.userID}" />
                <input type="submit" value="Delete this history" />
            </form>
            
            
            
            </br>
            </br>
            </br>
        </c:forEach> 
    </body>
</html>
