package Models;
import java.sql.Date;
import java.util.UUID;

public class DJ {
    
    private UUID id;
    private String nom;
    private String prenom;
    private String nomDeScene;
    private Date dateDeNaissance;
    private String lieuDeResidence;
    private StyleMusical styleMusical;

    public DJ() {}
    
    public DJ(String _nom, String _prenom, String _nomDeScene, Date _dateDeNaissance, String _lieuDeResidence, StyleMusical _styleMusical) {
    	id = UUID.randomUUID(); // Génération de l'id
    	nom = _nom;
    	prenom = _prenom;
    	nomDeScene = _nomDeScene;
    	dateDeNaissance = _dateDeNaissance;
    	lieuDeResidence = _lieuDeResidence;
    	styleMusical = _styleMusical;
    }
    
    public DJ(UUID _id, String _nom, String _prenom, String _nomDeScene, Date _dateDeNaissance, String _lieuDeResidence, StyleMusical _styleMusical) {
    	id = _id;
    	nom = _nom;
    	prenom = _prenom;
    	nomDeScene = _nomDeScene;
    	dateDeNaissance = _dateDeNaissance;
    	lieuDeResidence = _lieuDeResidence;
    	styleMusical = _styleMusical;
    }
    
    public UUID getId() {
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

	@Override
	public String toString() {
		return "DJ [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", nomDeScene=" + nomDeScene
				+ ", dateDeNaissance=" + dateDeNaissance + ", lieuDeResidence=" + lieuDeResidence + ", styleMusical="
				+ styleMusical + "]";
	}
    
}

