<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="header.jsp" />




<div id="main" class="container-fluid">


	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<spring:message code="signingUp" />
			</h3>
		</div>
		<div class="panel-body">

			<form class="form-horizontal" role="form">


				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.username" /></label>
					<div class="col-sm-7">
						<input type="text" field-id="user.id.userId" class="form-control" />


					</div>
				</div>


				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.givenName" /></label>
					<div class="col-sm-7">
						<input type="text" field-id="user.name" class="form-control" />

					</div>
				</div>


				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.familyName" /></label>
					<div class="col-sm-7">
						<input type="text" field-id="user.surname" class="form-control" />


					</div>
				</div>



				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.password" /></label>
					<div class="col-sm-7">
						<input type="password" field-id="user.password"
							class="form-control" />

					</div>
				</div>



				<div class="form-group text-center">
					<button type="button" id="register_user_button"
						class="btn btn-success">
						<span class="glyphicon glyphicon-pencil"></span>
						<spring:message code="signUp" />
					</button>
				</div>
			</form>

		</div>

	</div>

</div>


<jsp:include page="footer.jsp" />


