<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />




<div id="main" class="container-fluid">


	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Issue details</h3>
		</div>
		<div class="panel-body">

			<c:if test="${param.created!=null}">
				<div class="alert alert-success text-center">Issue reported
					successfully</div>
			</c:if>

			<c:if test="${param.updated!=null}">
				<div class="alert alert-success text-center">Issue updated
					successfully</div>
			</c:if>

			<c:choose>
				<c:when test="${not empty issueDTO.issue}">
					<form class="form-horizontal" role="form">

						<input type="hidden" field-id="issue.issueId"
							value="<c:out value="${issueDTO.issue.issueId}" />" />


						<div class="form-group">
							
							<div class="col-sm-12 text-center">
								
									Issue <strong >#<c:out value="${issueDTO.issue.issueId}" /></strong>
								
							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Reporter</label>
							<div class="col-sm-7">
								<div class="value-box">
									<c:out value="${issueDTO.issue.reporterUser.id.userId}" />
									/
									<c:out
										value="${issueDTO.issue.reporterUser.name} ${issueDTO.issue.reporterUser.surname}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Project</label>
							<div class="col-sm-7">

								<div class="value-box">
									<c:out value="${issueDTO.issue.ownerId}" />
									/
									<c:out value="${issueDTO.issue.projectId}" />
								</div>
								<input type="hidden" field-id="issue.ownerId"
									value="<c:out value="${issueDTO.issue.ownerId}" />" /> <input
									type="hidden" field-id="issue.projectId"
									value="<c:out value="${issueDTO.issue.projectId}" />" />
							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Title</label>
							<div class="col-sm-7">

								<c:choose>
									<c:when
										test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
										<input type="text" field-id="issue.title" class="form-control"
											value='<c:out value="${issueDTO.issue.title}"/>' />
									</c:when>

									<c:otherwise>
										<div class="value-box">
											<c:out value="${issueDTO.issue.title}" />
										</div>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Content</label>
							<div class="col-sm-7">


								<c:choose>
									<c:when
										test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
										<textarea field-id="issue.content" rows="8" class="form-control"><c:out
												value="${issueDTO.issue.content}" /></textarea>
									</c:when>
									<c:otherwise>
										<div class="value-box">
											<c:out value="${issueDTO.issue.content}" />
										</div>
									</c:otherwise>
								</c:choose>

							</div>
						</div>


						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Status</label>
							<div class="col-md-4 padding-small">

								<c:choose>
									<c:when
										test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
										<select class="selectpicker show-tick"
											field-id="issue.statusId" name="">
											<option value="">- Select Status</option>
											<option value="o"
												<c:if test="${issueDTO.issue.statusId.equals('o')}">selected="selected"</c:if>>Open</option>
											<option value="r"
												<c:if test="${issueDTO.issue.statusId.equals('r')}">selected="selected"</c:if>>Resolved</option>
										</select>
									</c:when>

									<c:otherwise>
										<div class="value-box">
											<c:choose>
												<c:when test="${issueDTO.issue.statusId.equals('o')}">Open</c:when>
												<c:when test="${issueDTO.issue.statusId.equals('r')}">Resolved</c:when>
											</c:choose>

										</div>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Priority</label>
							<div class="col-md-4 padding-small">
								<c:choose>
									<c:when
										test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
										<select class="selectpicker show-tick"
											field-id="issue.priorityId" name="">
											<option value="">- Select Priority</option>
											<option value="i"
												<c:if test="${issueDTO.issue.priorityId.equals('i')}">selected="selected"</c:if>>Minor</option>
											<option value="a"
												<c:if test="${issueDTO.issue.priorityId.equals('a')}">selected="selected"</c:if>>Major</option>
										</select>
									</c:when>
									<c:otherwise>
										<div class="value-box">
											<c:choose>
												<c:when test="${issueDTO.issue.priorityId.equals('i')}">Minor</c:when>
												<c:when test="${issueDTO.issue.priorityId.equals('a')}">Major</c:when>
											</c:choose>
										</div>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Type</label>
							<div class="col-md-4 padding-small">
								<c:choose>
									<c:when
										test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
										<select class="selectpicker show-tick"
											field-id="issue.issueTypeId" name="">
											<option value="">- Select Type</option>
											<option value="b"
												<c:if test="${issueDTO.issue.issueTypeId.equals('b')}">selected="selected"</c:if>>Bug</option>
											<option value="d"
												<c:if test="${issueDTO.issue.issueTypeId.equals('d')}">selected="selected"</c:if>>Deprecated</option>
											<option value="i"
												<c:if test="${issueDTO.issue.issueTypeId.equals('i')}">selected="selected"</c:if>>Improvement</option>
											<option value="n"
												<c:if test="${issueDTO.issue.issueTypeId.equals('n')}">selected="selected"</c:if>>New
												feature</option>
											<option value="r"
												<c:if test="${issueDTO.issue.issueTypeId.equals('r')}">selected="selected"</c:if>>Remove
												feature</option>
											<option value="t"
												<c:if test="${issueDTO.issue.issueTypeId.equals('t')}">selected="selected"</c:if>>Task</option>
										</select>
									</c:when>
									<c:otherwise>
										<div class="value-box">
											<c:choose>
												<c:when test="${issueDTO.issue.issueTypeId.equals('b')}">Bug</c:when>
												<c:when test="${issueDTO.issue.issueTypeId.equals('d')}">Deprecated</c:when>
												<c:when test="${issueDTO.issue.issueTypeId.equals('i')}">Improvement</c:when>
												<c:when test="${issueDTO.issue.issueTypeId.equals('n')}">New feature</c:when>
												<c:when test="${issueDTO.issue.issueTypeId.equals('r')}">Remove feature</c:when>
												<c:when test="${issueDTO.issue.issueTypeId.equals('t')}">Task</c:when>
											</c:choose>
										</div>
									</c:otherwise>
								</c:choose>


							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Assignee</label>
							<div class="col-md-4 padding-small">
								<c:choose>
									<c:when
										test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
										<select class="selectpicker show-tick"
											field-id="issue.assignee" name="">
											<option value="">- Select assignee</option>

											<c:forEach items="${userDTO.usersWrapper.content}" var="item">
												<option value='<c:out value="${item.id.userId}"/>'
													<c:if test="${issueDTO.issue.assignee.equals(item.id.userId)}">selected="selected"</c:if>><c:out
														value="${item.id.userId}" /> -
													<c:out value="${item.name}" />
													<c:out value="${item.surname}" />
												</option>
											</c:forEach>
										</select>
									</c:when>

									<c:otherwise>
										<div class="value-box">
											<c:out
												value="${issueDTO.issue.assigneeUser.id.userId} - ${issueDTO.issue.assigneeUser.name} ${issueDTO.issue.assigneeUser.surname}"></c:out>
										</div>
									</c:otherwise>
								</c:choose>


							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Reported</label>
							<div class="col-sm-7">

								<div class="value-box">
									<fmt:formatDate type="both" pattern="dd-MM-yyyy H:mm:ss"
										value="${issueDTO.issue.created}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-3 control-label">Modified</label>
							<div class="col-sm-7">

								<div class="value-box">
									<fmt:formatDate type="both" pattern="dd-MM-yyyy H:mm:ss"
										value="${issueDTO.issue.modified}" />
								</div>
							</div>
						</div>


						<c:if
							test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">
							<div class="form-group text-center">
								<button type="button" id="save_issue_button"
									class="btn btn-primary">
									<span class="glyphicon glyphicon-pencil"></span>
									<spring:message code="save" />
								</button>

								<button type="button" id="delete_issue_button"
									class="btn btn-danger">
									<span class="glyphicon glyphicon-trash"></span> Delete
								</button>
							</div>

						</c:if>
					</form>



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




					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-3 control-label">Fix version(s)</label>
							<div class="col-sm-7">


								<c:choose>
									<c:when test="${not empty issueDTO.issue.fixedVersions}">


										<c:forEach items="${issueDTO.issue.fixedVersions}" var="item">
											<c:if
												test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

												<form action="#"
													class="delete_fixed_version_form form-inline display-inline-block"
													method="get" role="form">
											</c:if>

											<span> <span class="badge"> <c:out
														value="${item.id.versionId}" /></span> <c:if
													test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

													<input type="hidden"
														list-field-id="fixedVersion.id.projectId"
														value="<c:out value="${item.id.projectId}" />" />
													<input type="hidden"
														list-field-id="fixedVersion.id.versionId"
														value="<c:out value="${item.id.versionId}" />" />
													<input type="hidden"
														list-field-id="fixedVersion.id.ownerId"
														value="<c:out value="${item.id.ownerId}" />" />
													<input type="hidden"
														list-field-id="fixedVersion.id.issueId"
														value="<c:out value="${item.id.issueId}" />" />

													<button type="submit"
														class="btn btn-danger btn-sm text-center delete_fixed_version_button">
														<span class="glyphicon glyphicon-remove"></span>
													</button>
												</c:if>
											</span>

											<c:if
												test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

												</form>
											</c:if>

										</c:forEach>

									</c:when>
									<c:otherwise>
										<div class="value-box">None</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>






					<c:if
						test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

						<form class="form-horizontal" role="form">

							<input type="hidden" field-id="fixedVersion.id.issueId"
								value='<c:out value="${issueDTO.issue.issueId}"/>' /> <input
								type="hidden" field-id="fixedVersion.id.projectId"
								value='<c:out value="${issueDTO.issue.projectId}"/>' /> <input
								type="hidden" field-id="fixedVersion.id.ownerId"
								value='<c:out value="${issueDTO.issue.ownerId}"/>' />


							<div class="form-group">
								<label for="" class="col-sm-3 control-label"></label>
								<div class="col-md-4 padding-small">
									<select id="fixed_version_selector"
										class="selectpicker show-tick"
										field-id="fixedVersion.id.versionId" name="">
										<option value="">- Select version</option>

										<c:forEach items="${issueDTO.notFixedVersionsWrapper.content}"
											var="item">
											<option value='<c:out value="${item.id.versionId}"/>'>
												<c:out value="${item.id.versionId}" />
											</option>
										</c:forEach>
									</select>
								</div>
							</div>

						</form>


					</c:if>





					<div class="text-center">

						<c:if test="${param.affected_version_created!=null}">
							<div class="alert alert-success text-center">Affected
								version added successfully</div>
						</c:if>

						<c:if test="${param.affected_version_deleted!=null}">
							<div class="alert alert-success text-center">Affected
								version removed successfully</div>
						</c:if>



					</div>




					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-3 control-label">Affect version(s)</label>
							<div class="col-sm-7">


								<c:choose>
									<c:when test="${not empty issueDTO.issue.affectedVersions}">


										<c:forEach items="${issueDTO.issue.affectedVersions}"
											var="item">

											<c:if
												test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

												<form action="#"
													class="delete_affected_version_form form-inline display-inline-block"
													method="get" role="form">
											</c:if>

											<span> <span class="badge"> <c:out
														value="${item.id.versionId}" /></span> <c:if
													test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

													<input type="hidden"
														list-field-id="affectedVersion.id.projectId"
														value="<c:out value="${item.id.projectId}" />" />
													<input type="hidden"
														list-field-id="affectedVersion.id.versionId"
														value="<c:out value="${item.id.versionId}" />" />
													<input type="hidden"
														list-field-id="affectedVersion.id.ownerId"
														value="<c:out value="${item.id.ownerId}" />" />
													<input type="hidden"
														list-field-id="affectedVersion.id.issueId"
														value="<c:out value="${item.id.issueId}" />" />

													<button type="submit"
														class="btn btn-danger btn-sm text-center delete_affected_version_button">
														<span class="glyphicon glyphicon-remove"></span>
													</button>
												</c:if>
											</span>

											<c:if
												test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

												</form>
											</c:if>

										</c:forEach>

									</c:when>
									<c:otherwise>
										<div class="value-box">None</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>






					<c:if
						test="${issueDTO.issue.reporter==pageContext.request.userPrincipal.name}">

						<form class="form-horizontal" role="form">

							<input type="hidden" field-id="affectedVersion.id.issueId"
								value='<c:out value="${issueDTO.issue.issueId}"/>' /> <input
								type="hidden" field-id="affectedVersion.id.projectId"
								value='<c:out value="${issueDTO.issue.projectId}"/>' /> <input
								type="hidden" field-id="affectedVersion.id.ownerId"
								value='<c:out value="${issueDTO.issue.ownerId}"/>' />


							<div class="form-group">
								<label for="" class="col-sm-3 control-label"></label>
								<div class="col-md-4 padding-small">
									<select id="affected_version_selector"
										class="selectpicker show-tick"
										field-id="affectedVersion.id.versionId" name="">
										<option value="">- Select version</option>

										<c:forEach
											items="${issueDTO.notAffectedVersionsWrapper.content}"
											var="item">
											<option value='<c:out value="${item.id.versionId}"/>'>
												<c:out value="${item.id.versionId}" />
											</option>
										</c:forEach>
									</select>
								</div>
							</div>

						</form>
					</c:if>



					<!-- responses -->


					<div>


						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Responses</h3>
							</div>

							<div class="panel-body">


								<div class="text-center">

									<c:if test="${param.response_created!=null}">
										<div class="alert alert-success text-center">Response
											added successfully</div>
									</c:if>

									<c:if test="${param.response_deleted!=null}">
										<div class="alert alert-success text-center">Response
											removed successfully</div>
									</c:if>

									<c:if test="${param.response_updated!=null}">
										<div class="alert alert-success text-center">Response
											saved successfully</div>
									</c:if>



								</div>

								<form class="form-horizontal">


									<input type="hidden" field-id="response.issueId"
										value='<c:out value="${issueDTO.issue.issueId}"/>' />

									<div class="form-group">
										<label for="" class="col-sm-3 control-label">Content</label>
										<div class="col-sm-7">
											<textarea field-id="response.content" class="form-control"></textarea>
										</div>
									</div>

									<div class="form-group text-center">
										<button type="button" id="add_response_button"
											class="btn btn-success">
											<span class="glyphicon glyphicon-plus"></span> Respond
										</button>


									</div>

								</form>


								<c:choose>
									<c:when test="${not empty issueDTO.issue.responses}">

										<c:forEach items="${issueDTO.issue.responses}" var="item">

											<div
												class="container col-sm-offset-2 col-sm-8 margin-top-small">
												<hr />

												<form
													class="form-horizontal delete_response_form save_response_form"
													role="form">

													<div>
														<span class="badge"><c:out
																value="${item.user.name} ${item.user.surname} (${item.user.id.userId})" /></span>
														added <span class="badge"><fmt:formatDate
																type="both" pattern="dd-MM-yyyy H:mm:ss"
																value="${item.created}" /></span> modified <span class="badge"><fmt:formatDate
																type="both" pattern="dd-MM-yyyy H:mm:ss"
																value="${item.modified}" /></span>
													</div>
													<div class="margin-top-small">
														<input type="hidden" list-field-id="response.responseId"
															value='<c:out value="${item.responseId}"/>' />

														<c:choose>
															<c:when
																test="${item.userId==pageContext.request.userPrincipal.name}">
																<textarea class="form-control"
																	list-field-id="response.content"><c:out
																		value="${item.content}" /></textarea>
															</c:when>
															<c:otherwise>
																<div class="value-box">
																	<c:out value="${item.content}" />
																</div>
															</c:otherwise>
														</c:choose>


													</div>




													<c:choose>
														<c:when
															test="${item.userId==pageContext.request.userPrincipal.name}">
															<div class="form-group text-center margin-top-small">
																<button type="button"
																	class="btn btn-primary save_response_button">
																	<span class="glyphicon glyphicon-pencil"></span>
																	<spring:message code="save" />
																</button>

																<button type="button"
																	class="btn btn-danger delete_response_button">
																	<span class="glyphicon glyphicon-trash"></span> Delete
																</button>
															</div>
														</c:when>
													</c:choose>

												</form>




											</div>



										</c:forEach>


									</c:when>
									<c:otherwise>
										<div class="alert alert-info text-center">No responses</div>
									</c:otherwise>
								</c:choose>
							</div>

						</div>

					</div>


				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">Issue not found</div>
				</c:otherwise>

			</c:choose>
		</div>

	</div>

</div>


<jsp:include page="footer.jsp" />