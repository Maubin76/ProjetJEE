package Queries;

import java.util.List; // List

import Models.DJ; // Classe DJ

public interface DJDAO {

	// Renvoie tous les DJ de la BDD
	public List<DJ> findByAll();
	// Insère un DJ dans la BDD
	public void insertDJtoDB(DJ dj);
	
}
