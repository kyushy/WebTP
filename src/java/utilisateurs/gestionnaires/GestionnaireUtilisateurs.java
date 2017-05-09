/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateurs.gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utilisateurs.modeles.Utilisateur;

/**
 *
 * @author frederic
 */
@Stateless
public class GestionnaireUtilisateurs {
    // Ici injection de code : on n'initialise pas. L'entity manager sera créé  
    // à partir du contenu de persistence.xml  
    @PersistenceContext  
    private EntityManager em;  
  
    public void creerUtilisateursDeTest() {  
        creeUtilisateur("John", "Lennon", "jlennon", "len0nn");  
        creeUtilisateur("Paul", "Mac Cartney", "pmc", "ppmn");  
        creeUtilisateur("Ringo", "Starr", "rstarr", "passw0");  
        creeUtilisateur("Georges", "Harisson", "georgesH", "Har1s0n");  
    }  
  
    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String pass) {  
        Utilisateur u = new Utilisateur(login, prenom, nom, pass);  
        em.persist(u);  
        return u;  
    }  
  
    public Collection<Utilisateur> getAllUsers() {  
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select u from Utilisateur u");  
        return q.getResultList();  
    }
    
    public Utilisateur getUser(String login){
        Query q = em.createQuery("select u from Utilisateur u where u.login= :login").
                setParameter("login", login);
        return (Utilisateur) q.getSingleResult();
    }
    
    public Utilisateur updateUser(String login, String nom, String prenom, String pass){
        Utilisateur user = getUser(login);
        user.setFirstname(prenom);
        user.setLastname(nom);
        user.setPassword(pass);
        em.merge(user);
        return user;   
    }
    
    public Utilisateur deleteUser(String login){
        Utilisateur user = getUser(login);
        em.remove(user);
        return user;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}