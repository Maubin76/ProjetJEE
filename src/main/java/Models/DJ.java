package Models;
import java.sql.Date;

public class DJ {
    
    private int id;
    private String nom;
    private String prenom;
    private String nomDeScene;
    private Date dateDeNaissance;
    private String lieuDeResidence;
    private StyleMusical styleMusical;

    public int getId() {
    	return id;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomDeScene() {
        return nomDeScene;
    }

    public void setNomDeScene(String nomDeScene) {
        this.nomDeScene = nomDeScene;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getLieuDeResidence() {
        return lieuDeResidence;
    }

    public void setLieuDeResidence(String lieuDeResidence) {
        this.lieuDeResidence = lieuDeResidence;
    }

    public StyleMusical getStyleMusical() {
        return styleMusical;
    }

    public void setStyleMusical(StyleMusical styleMusical) {
        this.styleMusical = styleMusical;
    }
}

