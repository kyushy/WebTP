/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateurs.gestionnaires;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utilisateurs.modeles.Adresse;
import utilisateurs.modeles.Telephone;
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
        creeUtilisateur("Lenore", "Hoffman", "Myles Marshall", "12345");
        creeUtilisateur("Illana", "Sheppard", "Alana Dunlap", "12345");
        creeUtilisateur("Macaulay", "Baldwin", "Kirsten Meyer", "12345");
        creeUtilisateur("Allegra", "Hale", "Julie Shields", "12345");
        creeUtilisateur("Aurelia", "Spencer", "Buffy Sanchez", "12345");
        creeUtilisateur("Hayden", "Trujillo", "Rina Mcguire", "12345");
        creeUtilisateur("Kelly", "Atkins", "Abigail Brennan", "12345");
        creeUtilisateur("Gil", "Morales", "Ralph Jacobson", "12345");
        creeUtilisateur("Emi", "Macdonald", "Zelenia Williams", "12345");
        creeUtilisateur("Jana", "Velasquez", "Lester Underwood", "12345");
        creeUtilisateur("Jamalia", "Tyler", "Dean Barton", "12345");
        creeUtilisateur("Kenneth", "Goff", "Whoopi Ortiz", "12345");
        creeUtilisateur("Edan", "Price", "Mason Willis", "12345");
        creeUtilisateur("Raymond", "Oneill", "Amos Mccullough", "12345");
        creeUtilisateur("Allen", "Sykes", "Hamilton Kline", "12345");
        creeUtilisateur("Kasper", "King", "Celeste Beard", "12345");
        creeUtilisateur("Lucy", "Ball", "Harriet Strickland", "12345");
        creeUtilisateur("Jael", "Jacobs", "Fuller Greene", "12345");
        creeUtilisateur("Ashton", "Frederick", "Kirk Zamora", "12345");
        creeUtilisateur("Arsenio", "Burks", "Vance Pitts", "12345");
        creeUtilisateur("Ann", "Conway", "Colt Macdonald", "12345");
        creeUtilisateur("Maia", "Whitley", "Ronan Bond", "12345");
        creeUtilisateur("Martina", "Gates", "Aimee Barron", "12345");
        creeUtilisateur("Cruz", "Buckner", "Meredith Mcmahon", "12345");
        creeUtilisateur("May", "Massey", "Felicia Clayton", "12345");
        creeUtilisateur("Ruby", "Cross", "Serena Stephenson", "12345"); 
        creeUtilisateur("Rachel", "Moon", "Lydia Mullen", "12345");
    }  
  
    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String pass) {  
        Utilisateur u = new Utilisateur(login, prenom, nom, pass);
        
        Collection<Adresse> adresses = new ArrayList<>();
        Adresse a = new Adresse("360 blabla", "04100", "man", "France");
        em.persist(a);
        Adresse a2 = new Adresse("410 blublu", "04500", "min", "France");
        em.persist(a2);
        adresses.add(a);
        adresses.add(a2);
        
        Collection<Telephone> telephones = new ArrayList<>();
        Telephone t = new Telephone("0492722973");
        em.persist(t);
        telephones.add(t);
        
        u.setTelephones(telephones);
        u.setAdresses(adresses);
        em.persist(u);  
        return u;  
    }  
  
    public Collection<Utilisateur> getAllUsers() {  
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select u from Utilisateur u");  
        return q.getResultList();  
    }
    
    public Collection<Utilisateur> paginationUtilisateur(int debut, int fin) {
        //Recuperation des utilisateurs entre une posisition de depart et de fin
        Query q = em.createQuery("select u from Utilisateur u").setMaxResults(fin).setFirstResult(debut);
        return q.getResultList();
        
    }
    
    public int nombreUtilisateur() {
        //recuperation du nombre max d'utilisateurs
        Query q = em.createQuery("select count(u) from Utilisateur u");
        return (int)q.getSingleResult();
        
    }
    
    public Utilisateur getUser(String login){
        Query q = em.createQuery("select u from Utilisateur u where u.login= :login").
                setParameter("login", login);
        return (Utilisateur) q.getSingleResult();
    }
    
    public Boolean userExist(String login, String password){
        Query q = em.createQuery("select u from Utilisateur u where u.login= :login and u.password= :password").
                setParameter("login", login).
                setParameter("password", password);
        return q.getSingleResult() != null;
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
