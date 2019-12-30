<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:import url="shared/header.jsp">
	<c:param name="pageName" value="propertyList"></c:param>
</c:import>
<html>
<head>
	<title>Lista pojazdów</title>
</head>
<body class="card">
<div id="main">
	<H1>LISTA POJAZDÓW</H1>
	<H3>Implementacja widoku z tagów JSTL(JSP Standard Tags Library)</H3>

	<form:form id="searchForm" modelAttribute="searchCommand">
		<div class="row">
			<div class="form-group col-md-6">
				<label for="phrase">Szukana fraza:</label>
				<form:input path="phrase" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="phrase" cssClass="error text-danger" element="div"/>
			</div>

			<div class="form-group col-md-3">
				<label for="phrase">Cena od:</label>
				<form:input path="minPrice" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="minPrice" cssClass="error text-danger" element="div"/>
			</div>
			<div class="form-group col-md-3">
				<label for="phrase">Cena do:</label>
				<form:input path="maxPrice" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="maxPrice" cssClass="error text-danger" element="div"/>
			</div>
		</div>
		<div class="row">

			<div class="form-group col-md-8"></div>

			<div class="form-group col-md-2">
				<a href="/propertyList.html?all" class="btn btn-success">
					<span class="glyphicon glyphicon-refresh"></span> Pokaż wszystko
				</a>
			</div>

			<div class="form-group col-md-2">
				<button type="submit" class="btn btn-info">
					<span class="glyphicon glyphicon-search"></span> Wyszukaj
				</button>
			</div>
		</div>
	</form:form>

	<c:if test="${empty propertyListPage.content}">
		${searchCommand.isEmpty() ? 'Lista pojazdów jest pusta':'Brak wyników wyszukiwania'}
	</c:if>




	<c:if test="${not empty propertyListPage.content}">

		<c:choose>
			<c:when test="${searchCommand.isEmpty()}">
				Lista zawiera ${propertyListPage.totalElements} pojazdów
			</c:when>
			<c:otherwise>
				Wynik wyszukiwania: ${propertyListPage.totalElements} pojazdów
			</c:otherwise>
		</c:choose>

		<c:set var="boundMin" value="${20000}"/>
		<c:set var="boundMax" value="${40000}"/>

		<table class="table table-striped">
			<thead>
			<tr>
				<th>Marka</th>
				<th>Model</th>
				<th>Data produkcji</th>
				<th>Cena</th>
				<th>Opinia</th>
				<th>Czy ekskluzywny?</th>
				<security:authorize access="hasRole('ADMIN')">
					<th>Opcje</th>
				</security:authorize>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${propertyListPage.content}" var="property">
				<tr>
					<td>
						<a href="?id=${property.id}">${property.name}</a>
					</td>
					<td>${empty property.description?'Brak danych': property.description}</td>
					<td><fmt:formatDate  value="${property.availableDate}"  type="date" timeStyle="medium"/></td>
					<td><fmt:formatNumber type="CURRENCY" value="${property.price}"  currencySymbol="PLN"/></td>
					<td>
						<c:choose>
							<c:when test="${property.price lt boundMin}">
								TANI
							</c:when>
							<c:when test="${property.price gt boundMax}">
								DROGI
							</c:when>
							<c:otherwise>
								ŚREDNI
							</c:otherwise>
						</c:choose>
					</td>
					<td>${property.exclusive?'Tak': 'Nie'}</td>
					<security:authorize access="hasRole('ADMIN')">
						<td>
							<a class="btn btn-danger" href="?did=${property.id}">Usuń</a>
							<a class="btn btn-success" href="propertyForm.html?id=${property.id}">Edytuj</a>
						</td>
					</security:authorize>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${propertyListPage}" scope="request"/>
		<c:set var="mainUrl" value="propertyList.html" scope="request"/>
		<jsp:include page="shared/pagination.jsp"/>

	</c:if>
	<security:authorize access="hasRole('ADMIN')">
		<a class="btn btn-success" href="propertyForm.html">Dodaj Nowy</a>
	</security:authorize>
</div>
</body>
</html>
<jsp:include page="shared/footer.jsp"/>
