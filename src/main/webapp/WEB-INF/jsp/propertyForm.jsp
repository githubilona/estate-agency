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
				<form:input path="name" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="name" cssClass="error text-danger" element="div"/>
			</div>
			<div class="form-group">
				<label for="description" class="bmd-label-floating">Opis:</label>
				<form:input path="description" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="description" cssClass="error text-danger" element="div"/>
			</div>
			<div class="form-group">
				<label for="price" class="bmd-label-floating">Cena:</label>
				<form:input path="price" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="price" cssClass="error text-danger" element="div"/>
			</div>
			<div class="form-group">
				<label for="availableDate" class="bmd-label-floating">Dostępne od:</label>
				<form:input path="availableDate" cssClass="form-control" type="date" cssErrorClass="form-control is-invalid"/>
				<form:errors path="availableDate" cssClass="error text-danger" element="div"/>
			</div>
			<div class="form-group">
				<label class="bmd-label-floating">
					<form:checkbox path="exclusive" /> Ogłoszenie premium
				</label>
			</div>

			<div class="form-group">
				<label for="propertyType.id" class="bmd-label-floating">Typ nieruchomości:</label>
				<form:select path="propertyType.id" cssClass="form-control" cssErrorClass="form-control is-invalid">
					<form:option value="-1">--wybierz typ nieruchomości--</form:option>
					<form:options items="${propertyTypes}" itemLabel="name" itemValue="id"/>
				</form:select>
				<form:errors path="propertyType.id" cssClass="error text-danger" element="div"/>
			</div>



			<button type="submit" class="btn btn-success" class="btn btn-success">Wyślij</button>
		</form:form>

</body>
</html>

</div>

<jsp:include page="shared/footer.jsp"/>
