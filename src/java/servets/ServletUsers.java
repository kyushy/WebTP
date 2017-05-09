/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilisateurs.gestionnaires.GestionnaireUtilisateurs;
import utilisateurs.modeles.Utilisateur;

/**
 *
 * @author frederic
 */
@WebServlet(name = "ServletUsers", urlPatterns = {"/ServletUsers"})
public class ServletUsers extends HttpServlet {

    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             // Pratique pour décider de l'action à faire  
        String action = request.getParameter("action");  
        String forwardTo = "";  
        String message = "";  
  
        if (action != null) {  
            
            //GET Action pour afficher la liste des utilisateurs
            if (action.equals("listerLesUtilisateurs")) {  
                Collection<Utilisateur> liste = gestionnaireUtilisateurs.getAllUsers();  
                request.setAttribute("listeDesUsers", liste);  
                forwardTo = "index.jsp?action=listerLesUtilisateurs";  
                message = "Liste des utilisateurs";  
            
            //GET Creation des utilisateurs de test
            } else if (action.equals("creerUtilisateursDeTest")) {  
                gestionnaireUtilisateurs.creerUtilisateursDeTest();  
                Collection<Utilisateur> liste = gestionnaireUtilisateurs.getAllUsers();  
                request.setAttribute("listeDesUsers", liste);  
                forwardTo = "index.jsp?action=listerLesUtilisateurs";  
                message = "Liste des utilisateurs"; 
            
            //GET Creation d'un utilisateur
            } else if (action.equals("creerUnUtilisateur")) {
                gestionnaireUtilisateurs.creeUtilisateur(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("login"), request.getParameter("password"));
                Collection<Utilisateur> liste = gestionnaireUtilisateurs.getAllUsers();  
                request.setAttribute("listeDesUsers", liste);  
                forwardTo = "index.jsp?action=listerLesUtilisateurs";  
                message = "Utilisateur "+ request.getParameter("nom") +" crée";
            
            //GET Recherche par login
            } else if (action.equals("chercherParLogin")) {
                Utilisateur user = gestionnaireUtilisateurs.getUser(request.getParameter("login"));
                request.setAttribute("user", user);  
                forwardTo = "index.jsp?action=listerUnUtilisateur";  
                message = "Détail de l'Utilisateur "+ request.getParameter("login");
            
            //GET Mettre à jour un utilisateur    
            } else if (action.equals("updateUtilisateur")) {
                Utilisateur user = gestionnaireUtilisateurs.updateUser(request.getParameter("login"), request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("password"));
                request.setAttribute("user", user);
                forwardTo = "index.jsp?action=listerUnUtilisateur";  
                message = "Modification de l'Utilisateur "+ request.getParameter("login");
            
            //GET Supprimer un utilisateur    
            } else if (action.equals("supprimerUtilisateur")) {
                gestionnaireUtilisateurs.deleteUser(request.getParameter("login"));
                Collection<Utilisateur> liste = gestionnaireUtilisateurs.getAllUsers();
                request.setAttribute("listeDesUsers", liste);
                forwardTo = "index.jsp?action=listerLesUtilisateurs";  
                message = "Utilisateur "+ request.getParameter("login") + "supprimé"; 
            
            //Erreur ?
            } else {  
                forwardTo = "index.jsp?action=todo";  
                message = "La fonctionnalité pour le paramètre " + action + " est à implémenter !";  
            }  
        }  
  
        RequestDispatcher dp = request.getRequestDispatcher(forwardTo + "&message=" + message);  
        dp.forward(request, response);  
        // Après un forward, plus rien ne peut être exécuté après !  
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        
            //POST Connexion    
            if (action.equals("connexion")) {
                String login = request.getParameter("login");  
                String password = request.getParameter("password");
                if(gestionnaireUtilisateurs.userExist(login, password)){
                    HttpSession session = request.getSession();
                    session.setAttribute("username", login);
                }
                response.sendRedirect(request.getContextPath());
            }
            
            //POST Deconnexion
            else if (action.equals("deconnexion")) {
                HttpSession session = request.getSession(true);
                session.invalidate();
                response.sendRedirect(request.getContextPath());
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
