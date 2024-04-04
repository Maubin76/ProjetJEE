package Queries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Models.DJ;
import Models.Event;
import Models.Lieu;
import Models.StyleMusical;

public class EventDAOImpl extends EventDAO {
	
	@Override
	public void addDjtoEvent(DJ dj, Event event) {
		
		String djID = dj.getId().toString();
		String nom = event.getNom();
		
		try (Connection connection = DBManager.getInstance().getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Events SET dj = ? WHERE nom = ?")) {

		        preparedStatement.setString(1, djID);
		        preparedStatement.setString(2, nom);

		        int rowsUpdated = preparedStatement.executeUpdate();
		        System.out.println(rowsUpdated + " lignes mises à jour.");
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	@Override
	public List<Event> findByNom(String _nom) {
		
		List<Event> resultList = new ArrayList<Event>();
		
		Connection connection = DBManager.getInstance().getConnection();
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT * FROM Events WHERE nom = '" + _nom + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String nom = rs.getString("nom");
				String djID = rs.getString("dj");
				String lieuNom = rs.getString("lieu");
				Date date = rs.getDate("date");
				Time horaireDebut = rs.getTime("horaireDebut");
				Time horaireFin = rs.getTime("horaireFin");
				ClubDAO clubdao = new ClubDAOImpl();
				// On crée un ClubDAO pour aller chercher le club via le nom
				Lieu lieu = clubdao.findByName(lieuNom);
				// On crée un DJDAO pour aller chercher le DJ via son id
				DJDAO djdao = new DJDAOImpl();
				if (djID!=null) {
					DJ dj = djdao.findByID(UUID.fromString(djID));
					Event event = new Event(nom, dj, lieu, date, horaireDebut, horaireFin);
					resultList.add(event);
				}
				else {
					Event event = new Event(nom, null, lieu, date, horaireDebut, horaireFin);
					resultList.add(event);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	@Override
	public void insertEventtoDB(Event event) {
		
		// Attributs de l'event
		String nom = event.getNom();
		DJ dj = event.getDj();
		Lieu lieu = event.getLieu();
		Date date = event.getDate();
		Time horaireDebut = event.getHoraireDebut();
		Time horaireFin = event.getHoraireFin();
		
		// Convertion utilisable SQL
		String lieuNom = lieu.getNomLieu();
		Time horaireDebutSQL = horaireDebut;
		Time horaireFinSQL = horaireFin;
		
		if(dj == null) {
			String sql = "INSERT INTO `info_captainm_schema`.`Events` "
					+ "(`nom`, `lieu`, `date`, `horaireDebut`, `horaireFin`)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)";
		
			try {
				Connection connection = DBManager.getInstance().getConnection();
				
				// Permet d'éviter les injections SQL
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
				preparedStatement.setString(1, nom);
				preparedStatement.setString(2, lieuNom);
				preparedStatement.setDate(3, date);
				preparedStatement.setTime(4, horaireDebutSQL);
				preparedStatement.setTime(5, horaireFinSQL);
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				preparedStatement.close();
				
				if(rowsAffected < 0) {
					System.err.println("Aucune colonne modifiée");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Connection connection = DBManager.getInstance().getConnection();
			
			// Convertion utilisable SQL
			String djID = dj.getId().toString();
			
			/* Connection connection = null;
			try {
				connection = DBConnection.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} */
			
			String sql = "INSERT INTO `info_captainm_schema`.`Events` "
						+ "(`nom`, `dj`, `lieu`, `date`, `horaireDebut`, `horaireFin`)"
						+ "VALUES "
						+ "(?, ?, ?, ?, ?, ?)";
			
			try {
				// Permet d'éviter les injections SQL
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
				preparedStatement.setString(1, nom);
				preparedStatement.setString(2, djID);
				preparedStatement.setString(3, lieuNom);
				preparedStatement.setDate(4, date);
				preparedStatement.setTime(5, horaireDebutSQL);
				preparedStatement.setTime(6, horaireFinSQL);
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				preparedStatement.close();
				
				if(rowsAffected < 0) {
					System.err.println("Aucune colonne modifiée");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	@Override
	public List<Event> findByAll() {
		
		List<Event> resultList = new ArrayList<Event>();
		
		Connection connection = DBManager.getInstance().getConnection();
		/*
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT * FROM Events");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String nom = rs.getString("nom");
				String djID = rs.getString("dj");
				String lieuNom = rs.getString("lieu");
				Date date = rs.getDate("date");
				Time horaireDebut = rs.getTime("horaireDebut");
				Time horaireFin = rs.getTime("horaireFin");
				
				// On crée un DJDAO pour aller chercher le DJ via son id
				DJDAO djdao = new DJDAOImpl();
				DJ dj = djdao.findByID(UUID.fromString(djID));
				// On crée un ClubDAO pour aller chercher le club via le nom
				ClubDAO clubdao = new ClubDAOImpl();
				Lieu lieu = clubdao.findByName(lieuNom);
				
				Event event = new Event(nom, dj, lieu, date, horaireDebut, horaireFin);
				
				resultList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<Event> eventListBetweenDates(Date dateMin, Date dateMax) {
		List<Event> resultList = new ArrayList<Event>();
		
		Connection connection = DBManager.getInstance().getConnection();
		/*
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		ResultSet rs = null;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Events WHERE date BETWEEN ? AND ?");
			statement.setDate(1, dateMin);
			statement.setDate(2, dateMax);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String nom = rs.getString("nom");
				String djID = rs.getString("dj");
				String lieuNom = rs.getString("lieu");
				Date date = rs.getDate("date");
				Time horaireDebut = rs.getTime("horaireDebut");
				Time horaireFin = rs.getTime("horaireFin");
				DJ dj;
				// On crée un DJDAO pour aller chercher le DJ via son id
				DJDAO djdao = new DJDAOImpl();
				if (djID!=null) {
					dj = djdao.findByID(UUID.fromString(djID));
				}else {
					dj=null;
				}
				// On crée un ClubDAO pour aller chercher le club via le nom
				ClubDAO clubdao = new ClubDAOImpl();
				Lieu lieu = clubdao.findByName(lieuNom);
				
				Event event = new Event(nom, dj, lieu, date, horaireDebut, horaireFin);
				
				resultList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		/*
		afficherEvent();
		
		DJDAO djdao = new DJDAOImpl();
		
		List<DJ> listeDJ = djdao.findByAll();
		DJ dj1 = listeDJ.get(0);
		DJ dj2 = listeDJ.get(1);
		DJ dj3 = listeDJ.get(2);
		DJ dj4 = listeDJ.get(3);
		
		ClubDAO clubdao = new ClubDAOImpl();
		
		List<Lieu> listeClubs = clubdao.findByAll();
		Lieu lieu5 = listeClubs.get(4);
		
		Date date5 = Date.valueOf(LocalDate.now().plusDays(20));
		
		Time horaireDebut5 = Time.valueOf(LocalTime.of(16, 0));
		
		Time horaireFin5 = Time.valueOf(LocalTime.of(23, 0));
		
		Event event5 = new Event("Event6" , null, lieu5, date5, horaireDebut5, horaireFin5);
		
		EventDAO eventDAO = new EventDAOImpl();
		
		eventDAO.insertEventtoDB(event5);
		
		
		
		afficherEvent();*/
		
	}
	
	private static void afficherEvent() {
		EventDAO dao = new EventDAOImpl();
		
		// On affiche tous les noms de scène
		List<Event> liste = dao.findByAll();
		for(int i=0; i<liste.size(); i++) {
			String nom = liste.get(i).getNom();
			String dj = liste.get(i).getDj().getNom().toString();
			String lieu = liste.get(i).getLieu().getNomLieu();
			String date = liste.get(i).getDate().toString();
			String horaireDebut = liste.get(i).getHoraireDebut().toString();
			String horaireFin = liste.get(i).getHoraireFin().toString();
			
			String djString = (i+1) + " " + nom + " " + dj + " " + lieu + " " + date + " " + 
					horaireDebut + " " + horaireFin;
			System.out.println(djString);
		}
	}
	
	@Override
    public List<Event> findByDJ(DJ dj) {

        List<Event> resultList = new ArrayList<Event>();

        Connection connection = DBManager.getInstance().getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
        	if (dj!=null) {
                rs = statement.executeQuery("SELECT * FROM Events WHERE dj = '" + dj.getId().toString() + "'");
        	}else {
                rs = statement.executeQuery("SELECT * FROM Events WHERE dj is null");

        	}
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            while(rs.next()) {
                String nom = rs.getString("nom");
                String djID = rs.getString("dj");
                String lieuNom = rs.getString("lieu");
                Date date = rs.getDate("date");
                Time horaireDebut = rs.getTime("horaireDebut");
                Time horaireFin = rs.getTime("horaireFin");
                ClubDAO clubdao = new ClubDAOImpl();
                Lieu lieu = clubdao.findByName(lieuNom);
                // On crée un DJDAO pour aller chercher le DJ via son id
                DJDAO djdao = new DJDAOImpl();
                if (djID!=null) {
                    Event event = new Event(nom, dj, lieu, date, horaireDebut, horaireFin);
                    resultList.add(event);
                }
                // On crée un ClubDAO pour aller chercher le club via le nom
                else {
                    Event event = new Event(nom, null, lieu, date, horaireDebut, horaireFin);
                    resultList.add(event);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultList;
    }
	
	
}
