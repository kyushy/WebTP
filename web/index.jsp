    <%--  
        Document   : index  
        Created on : 16 sept. 2009, 16:54:32  
        Author     : michel buffa  
    --%>  
      
    <%@page contentType="text/html" pageEncoding="UTF-8"%>  
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
        "http://www.w3.org/TR/html4/loose.dtd">  
      
    <!-- Ne pas oublier cette ligne sinon tous les tags de la JSTL seront ignorés ! -->  
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
      
    <html>  
        <head>  
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
            <title>Gestionnaire d'utilisateurs</title>
            <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/style.css" />
        </head>  
        <body>
            <jsp:include page="header.jsp"/>
            
            <c:if test="${sessionScope.username != null}">
            <div class="userMenu">    
            <h1>Gestionnaire d'utilisateurs</h1>  
      
            <!-- DEBUG : Message qui s'affiche lorsque la page est appelé avec un paramètre http message -->  
            <c:if test="${!empty param['message']}">  
                <h2>Reçu message : ${param.message}</h2>  
            </c:if>  
      
            <h2>Menu de gestion des utilisateurs</h2>  
            <ul>  
                <li><a href="ServletUsers?action=listerLesUtilisateurs">Afficher/raffraichir la liste de tous les utilisateurs</a></li>  
                <p>  
            </ul>

            <ol>  
                <li><a href="ServletUsers?action=creerUtilisateursDeTest">Créer 30 utilisateurs de test</a></li>  
      
                <li>Créer un utilisateur</li>  
                <form action="ServletUsers" method="get">  
                    Nom : <input type="text" name="nom"/><br>  
                    Prénom : <input type="text" name="prenom"/><br>  
                    Login : <input type="text" name="login"/><br>
                    Mot de passe : <input type="text" name="password"/><br>  
                    <!-- Astuce pour passer des paramètres à une servlet depuis un formulaire JSP !-->  
                    <input type="hidden" name="action" value="creerUnUtilisateur"/>  
                    <input type="submit" value="Créer l'utilisateur" name="submit"/>  
                </form>  
      
                <li>Afficher les détails d'un utilisateur</li>  
                <form action="ServletUsers" method="get">  
                    login : <input type="text" name="login"/><br>  
                    <input type="hidden" name="action" value="chercherParLogin"/>  
                    <input type="submit" value="Chercher" name="submit"/>  
                </form>  
      
      
                <li>Modifier les détails d'un utilisateur :</li>  
                <form action="ServletUsers" method="get">  
                    Login : <input type="text" name="login"/><br>  
                    Nom : <input type="text" name="nom"/><br>  
                    Prénom : <input type="text" name="prenom"/><br>
                    Mot de passe : <input type="text" name="password"/><br> 
                    <input type="hidden" name="action" value="updateUtilisateur"/>  
                    <input type="submit" value="Mettre à jour" name="submit"/>  
                </form> 
                
                <li>Supprimer un utilisateur</li>  
                <form action="ServletUsers" method="get">  
                    login : <input type="text" name="login"/><br>  
                    <input type="hidden" name="action" value="supprimerUtilisateur"/>  
                    <input type="submit" value="Supprimer" name="submit"/>  
                </form>  
                
            </ol>  
            </div>
            </c:if>
            <!-- Fin du menu -->
            
            <div class="displayContainer">
            <!-- Zone qui affiche les utilisateurs si le paramètre action vaut listerComptes -->  
            <c:if test="${param['action'] == 'listerLesUtilisateurs'}" >  
                <h2>Liste des utilisateurs</h2>  
      
                <table class="listing" border="10">  
                    <!-- La ligne de titre du tableau des comptes -->  
                    <tr>  
                        <th><b>Login</b></th>  
                        <th><b>Nom</b></th>  
                        <th><b>Prénom</b></th>
                        <th><b>Adresses</b></th>
                        <th><b>Telephones</b></th>
                    </tr>  
      
                    <!-- Ici on affiche les lignes, une par utilisateur -->  
                    <!-- cette variable montre comment on peut utiliser JSTL et EL pour calculer -->  
                    <c:set var="total" value="0"/>  
      
                    <c:forEach var="u" items="${requestScope['listeDesUsers']}">
                        <c:choose>
                            <c:when test="${total%2 == 1}">
                                <tr class="alt"> 
                            </c:when>
                            <c:otherwise>
                                <tr> 
                            </c:otherwise>
                        </c:choose>
                            <td>${u.login}</td>  
                            <td>${u.firstname}</td>  
                            <td>${u.lastname}</td>
                            <td>
                                <c:forEach var="a" items="${u.adresses}">
                                    <p>${a.numNomRue} ${a.ville} ${a.codePostal} ${a.pays}</p>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="t" items="${u.telephones}">
                                    <p>${t.numero}</p>
                                </c:forEach>    
                            </td>
                            <!-- On compte le nombre de users -->  
                            <c:set var="total" value="${total+1}"/>  
                        </tr>  
                    </c:forEach>  
      
                    <!-- Affichage du solde total dans la dernière ligne du tableau -->  
                    <tr><th><b>TOTAL</b></th><th></th><th></th><th></th><th><b>${total}</b></th></tr>  
                </table>
                <c:if test="${total > 10}"><!-- Pagination -->
                    <table>
                        <tr>
                            <form action="ServletUsers" method="get">
                            <input type="hidden" name="action" value="paginationUtilisateur"/>
                            <td>
                                <input type="submit" value="<-" name="precedant"/>
                            </td>
                            <td>
                                <input type="submit" value="->" name="suivant"/>
                            </td>
                            <input type="hidden" name="depart" value="${requestScope['debutPagination']}"/>
                            <input type="hidden" name="fin" value="${requestScope['finPagination']}"/>
                            </form> 
                        </tr>
                    </table>
                </c:if>    
            </c:if>
            
            <!-- Zone qui affiche les utilisateurs si le paramètre action vaut listerUnUtilisateur -->  
            <c:if test="${param['action'] == 'listerUnUtilisateur'}" > 
                <h2>Utilisateur</h2>  
      
                <table class="listing" border="10">  
                    <!-- La ligne de titre du tableau des comptes -->  
                    <tr>  
                        <th><b>Login</b></th>  
                        <th><b>Nom</b></th>  
                        <th><b>Prénom</b></th>
                        <th><b>Mot de passe</b></th>
                    </tr>
                    
                    <tr>  
                        <td>${requestScope['user'].login}</td>  
                        <td>${requestScope['user'].firstname}</td>  
                        <td>${requestScope['user'].lastname}</td>
                        <td>${requestScope['user'].password}</td>
                    </tr>  
                </table>
                    
            </c:if>
            </div>    
        </body>  
    </html>  
