<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="header.jsp" />

<c:set var="pager" scope="request" value="${projectDTO.pager}" />

<div id="main" class="container-fluid">

	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<spring:message code="projects" />
			</h3>
		</div>
		<div id="project-list" class="panel-body">



			<c:url var="url" value="/projects/list" />

			<div class="container">
				<form id="project_list_form" action="${url}"
					class="form-horizontal margin-bottom margin-top" method="get">


					<input type="hidden" id="current_page" name="page"
						value="<c:out value="${pager.page}" />" />

					<!-- 
						<div class="form-group col-sm-4">
							<div class="checkbox">
								<label> <input type="checkbox" name="> My own projects
								</label>
							</div>
						</div>
-->

					<div class="row text-center">

						
							<a href='<c:url value="/projects/new"/>' class="btn btn-success"><span
								class="glyphicon glyphicon-plus icon-black"></span> <spring:message
									code="add" /></a>
						
					</div>

					<div class="row">




						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="project_list_order_selector" name="sort">
								<option value="">- Order by</option>
								<option value="id.projectId,ASC"
									<c:if test="${pager.sort.equals('id.projectId,ASC')}"> selected="selected" </c:if>>
									Name ascending</option>
								<option value="id.projectId,DESC"
									<c:if test="${pager.sort.equals('id.projectId,DESC')}"> selected="selected" </c:if>>
									Name descending</option>
								<option value="description,ASC"
									<c:if test="${pager.sort.equals('description')||pager.sort.equals('description,ASC')}"> selected="selected" </c:if>>
									Description ascending</option>
								<option value="description,DESC"
									<c:if test="${pager.sort.equals('description,DESC')}"> selected="selected" </c:if>>
									Description descending</option>
							</select>
						</div>

						<div id="page_size_container">

							<div class='col-md-4 text-center padding-small'>
								<select name="size" class='selectpicker show-tick'
									id='project_list_page_size_selector'>

									<option value="">- Elements per page</option>

									<c:forEach var="i" begin="0" step="5" varStatus=""
										end="${pager.totalElements}">

										<c:if test="${i>0}">
											<option value="${i}"
												<c:if test="${i==pager.size}"> selected="selected"</c:if>>${i}</option>
										</c:if>

									</c:forEach>


									<c:if test="${pager.totalPages<20}">
										<option value="20"
											<c:if test="${20==pager.size}"> selected="selected"</c:if>>20</option>
									</c:if>

									<option value="${pager.totalElements}"
										<c:if test="${pager.size==pager.totalElements}"> selected="selected"</c:if>>All
										elements on page</option>





								</select>
							</div>


						</div>



						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="project_list_owner_selector" name="owner_id">
								<option value="">- Select owner</option>
								<option value="<sec:authentication property="principal.name"/>"
									<c:if test="${param.owner_id.equals(pageContext.request.userPrincipal.name)}"> selected="selected" </c:if>>Me</option>

								<c:forEach items="${userDTO.usersWrapper.content}" var="item">

									<c:if
										test="${item.id.userId!=pageContext.request.userPrincipal.name}">
										<option value='<c:out value="${item.id.userId}"/>'
											<c:if test="${param.owner_id.equals(item.id.userId)}"> selected="selected" </c:if>>
											<c:out
												value="${item.id.userId} - ${item.name} ${item.surname}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>



					</div>




					<!-- Pagination -->
					<c:url var="pag_link" value="${pageContext.request.requestURI}" />
					<jsp:include page="pagination.jsp"></jsp:include>

				</form>
			</div>


			<c:choose>
				<c:when test="${not empty projectDTO.projectsWrapper.content}">
					<div>
						<div>
							Total elements: <strong>${pager.totalElements}</strong>
						</div>

						<table id="data-container" class="table table-hover table-striped">

							<thead>
								<tr>
									<th><spring:message code="label.owner" /></th>
									<th><spring:message code="label.name" /></th>
									<th><spring:message code="label.description" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${projectDTO.projectsWrapper.content}"
									var="item">

									<tr>
										<td><strong><c:out
													value="${item.owner.id.userId}" /></strong> <br />
										<c:out value="${item.owner.name}" /> <c:out
												value="${item.owner.surname}" /></td>
										<td><c:out value="${item.id.projectId}" /></td>
										<!-- <td><c:forEach items="${item.versions}" var="v">
										<a href="btn btn-link" href="fdsf"><c:out value="${v.id.versionId}"></c:out></a>
										</c:forEach></td>-->
										<td><c:out value="${item.description}" /></td>
										<td><a class="btn btn-info text-center"
											href='<c:url value="/projects/view?owner_id=${item.id.ownerId}&project_id=${item.id.projectId}"/>'><spring:message
													code="view" /> <span
												class="glyphicon glyphicon-chevron-right"></span></a> <a
											href='<c:url value="/issues/new?owner_id=${item.id.ownerId}&project_id=${item.id.projectId}" />'
											class="btn btn-success"> <span
												class="glyphicon glyphicon-plus"></span> Report issue
										</a>  <a
											href='<c:url value="/issues/list?owner_id=${item.id.ownerId}&project_id=${item.id.projectId}" />'
											class="btn btn-info"> <span
												class="glyphicon glyphicon-flag"></span> Issues
										</a></td>
									</tr>

								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info text-center">
						<spring:message code="empty.projects" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />