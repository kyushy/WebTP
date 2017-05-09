<%-- 
    Document   : header
    Created on : 9 mai 2017, 17:54:59
    Author     : Frederic
--%>

<div class="header">  
    <a href="${pageContext.request.contextPath}">  
        <img src="http://unice.fr/++theme++ThemeUNS/assets/img/logo.png"/>
        <img src="http://miageprojet2.unice.fr/@api/deki/files/1946/=logo_miage-petit_transparent.png" style="margin-bottom: 40px; margin-left: 10px"/>
    </a>
    <div class="connectionForm">
        <form action="ServletUsers" method="post">  
            Login : <input type="text" name="login"/><br>
            Password : <input type="text" name="password"/><br> 
            <input type="hidden" name="action" value="connexion"/>  
            <input type="submit" value="connexion" name="submit"/>  
        </form>  
    </div>
</div>  