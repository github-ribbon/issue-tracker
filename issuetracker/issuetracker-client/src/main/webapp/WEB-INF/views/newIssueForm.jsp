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
			<h3 class="panel-title">New issue</h3>
		</div>
		<div class="panel-body">

			<form class="form-horizontal" role="form">

				<input type="hidden" field-id="issue.statusId" value="o" />

				<div class="form-group">
					<label for="" class="col-sm-3 control-label">Project</label>
					<div class="col-sm-7">

						<div class="value-box">

							<c:out value="${projectDTO.project.id.ownerId}" />
							/ <c:out
									value="${projectDTO.project.id.projectId}" />
						</div>
						<input type="hidden" field-id="issue.ownerId"
							value="<c:out value="${projectDTO.project.id.ownerId}" />" /> <input
							type="hidden" field-id="issue.projectId"
							value="<c:out value="${projectDTO.project.id.projectId}" />" />
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-sm-3 control-label">Title</label>
					<div class="col-sm-7">
						<input type="text" field-id="issue.title" class="form-control" />
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-sm-3 control-label">Content</label>
					<div class="col-sm-7">
						<textarea field-id="issue.content"  rows="8" class="form-control"></textarea>
					</div>
				</div>


				<div class="form-group">
					<label for="" class="col-sm-3 control-label">Priority</label>
					<div class="col-md-4 text-center padding-small">
						<select class="selectpicker show-tick" field-id="issue.priorityId"
							name="">
							<option value="">- Select Priority</option>
							<option value="i">Minor</option>
							<option value="a">Major</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-sm-3 control-label">Type</label>
					<div class="col-md-4 text-center padding-small">
						<select class="selectpicker show-tick"
							field-id="issue.issueTypeId" name="">
							<option value="">- Select Type</option>
							<option value="b">Bug</option>
							<option value="d">Deprecated</option>
							<option value="i">Improvement</option>
							<option value="n">New feature</option>
							<option value="r">Remove feature</option>
							<option value="t">Task</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-sm-3 control-label">Assignee</label>
					<div class="col-md-4 text-center padding-small">
						<select class="selectpicker show-tick" field-id="issue.assignee"
							name="">
							<option value="">- Select assignee</option>

							<c:forEach items="${userDTO.usersWrapper.content}" var="item">
								<option value='<c:out value="${item.id.userId}"/>'><c:out
										value="${item.id.userId}" /> -
									<c:out value="${item.name}" />
									<c:out value="${item.surname}" />
								</option>
							</c:forEach>
						</select>
					</div>
				</div>


				<div class="form-group text-center">
					<button type="button" id="add_issue_button" class="btn btn-success">
						<span class="glyphicon glyphicon-pencil"></span> Report
					</button>
				</div>
			</form>

		</div>

	</div>

</div>


<jsp:include page="footer.jsp" />


