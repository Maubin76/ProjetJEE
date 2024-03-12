package Queries;

import java.util.List;

import Models.Lieu;

public abstract class ClubDAO {

	// Permet d'insérer un lieu à la base de donnée
	public abstract void insertClubtoDB(Lieu lieu);
	public abstract List<Lieu> findByAll();
	public abstract Lieu findByName(String nom);
}
