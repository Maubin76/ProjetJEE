package Queries;

import java.util.List; // List

import Models.DJ; // Classe DJ

public interface DJDAO {

	// Renvoie tous les DJ de la BDD
	List<DJ> findByAll();
	
}
