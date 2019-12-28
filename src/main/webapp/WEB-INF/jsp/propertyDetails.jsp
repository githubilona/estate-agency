<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="shared/header.jsp">
    <jsp:param name="pageName" value="propertyDetails"/>
</jsp:include>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Szczegóły ogłoszenia</title>
</head>
<body>
<div id="main">
    <H1>Dane nieruchmości</H1>
    Id: <b>${property.id}</b><br/>
    Marka: <b>${empty property.name?'Brak danych': property.name}</b><br/>
    Model: <b>${empty property.propertyType?'Brak danych': property.propertyType}</b><br/>
    Cena: <b><fmt:formatNumber type="CURRENCY" value="${property.price}"  currencySymbol="PLN"/></b><br/>
    Dostępne od: <b><fmt:formatDate  value="${property.availableDate}"  type="date" timeStyle="medium"/></b><br/>
    Czy ekskluzywne? <b>${property.exclusive?'Tak': 'Nie'}</b><br/>
    <a href="/">Wstecz</a>
</div>
</body>
</html>

<jsp:include page="shared/footer.jsp"/>