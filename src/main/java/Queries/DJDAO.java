package Queries;

import java.util.List; // List

import Models.DJ; // Classe DJ

public interface DJDAO {

	// Renvoie tous les DJ de la BDD
	public List<DJ> findByAll();
	// Insère un DJ dans la BDD
	public void insertDJtoDB(DJ dj);
	// Renvoie les DJ de nom de scene "nomDeScene"
	public List<DJ> findByNomDeScene(String nomDeScene);
	// Supprime un DJ de la BDD
	public void deleteFromDB(DJ dj);
	
}
