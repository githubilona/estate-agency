<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:import url="shared/header.jsp">
	<c:param name="pageName" value="propertyForm"></c:param>
</c:import>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="card">
<div id="main">

		<form:form modelAttribute="property">
			<div class="form-group">
				<label for="name" class="bmd-label-floating">Tytuł ogłoszenia:</label>
				<form:input path="name" cssClass="form-control"/>
			</div>
			<div class="form-group">
				<label for="propertyType" class="bmd-label-floating">Typ nieruchmości:</label>
				<form:input path="propertyType" cssClass="form-control"/>
			</div>
			<div class="form-group">
				<label for="price" class="bmd-label-floating">Cena:</label>
				<form:input path="price" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="availableDate" class="bmd-label-floating">Dostępne od:</label>
				<form:input path="availableDate" cssClass="form-control" type="date"/>
			</div>
			<div class="form-group">
				<label class="bmd-label-floating">
					<form:checkbox path="exclusive" /> Ogłoszenie premium
				</label>
			</div>



			<button type="submit" class="btn btn-success" class="btn btn-success">Wyślij</button>
		</form:form>

</body>
</html>

</div>

<jsp:include page="shared/footer.jsp"/>
