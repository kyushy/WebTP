<%-- 
    Document   : header
    Created on : 9 mai 2017, 17:54:59
    Author     : Frederic
--%>

<div>  
    <a href="${pageContext.request.contextPath}">  
        <img src="http://miageprojet2.unice.fr/@api/deki/files/1987/=logo.jpg"/>  
    </a>
    <div class="header">
        <form action="ServletUsers" method="post">  
            Login : <input type="text" name="login"/><br>
            Password : <input type="text" name="password"/><br> 
            <input type="hidden" name="action" value="connexion"/>  
            <input type="submit" value="connexion" name="submit"/>  
        </form>  
    </div>
</div>  