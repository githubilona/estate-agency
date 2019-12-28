<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:import url="shared/header.jsp">
	<c:param name="pageName" value="propertyList"></c:param>
</c:import>
<html>
<head>
	<title>Lista ogłoszeń</title>
</head>
<body>
<div id="main">
	<H1>LISTA OGŁOSZEŃ</H1>
	<H3>Implementacja widoku z tagów JSTL(JSP Standard Tags Library)</H3>

	<c:if test="${empty propertyList}">
		Lista ogłoszeń jest pusta
	</c:if>

	<c:if test="${not empty propertyList}">
		Lista zawiera ${fn:length(propertyList)} ogłoszeń

		<c:set var="boundMin" value="${20000}"/>
		<c:set var="boundMax" value="${40000}"/>

		<table class="table table-striped">
			<thead>
			<tr>
				<th>Tytuł ogłoszenia</th>
				<th>Model</th>
				<th>Dostępne od</th>
				<th>Cena</th>
				<th>Opinia</th>
				<th>Czy ekskluzywne?</th>
				<th>Opcje</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${propertyList}" var="property">
				<tr>
					<td>
						<a href="?id=${property.id}">${property.name}</a>
					</td>
					<td>${empty property.propertyType?'Brak danych': property.propertyType}</td>
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
					<td>
						<a>Usuń</a>
						<a>Edytuj</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>

	<a>Dodaj Nowy</a>
</div>
</body>
</html>
<jsp:include page="shared/footer.jsp"/>
