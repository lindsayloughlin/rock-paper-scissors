<html>


<#include "commonheader.ftl">

    <body>
    <#list players as player>
        <div>Username: ${player.username}</div>
        <div>Password : ${player.password}</div>
    </#list>
    </body>

<!-- not very secure -->
</html>