<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<div class="col-sm-12 text-center">

	<ul class="pagination">
		<c:choose>

			<c:when test="${pager.totalPages>1}">

				<c:set var="pag_link" value="${pag_link}?" />

				<c:forEach items="${param}" var="currentParam" varStatus="counter">
					<c:if test='${!currentParam.key.equals("page")}'>
						<c:if test="${counter.count!=1}">
							<c:set var="pag_link" value="${pag_link}&" />
						</c:if>
						<c:set var="pag_link"
							value="${pag_link}${currentParam.key}=${currentParam.value}" />
					</c:if>
				</c:forEach>



				<c:if
					test="${(fn:length(param)>0)&&(!((fn:length(param)==1)&&(param.page!=null)))}">
					<c:set var="pag_link" value="${pag_link}&" />
				</c:if>


				<c:choose>
					<c:when test="${pager.page==0}">
						<li class="disabled"><a>←</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href='<c:out value="${pag_link}page=${pager.page-1}"/>'>←</a></li>
					</c:otherwise>

				</c:choose>

				<c:choose>
					<c:when test="${(pager.page)<=5}">

						<c:if test="${pager.page>0}">
							<c:forEach begin="0" end="${pager.page-1}" var="i" step="1">
								<li><a href='<c:out value="${pag_link}page=${i}"/>'>${i+1}</a></li>
							</c:forEach>
						</c:if>

						<li class="disabled"><a><c:out value="${pager.page+1}" /></a></li>

					</c:when>

					<c:otherwise>

						<li><a href='<c:out value="${pag_link}page=0"/>'>1</a></li>
						<li class="disabled"><a>...</a></li>

						<c:forEach begin="${pager.page-4}" end="${pager.page-1}" var="i"
							step="1">
							<li><a href='<c:out value="${pag_link}page=${i}"/>'>${i+1}</a></li>
						</c:forEach>

						<li class="disabled"><a><c:out value="${pager.page+1}" /></a></li>



					</c:otherwise>

				</c:choose>




				<c:choose>
					<c:when test="${(pager.totalPages-(pager.page+1))<=5}">


						<c:forEach begin="${pager.page+1}" end="${pager.totalPages-1}"
							var="i" step="1">
							<li><a href='<c:out value="${pag_link}page=${i}" />'>${i+1}</a></li>
						</c:forEach>


					</c:when>

					<c:otherwise>


						<c:forEach begin="${pager.page+1}" end="${pager.page+4}" var="i"
							step="1">
							<li><a href='<c:out value="${pag_link}page=${i}"/>'>${i+1}</a></li>
						</c:forEach>


						<li class="disabled"><a>...</a></li>

						<li><a
							href='<c:out value="${pag_link}page=${pager.totalPages-1}" />'><c:out
									value="${pager.totalPages}" /></a></li>

					</c:otherwise>

				</c:choose>



				<c:choose>
					<c:when test="${(pager.page+1)==pager.totalPages}">
						<li class="disabled"><a>→</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href='<c:out value="${pag_link}page=${pager.page+1}"/>'>→</a></li>
					</c:otherwise>

				</c:choose>


			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>


	</ul>
</div>


