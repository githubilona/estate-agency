<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<H1>Dane nieruchomości</H1>
Id: <b>${property.id}</b><br/>
Tytuł ogłoszenia: <b>${empty property.name?'Brak danych': property.name}</b><br/>
Typ: <b>${empty property.propertyType?'Brak danych': property.propertyType}</b><br/>
Cena: <b><fmt:formatNumber type="CURRENCY" value="${property.price}"  currencySymbol="PLN"/></b><br/>
Dostępne od: <b><fmt:formatDate  value="${property.availableDate}"  type="date" timeStyle="medium"/></b><br/>
Ekskluzywna <b>${empty property.exclusive?'TAK': 'NIE'}</b><br/>

<a href="propertyList.html">Wstecz</a>
</body>
</html>