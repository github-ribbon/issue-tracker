<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<footer
	class="container text-center margin-top-small margin-bottom-small">
	&copy; 2014 Copyright by Andrzej Stążecki </footer>


<!-- esapi4js dependencies -->
<script type="text/javascript"
	src="<c:url value="/resources/js/esapi4js/lib/log4js.js" />"></script>
<!-- esapi4js core -->
<script type="text/javascript"
	src="<c:url value="/resources/js/esapi4js/esapi.js" />"></script>
<!-- esapi4js i18n resources -->
<script type="text/javascript"
	src="<c:url value="/resources/js/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js" />"></script>
<!-- esapi4js configuration -->
<script type="text/javascript"
	src="<c:url value="/resources/js/esapi4js/resources/Base.esapi.properties.js" />"></script>

<script src="<c:url value="/resources/js/circular-json.js" />"></script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/js/scripts.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-select.js" />"></script>
</body>
</html>