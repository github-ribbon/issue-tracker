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
				<spring:message code="projectDetails" />
			</h3>
		</div>
		<div class="panel-body">

			<c:if test="${param.created!=null}">
				<div class="alert alert-success text-center">Project created
					successfully</div>
			</c:if>

			<c:if test="${param.updated!=null}">
				<div class="alert alert-success text-center">Project updated
					successfully</div>
			</c:if>

			<c:choose>
				<c:when test="${not empty projectDTO.project}">
					<form class="form-horizontal" role="form">


						<div class="form-group">
							<label for="" class="col-sm-3 control-label"><spring:message
									code="label.name" /></label>
							<div class="col-sm-7">

								<div class="value-box">
									<c:out
										value="${projectDTO.project.id.ownerId}/${projectDTO.project.id.projectId}" />
								</div>
								<input type="hidden" field-id="project.id.projectId"
									value="<c:out value="${projectDTO.project.id.projectId}" />" />
							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label"><spring:message
									code="label.description" /></label>
							<div class="col-sm-7">

								<c:choose>
									<c:when
										test="${projectDTO.project.id.ownerId==pageContext.request.userPrincipal.name}">
										<textarea field-id="project.description"  rows="8" class="form-control"><c:out
												value="${projectDTO.project.description}" /></textarea>
									</c:when>
									<c:otherwise>
										<div class="value-box">
											<c:out value="${projectDTO.project.description}" />
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="form-group text-center">

							<a
								href='<c:url value="/issues/new?owner_id=${projectDTO.project.id.ownerId}&project_id=${projectDTO.project.id.projectId}" />'
								class="btn btn-success"> <span
								class="glyphicon glyphicon-plus"></span> Report issue
							</a> <a
								href='<c:url value="/issues/list?owner_id=${projectDTO.project.id.ownerId}&project_id=${projectDTO.project.id.projectId}" />'
								class="btn btn-info"> <span class="glyphicon glyphicon-flag"></span>
								Issues
							</a>


							<c:if
								test="${projectDTO.project.id.ownerId==pageContext.request.userPrincipal.name}">

								<button type="button" id="save_project_button"
									class="btn btn-primary">
									<span class="glyphicon glyphicon-pencil"></span>
									<spring:message code="save" />
								</button>

								<button type="button" id="delete_project_button"
									class="btn btn-danger">
									<span class="glyphicon glyphicon-trash"></span> Delete
								</button>
							</c:if>
						</div>
					</form>



					<c:if test="${param.version_created!=null}">
						<div class="alert alert-success text-center">Version added
							successfully</div>
					</c:if>

					<c:if test="${param.version_deleted!=null}">
						<div class="alert alert-success text-center">Version removed
							successfully</div>
					</c:if>


					<c:if
						test="${projectDTO.project.id.ownerId==pageContext.request.userPrincipal.name}">
						<div class="containter">

							<form class="form-inline text-center" role="form">

								<input type="hidden" field-id="version.id.projectId"
									value="<c:out value="${projectDTO.project.id.projectId}" />" />

								<div class="form-group">
									<input type="text" field-id="version.id.versionId"
										placeholder="Version" class="form-control" />
								</div>

								<div class="form-group text-center">
									<button type="button" id="add_version_button"
										class="btn btn-success">
										<span class="glyphicon glyphicon-plus"></span>
										<spring:message code="add" />
									</button>
								</div>
							</form>


						</div>
					</c:if>



					<div class="form-horizontal margin-top-small">
						<div class="form-group">
							<label class="col-sm-3 control-label">Versions</label>
							<div class="col-sm-7">


								<c:choose>
									<c:when test="${not empty projectDTO.project.versions}">


										<c:forEach items="${projectDTO.project.versions}" var="item">



											<c:if
												test="${projectDTO.project.id.ownerId==pageContext.request.userPrincipal.name}">
												<form action="#"
													class="delete_version_form form-inline display-inline-block"
													method="get" role="form">
											</c:if>
											<span> <span class="badge"> <c:out
														value="${item.id.versionId}" /></span> <c:if
													test="${projectDTO.project.id.ownerId==pageContext.request.userPrincipal.name}">
													<input type="hidden" list-field-id="version.id.projectId"
														value="<c:out value="${item.id.projectId}" />" />
													<input type="hidden" list-field-id="version.id.versionId"
														value="<c:out value="${item.id.versionId}" />" />
													<input type="hidden" list-field-id="version.id.ownerId"
														value="<c:out value="${item.id.ownerId}" />" />

													<button type="submit"
														class="btn btn-danger btn-sm text-center delete_version_button">
														<span class="glyphicon glyphicon-remove"></span>
													</button>
												</c:if>
											</span>

											<c:if
												test="${projectDTO.project.id.ownerId==pageContext.request.userPrincipal.name}">

												</form>
											</c:if>

										</c:forEach>

									</c:when>
									<c:otherwise>
										<div class="value-box">No versions</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="text-center">

						<c:if test="${param.fixed_version_created!=null}">
							<div class="alert alert-success text-center">Fixed version
								added successfully</div>
						</c:if>

						<c:if test="${param.fixed_version_deleted!=null}">
							<div class="alert alert-success text-center">Fixed version
								removed successfully</div>
						</c:if>



					</div>


				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">Project not found</div>
				</c:otherwise>

			</c:choose>

		</div>

	</div>

</div>


<jsp:include page="footer.jsp" />


