<html>


<#include "commonheader.ftl">

<h1>Game content</h1>

<div>
        <#list gamesContent as game>
            <div>Player one move : ${game.playerOneMove}</div>
            <div>Player two move : ${game.playerTwoMove} </div>
        </#list>
</div>

</html>