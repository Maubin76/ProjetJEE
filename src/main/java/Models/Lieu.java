package Models;

public class Lieu {

    private String nomLieu;
    private String ville;
    private String pays;
    private String continent;

    public Lieu(String nom, String ville, String pays, String continent) {
    	this.nomLieu = nom;
    	this.ville = ville;
    	this.pays = pays;
    	this.continent = continent;
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
