<%-- 
    Document   : signupPage
    Created on : Dec 16, 2020, 4:28:04 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signup</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body> 
        <c:set var="createNewError" value="${requestScope.createNewError}"/>

        <h1>Create your account</h1>
        <form action="signup">
            Email <input type="text" name="txtEmail" value="${param.txtEmail}" /> <br>
            <c:if test="${not empty createNewError.emailFormatError}">
                <div class="text-danger">${createNewError.emailFormatError}</div>
            </c:if>
            <c:if test="${not empty createNewError.emailDuplicateError}">
                <div class="text-danger">${createNewError.emailDuplicateError}</div>
            </c:if>
            <c:if test="${not empty createNewError.emailEmptyError}">
                <div class="text-danger">${createNewError.emailEmptyError}</div>
            </c:if>
            Phone <input type="text" name="txtPhone" value="${param.txtPhone}" /> <br>
            <c:if test="${not empty createNewError.phoneFormatError}">
                <div class="text-danger">${createNewError.phoneFormatError}</div>
            </c:if>
            <c:if test="${not empty createNewError.phoneLengthError}">
                <div class="text-danger">${createNewError.phoneLengthError}</div>
            </c:if>
            Name <input type="text" name="txtName" value="${param.txtName}" /> <br>
            <c:if test="${not empty createNewError.nameLengthError}">
                <div class="text-danger">${createNewError.nameLengthError}</div>
            </c:if>
            Address: <input type="text" name="txtAddress" value="${param.txtAddress}" /> <br>
            <c:if test="${not empty createNewError.addressLengthError}">
                <div class="text-danger">${createNewError.addressLengthError}</div>
            </c:if>
            Password <input type="password" name="txtPassword" value="" /> <br>
            <c:if test="${not empty createNewError.passwordLengthError}">
                <div class="text-danger">${createNewError.passwordLengthError}</div>
            </c:if>
            Confirm password <input type="password" name="txtConfirmPassword" value="" /> <br>
            <c:if test="${not empty createNewError.confirmPasswordLengthError}">
                <div class="text-danger">${createNewError.confirmPasswordLengthError}</div>
            </c:if>
            <c:if test="${not empty createNewError.confirmPasswordNotMatchedError}">
                <div class="text-danger">${createNewError.confirmPasswordNotMatchedError}</div>
            </c:if>
            <input type="submit" value="Sign up"/>
        </form>
        Already have your own account ?<a href="loginPage">Click here!</a>
    </body>
</html>
