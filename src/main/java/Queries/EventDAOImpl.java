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
				Lieu lieu = clubdao.findByName(lieuNom);
				// On crée un DJDAO pour aller chercher le DJ via son id
				DJDAO djdao = new DJDAOImpl();
				if (djID!=null) {
					DJ dj = djdao.findByID(UUID.fromString(djID));
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
		
		afficherEvent();
		
		System.out.println("-------------------");
		
		DJDAO djdao = new DJDAOImpl();
		
		List<DJ> listeDJ = djdao.findByAll();
		DJ dj1 = listeDJ.get(0);
		
		EventDAO dao = new EventDAOImpl();
		
		List<Event> liste = dao.findByDJ(dj1);
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
		
		/*
		 
		DJDAO djdao = new DJDAOImpl();
		
		List<DJ> listeDJ = djdao.findByAll();
		DJ dj1 = listeDJ.get(0);
		DJ dj2 = listeDJ.get(1);
		DJ dj3 = listeDJ.get(2);
		DJ dj4 = listeDJ.get(3);
		DJ dj5 = listeDJ.get(4);

		
		ClubDAO clubdao = new ClubDAOImpl();
		
		List<Lieu> listeClubs = clubdao.findByAll();
		Lieu lieu1 = listeClubs.get(4);
		Lieu lieu2 = listeClubs.get(5);
		Lieu lieu3 = listeClubs.get(6);
		Lieu lieu4 = listeClubs.get(7);
		Lieu lieu5 = listeClubs.get(8);
		Lieu lieu6 = listeClubs.get(9);
		Lieu lieu7 = listeClubs.get(1);
		Lieu lieu8 = listeClubs.get(2);
		Lieu lieu9 = listeClubs.get(3);
		
		Date date6 = Date.valueOf(LocalDate.now().plusDays(80));
		Date date7 = Date.valueOf(LocalDate.now().plusDays(90));
		Date date8 = Date.valueOf(LocalDate.now().plusDays(120));
		Date date9 = Date.valueOf(LocalDate.now().plusDays(130));
		Date date10 = Date.valueOf(LocalDate.now().plusDays(115));
		Date date11 = Date.valueOf(LocalDate.now().plusDays(93));
		Date date12 = Date.valueOf(LocalDate.now().plusDays(162));
		Date date13 = Date.valueOf(LocalDate.now().plusDays(142));
		Date date14 = Date.valueOf(LocalDate.now().minusDays(25));
		Date date15 = Date.valueOf(LocalDate.now().minusDays(36));
		Date date16 = Date.valueOf(LocalDate.now().minusDays(42));
		Date date17 = Date.valueOf(LocalDate.now().minusDays(64));
		
		Time horaireDebut6 = Time.valueOf(LocalTime.of(16, 30));
		Time horaireDebut7 = Time.valueOf(LocalTime.of(17, 0));
		Time horaireDebut8 = Time.valueOf(LocalTime.of(14, 0));
		Time horaireDebut9 = Time.valueOf(LocalTime.of(13, 30));
		Time horaireDebut10 = Time.valueOf(LocalTime.of(8, 0));
		Time horaireDebut11 = Time.valueOf(LocalTime.of(16, 30));
		Time horaireDebut12 = Time.valueOf(LocalTime.of(20, 0));
		Time horaireDebut13 = Time.valueOf(LocalTime.of(20, 30));
		Time horaireDebut14 = Time.valueOf(LocalTime.of(20, 0));
		Time horaireDebut15 = Time.valueOf(LocalTime.of(19, 30));
		Time horaireDebut16 = Time.valueOf(LocalTime.of(14, 0));
		Time horaireDebut17 = Time.valueOf(LocalTime.of(15, 30));
		
		Time horaireFin6 = Time.valueOf(LocalTime.of(23, 0));
		Time horaireFin7 = Time.valueOf(LocalTime.of(0, 30));
		Time horaireFin8 = Time.valueOf(LocalTime.of(22, 0));
		Time horaireFin9 = Time.valueOf(LocalTime.of(1, 30));
		Time horaireFin10 = Time.valueOf(LocalTime.of(23, 0));
		Time horaireFin11 = Time.valueOf(LocalTime.of(4, 30));
		Time horaireFin12 = Time.valueOf(LocalTime.of(2, 0));
		Time horaireFin13 = Time.valueOf(LocalTime.of(22, 30));
		Time horaireFin14 = Time.valueOf(LocalTime.of(4, 0));
		Time horaireFin15 = Time.valueOf(LocalTime.of(22, 0));
		Time horaireFin16 = Time.valueOf(LocalTime.of(23, 30));
		Time horaireFin17 = Time.valueOf(LocalTime.of(21, 30));
		
		Event event7 = new Event("Event7" , dj2, lieu2, date7, horaireDebut7, horaireFin7);
		Event event8 = new Event("Event8" , dj3, lieu3, date8, horaireDebut8, horaireFin8);
		Event event9 = new Event("Event9" , dj4, lieu4, date9, horaireDebut9, horaireFin9);
		Event event10 = new Event("Event10" , dj5, lieu5, date10, horaireDebut10, horaireFin10);
		Event event11 = new Event("Event11" , dj3, lieu6, date11, horaireDebut11, horaireFin11);
		Event event12 = new Event("Event12" , dj2, lieu7, date12, horaireDebut12, horaireFin12);
		Event event13 = new Event("Event13" , dj3, lieu8, date13, horaireDebut13, horaireFin13);
		Event event14 = new Event("Event14" , dj1, lieu9, date14, horaireDebut14, horaireFin14);
		Event event15 = new Event("Event15" , dj4, lieu2, date15, horaireDebut15, horaireFin15);
		Event event16 = new Event("Event16" , dj1, lieu3, date16, horaireDebut16, horaireFin16);
		Event event17 = new Event("Event17" , dj3, lieu4, date17, horaireDebut17, horaireFin17);

		
		EventDAO eventDAO = new EventDAOImpl();
		
		eventDAO.insertEventtoDB(event7);
		eventDAO.insertEventtoDB(event8);
		eventDAO.insertEventtoDB(event9);
		eventDAO.insertEventtoDB(event10);
		eventDAO.insertEventtoDB(event11);
		eventDAO.insertEventtoDB(event12);
		eventDAO.insertEventtoDB(event13);
		eventDAO.insertEventtoDB(event14);
		eventDAO.insertEventtoDB(event15);
		eventDAO.insertEventtoDB(event16);
		eventDAO.insertEventtoDB(event17);
		
		afficherEvent();
		
		*/
		
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
            rs = statement.executeQuery("SELECT * FROM Events WHERE dj = '" + dj.getId().toString() + "'");
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
