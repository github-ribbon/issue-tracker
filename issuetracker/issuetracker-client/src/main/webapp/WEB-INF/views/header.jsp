<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Issue Tracker</title>

<!-- Bootstrap -->
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">
<!-- Custom styles -->
<link href="<c:url value="/resources/css/styles.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/js/bootstrap-select.css" />"
	rel="stylesheet">


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>




	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span
					class="glyphicon glyphicon-flag"></span> Issue Tracker</a>
			</div>



			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<ul class="nav navbar-nav">
					<sec:authorize access="authenticated">

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span class="glyphicon glyphicon-th"></span>
								Projects <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href='<c:url value="/projects/list?owner_id=${pageContext.request.userPrincipal.name}"></c:url>'>My
										projects </a></li>
								<li><a href='<c:url value="/projects/list"></c:url>'>All
										projects </a></li>
							</ul></li>


						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span class="glyphicon glyphicon-th"></span>
								Issues <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href='<c:url value="/issues/list?reporter=${pageContext.request.userPrincipal.name}"></c:url>'>Reported
										by me</a></li>
								<li><a
									href='<c:url value="/issues/list?assignee=${pageContext.request.userPrincipal.name}"></c:url>'>Assigned
										to me</a></li>
								<li><a
									href='<c:url value="/issues/list?owner_id=${pageContext.request.userPrincipal.name}"></c:url>'>Affecting
										my projects</a></li>
								<li><a href='<c:url value="/issues/list"></c:url>'>All
										issues</a></li>
							</ul></li>
					</sec:authorize>
					<li><a href="#"><span
							class="glyphicon glyphicon-info-sign"></span> Help</a></li>
				</ul>




				<sec:authorize access="hasRole('ROLE_USER')">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">

							<div class="navbar-form btn-group">
								<button class="btn btn-success">
									<span class="glyphicon glyphicon-user"></span>
									<sec:authentication property="principal.name" />
								</button>
								<button data-toggle="dropdown"
									class="btn btn-success dropdown-toggle">
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a href='<c:url value="/account" />'><span
											class="glyphicon glyphicon-cog"></span> Edit account</a></li>
									<li><a href='<c:url value="/logout"/>'><span
											class="glyphicon glyphicon-off"></span> Sign out</a></li>
								</ul>
							</div>


						</li>

					</ul>
				</sec:authorize>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div id="form_ajax_loader">
		<div class="progress progress-striped active">
			<div class="progress-bar progress-bar-success">Loading...</div>
		</div>
	</div>