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

<c:set var="pager" scope="request" value="${issueDTO.pager}" />

<div id="main" class="container-fluid">

	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Issues</h3>
		</div>
		<div id="issue-list" class="panel-body">



			<c:url var="url" value="/issues/list" />

			<div class="container">
				<form id="issue_list_form" action="${url}"
					class="form-horizontal margin-bottom margin-top" method="get">


					<input type="hidden" id="current_page" name="page"
						value="<c:out value="${pager.page}" />" />

					<div class="row">




						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="issue_list_order_selector" name="sort">
								<option value="">- Order by</option>
								<option value="projectId,ASC"
									<c:if test="${pager.sort.equals('projectId,ASC')}"> selected="selected" </c:if>>
									Project name ascending</option>
								<option value="projectId,DESC"
									<c:if test="${pager.sort.equals('projectId,DESC')}"> selected="selected" </c:if>>
									Project name descending</option>
								<option value="title,ASC"
									<c:if test="${pager.sort.equals('title,ASC')}"> selected="selected" </c:if>>
									Title ascending</option>
								<option value="title,DESC"
									<c:if test="${pager.sort.equals('title,DESC')}"> selected="selected" </c:if>>
									Title descending</option>
							</select>
						</div>

						<div id="page_size_container">

							<div class='col-md-4 text-center padding-small'>
								<select name="size" class='selectpicker show-tick'
									id='issue_list_page_size_selector'>

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
								id="issue_list_reporter_selector" name="reporter">
								<option value="">- Reported by</option>
								<option value="<sec:authentication property="principal.name"/>"
									<c:if test="${param.reporter.equals(pageContext.request.userPrincipal.name)}"> selected="selected" </c:if>>Me</option>

								<c:forEach items="${userDTO.usersWrapper.content}" var="item">

									<c:if
										test="${item.id.userId!=pageContext.request.userPrincipal.name}">
										<option value='<c:out value="${item.id.userId}"/>'
											<c:if test="${param.reporter.equals(item.id.userId)}"> selected="selected" </c:if>>
											<c:out
												value="${item.id.userId} - ${item.name} ${item.surname}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>



						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="issue_list_assignee_selector" name="assignee">
								<option value="">- Assigned to</option>
								<option value="<sec:authentication property="principal.name"/>"
									<c:if test="${param.assignee.equals(pageContext.request.userPrincipal.name)}"> selected="selected" </c:if>>Me</option>

								<c:forEach items="${userDTO.usersWrapper.content}" var="item">

									<c:if
										test="${item.id.userId!=pageContext.request.userPrincipal.name}">
										<option value='<c:out value="${item.id.userId}"/>'
											<c:if test="${param.assignee.equals(item.id.userId)}"> selected="selected" </c:if>>
											<c:out
												value="${item.id.userId} - ${item.name} ${item.surname}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						
						

						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="issue_list_status_selector" name="status_id">
								<option value="">- Select Status</option>
								<option value="o"
									<c:if test="${param.status_id.equals('o')}">selected="selected"</c:if>>Open</option>
								<option value="r"
									<c:if test="${param.status_id.equals('r')}">selected="selected"</c:if>>Resolved</option>
							</select>
						</div>

						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="issue_list_priority_selector" name="priority_id">
								<option value="">- Select Priority</option>
								<option value="i"
									<c:if test="${param.priority_id.equals('i')}">selected="selected"</c:if>>Minor</option>
								<option value="a"
									<c:if test="${param.priority_id.equals('a')}">selected="selected"</c:if>>Major</option>
							</select>
						</div>

						<div class="col-md-4 text-center padding-small">
							<select class="selectpicker show-tick"
								id="issue_list_type_selector" name="issue_type_id">
								<option value="">- Select Type</option>
								<option value="b"
									<c:if test="${param.issue_type_id.equals('b')}">selected="selected"</c:if>>Bug</option>
								<option value="d"
									<c:if test="${param.issue_type_id.equals('d')}">selected="selected"</c:if>>Deprecated</option>
								<option value="i"
									<c:if test="${param.issue_type_id.equals('i')}">selected="selected"</c:if>>Improvement</option>
								<option value="n"
									<c:if test="${param.issue_type_id.equals('n')}">selected="selected"</c:if>>New
									feature</option>
								<option value="r"
									<c:if test="${param.issue_type_id.equals('r')}">selected="selected"</c:if>>Remove
									feature</option>
								<option value="t"
									<c:if test="${param.issue_type_id.equals('t')}">selected="selected"</c:if>>Task</option>
							</select>
						</div>




					</div>




					<!-- Pagination -->
					<c:url var="pag_link" value="${pageContext.request.requestURI}" />
					<jsp:include page="pagination.jsp"></jsp:include>

				</form>
			</div>


			<c:choose>
				<c:when test="${not empty issueDTO.issuesWrapper.content}">
					<div>
						<div>
							Total elements: <strong>${pager.totalElements}</strong>
						</div>

						<table id="data-container" class="table table-hover table-striped">

							<thead>
								<tr>
									<th>#</th>
									<th>Project</th>
									<th>Title</th>
									<th>Reporter</th>
									<th>Assignee</th>
									<th>Status</th>
									<th>Priority</th>
									<th>Type</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${issueDTO.issuesWrapper.content}" var="item">

									<tr>
										<td>#<c:out value="${item.issueId}" /></td>
										<td><c:out value="${item.ownerId}/${item.projectId}" /></td>
										<td><c:out value="${item.title}" /></td>
										<td><strong><c:out
													value="${item.reporterUser.id.userId}" /></strong><br /> <c:out
												value="${item.reporterUser.name}" /> <c:out
												value="${item.reporterUser.surname}" /></td>
										<td><strong><c:out
													value="${item.assigneeUser.id.userId}" /></strong> <br /> <c:out
												value="${item.assigneeUser.name}" /> <c:out
												value="${item.assigneeUser.surname}" /></td>
										<td><c:choose>
												<c:when test="${item.statusId=='o'}">Open</c:when>
												<c:when test="${item.statusId=='r'}">Resolved</c:when>
											</c:choose></td>
										<td><c:choose>
												<c:when test="${item.priorityId=='a'}">Major</c:when>
												<c:when test="${item.priorityId=='i'}">Minor</c:when>
											</c:choose></td>
										<td><c:choose>
												<c:when test="${item.issueTypeId=='b'}">Bug</c:when>
												<c:when test="${item.issueTypeId=='d'}">Deprecated</c:when>
												<c:when test="${item.issueTypeId=='i'}">Improvement</c:when>
												<c:when test="${item.issueTypeId=='n'}">New feature</c:when>
												<c:when test="${item.issueTypeId=='r'}">Remove feature</c:when>
												<c:when test="${item.issueTypeId=='t'}">Task</c:when>
											</c:choose></td>

										<td><a class="btn btn-info text-center"
											href='<c:url value="/issues/view?issue_id=${item.issueId}"/>'><spring:message
													code="view" /> <span
												class="glyphicon glyphicon-chevron-right"></span></a></td>
									</tr>

								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info text-center">No issues found</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />