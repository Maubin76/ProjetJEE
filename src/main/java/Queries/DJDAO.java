package Queries;

import java.sql.Date;
import java.util.List; // List
import java.util.UUID;

import Models.DJ; // Classe DJ
import Models.StyleMusical;

public interface DJDAO {

	// Renvoie tous les DJ de la BDD
	public List<DJ> findByAll();
	// Insère un DJ dans la BDD
	public void insertDJtoDB(DJ dj);
	// Renvoie les DJ de nom de scene "nomDeScene"
	public List<DJ> findByNomDeScene(String nomDeScene);
	// Renvoie les DJ de nom de scene commençant par "nomDeScene"
	public List<DJ> findByPartialNomDeScene(String nomDeScene);
	// Renvoie le DJ d'ID "id"
	public DJ findByID(UUID id);
	// Supprime un DJ de la BDD
	public void deleteFromDB(DJ dj);
	// Modifie le champs "champs" à "valeur" du dj DJ
	// 2 surcharges selon l'argument
	public void modifyDJ(DJ dj, String champs, String valeur);
	public void modifyDJ(DJ dj, String champs, Date date);
	public void modifyDJ(DJ dj, String champs, StyleMusical style);
}
