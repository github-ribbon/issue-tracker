<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
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
				<spring:message code="accountDetails" />
			</h3>
		</div>
		<div class="panel-body">

			<div class="alert alert-info text-center">
				<spring:message code="accountUpdateInfo" />
			</div>


			<form class="form-horizontal" role="form">


				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.username" /></label>
					<div class="col-sm-7">
						<div class="value-box">
							<c:out value="${userDTO.user.id.userId}" />
						</div>
					</div>
				</div>


				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.givenName" /></label>
					<div class="col-sm-7">
						<input type="text" field-id="user.name" class="form-control"
							value="<c:out
								value="${userDTO.user.name}" />" />

					</div>
				</div>


				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="label.familyName" /></label>
					<div class="col-sm-7">
						<input type="text" field-id="user.surname" class="form-control"
							value="<c:out
								value="${userDTO.user.surname}" />" />


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



					<button type="button" id="save_user_button" class="btn btn-primary">
						<span class="glyphicon glyphicon-pencil"></span>
						<spring:message code="save" />
					</button>


				</div>
			</form>

		</div>

	</div>

</div>


<jsp:include page="footer.jsp" />


