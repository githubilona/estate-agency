<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>

<head>
    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap-material-design/4.1.2/dist/css/bootstrap-material-design.css" />
    <link rel="stylesheet" type="text/css"
          href="statics/css/main.css" />

</head>
<body>
<div class="card-header">

    <nav id="header" class="navbar navbar-expand bg-dark flex-column flex-md-row">
        <div class="navbar-nav-scroll">
        <ul class="nav nav-tabs bg-dark">

            <li class="nav-item">
                <a class="nav-link ${param['pageName'] eq 'home' ?'active':''}"  href="/" >Strona główna</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${param['pageName'] eq 'propertiesList' ?'active':''}"  href="propertyList.html">Lista ogloszeń</a>
            </li>
            <security:authorize access="hasRole('ADMIN')">
                <li class="nav-item">
                    <a class="nav-link ${param['pageName'] eq 'propertyForm' ?'active':''}"  href="propertyForm.html">Nowe ogłoszenie</a>
                </li>
            </security:authorize>

            <li class="nav-item">
                <a class="nav-link ${param['pageName'] eq 'registration' ?'active':''}"  href="/registrationForm.html">Rejestracja</a>
            </li>

        </ul>
        </div>

        <ul class="nav-tabs navbar-nav ml-md-auto bg-dark">
            <security:authorize access="isAuthenticated()">

                <label style="color:yellow; margin-top: 20px;">
                    Witaj <security:authentication property="principal.username"/>!
                </label>

                <li class="nav-item">
                    <a class="nav-link" href="#" onclick="document.getElementById('logout').submit()">Wyloguj się</a>
                    <form action="/logout" id="logout" method="post" style="display: none;">
                        <security:csrfInput/>
                    </form>
                </li>
            </security:authorize>


            <security:authorize access="isAnonymous()">
                <li class="nav-item">
                    <a class="nav-link ${param['pageName'] eq 'loginForm' ?'active':''}" href="/login">Zaloguj się</a>
                </li>
            </security:authorize>
        </ul>


    </nav>
</div>
</body>
</html>


