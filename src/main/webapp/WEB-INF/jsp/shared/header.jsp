<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap-material-design/4.1.2/dist/css/bootstrap-material-design.css" />
    <link rel="stylesheet" type="text/css"
          href="statics/css/main.css" />

</head>
<body>
<div id="header" class="card-header">
    <nav>
        <ul class="nav nav-tabs bg-dark">
            <li class="nav-item">
                <a class="nav-link ${param['pageName'] eq 'home' ?'active':''}"  href="/" >Strona główna</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${param['pageName'] eq 'propertiesList' ?'active':''}"  href="propertyList.html">Lista ogloszeń</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${param['pageName'] eq 'propertyForm' ?'active':''}"  href="propertyForm.html">Nowe ogłoszenie</a>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>


