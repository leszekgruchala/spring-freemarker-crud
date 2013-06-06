<#import "/spring.ftl" as spring />
<#include "macros/personFormInput.ftl"/>
<#include "macros/changePersonModal.ftl"/>
<!DOCTYPE HTML>
<html>
<head>
    <title>Spring CRUD example</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Hello from Spring Freemaker CRUD example</h2>
    <form id="mainForm" class=".form-inline" action="savePerson" method="POST">
        <label>Please add a person :-)</label>
        <@personFormInput bean="person" withSubmit=true/>
    </form>
    <h3>People history</h3>
    <ul class="unstyled prettyprint">
        <#if (people?? && people?size != 0)>
            <#list people as persona>
                <li class="person-row">
                    <label class="force-middle">${persona.name} / ${persona.formattedBirthDate} / ${persona.email}</label>
                    <div class="force-middle person-data">
                        <input type="hidden" class="personHash" value="${persona.hash}">
                        <input type="hidden" class="personName" value="${persona.name}">
                        <input type="hidden" class="personBirthDate" value="${persona.formattedBirthDate}">
                        <input type="hidden" class="personEmail" value="${persona.email}">
                        <i class="icon-edit" title="Edit person"></i>
                        <i class="icon-remove" title="Remove person"></i>
                    </div>
                </li>
            </#list>
        <#else>
            No people around.
        </#if>
    </ul>
<@changePersonModal/>
</div>
<script src="resources/js/jquery-2.0.2.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/code.js"></script>
</body>
</html>