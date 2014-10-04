<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="header.jsp" />


<div class="container">

	<div id="main" class="container login-box well">

		<c:url value="/login" var="url" />
		<form action="${url}" method="post">
			<h3 class="text-center">Signing in</h3>

			<c:if test="${param.error!=null}">
				<div class="alert alert-danger margin-top">
					<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</c:if>
				</div>
			</c:if>

			<c:if test="${param.logout!=null}">
				<div class="alert alert-success margin-top">Logged out
					successfully</div>
			</c:if>

			<c:if test="${param.reg!=null}">
				<div class="alert alert-success margin-top">You have signed up
					successfully. Please, sign in.</div>
			</c:if>

			<c:if test="${param.account_updated!=null}">
				<div class="alert alert-success">
					<spring:message code="accountUpdated" />
				</div>
			</c:if>


			<input type="text" id="username" name="username" class="form-control"
				placeholder="username" autofocus="autofocus"> <input
				type="password" id="password" name="password" class="form-control"
				placeholder="password">

			<button class="btn btn-lg btn-primary btn-block" type="submit">
				<span class="glyphicon glyphicon-user"></span> Sign in
			</button>

			<a href='<c:url value="/registration"/>'
				class="btn btn-default btn-lg btn-block"> <span
				class="glyphicon glyphicon-pencil"></span> Sign up
			</a>
		</form>

	</div>

</div>


<jsp:include page="footer.jsp" />


