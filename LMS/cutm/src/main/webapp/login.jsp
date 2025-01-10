<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<%@include file="all_component/all_css.jsp"%>
<style type="text/css">
.back-video {
    position: fixed;
    right: 0;
    bottom: 0;
    min-width: 100%;
    min-height: 100%;
    z-index: -1;
}
</style>
</head>
<body style="background-color: #f0f1f2;">
    <%@include file="all_component/navbar.jsp"%>
    <div class="container-fluid">
        <div class="row p-4">
            <div class="col-md-4 offset-md-4">
                <video autoplay loop muted playsinline class="back-video">
                    <source src="img/video.mp4" type="video/mp4">
                </video>
                <div class="card bg-dark text-white opacity-75" style="box-shadow: 8px 9px 19px -10px rgba(33, 37, 41, 1);">
                    <div class="card-body">
                        <div class="text-center">
                            <i class="fa fa-user-plus fa-2x" aria-hidden="true"></i>
                            <h5>Login Page</h5>
                        </div>
                        <c:if test="${not empty succMsg}">
                            <h4 class="text-center text-success">${succMsg}</h4>
                            <c:remove var="succMsg" />
                        </c:if>
                        <c:if test="${not empty errMsg}">
                            <h4 class="text-center text-danger">${errMsg}</h4>
                            <c:remove var="errMsg" />
                        </c:if>
                        <form action="login" method="post">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Enter Email</label>
                                <input type="email" required class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="email" aria-label="Email">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Enter Password</label>
                                <input type="password" required class="form-control" id="exampleInputPassword1" name="password" aria-label="Password">
                            </div>
                            <button type="submit" class="btn btn-primary badge-pill btn-block">Login</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
