package astrofili.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import astrofili.control.GestioneArticoli;
import astrofili.database.DBManager;
import astrofili.entity.Articolo;
import astrofili.entity.OggettoCeleste;
import astrofili.enumeration.TipoOggetto;

public class TestRicercaArticoli {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Connection conn = DBManager.getConnection();
		// ovviamente non ha un campo per la lista dei commenti
		// in generale anche autore è una foreign key e non può essere NULL
		String createOggettoCelesteTable = "CREATE TABLE OGGETTOCELESTE("
				+ "IDOGGETTO INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "TIPOOGGETTOCELESTE VARCHAR(18) NOT NULL, "
				+ "NOME VARCHAR(50), "
				+ "GALASSIADIAPPARTENENZA INT, "
				+ "SISTEMAPLANETARIODIAPPARTENENZA INT, "
				+ "CENTROSTELLARE INT, "
				+ "FOREIGN KEY (GALASSIADIAPPARTENENZA) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE SET NULL, "
				+ "FOREIGN KEY (SISTEMAPLANETARIODIAPPARTENENZA) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE SET NULL, "
				+ "FOREIGN KEY (CENTROSTELLARE) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE SET NULL);";
		String createArticoloTable = "CREATE TABLE ARTICOLO("
				+ "IDARTICOLO INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "TITOLO VARCHAR(30) NOT NULL, "
				+ "OGGETTOARTICOLO VARCHAR(50), "
				+ "CORPO VARCHAR(255), "
				+ "DATAINSERIMENTO DATE, "
				+ "NUMEROLIKE INT, "
				+ "NUMERODISLIKE INT, "
				+ "OGGETTOCELESTE INT NOT NULL, "
				+ "AUTORE INT, "
				+ "FOREIGN KEY (OGGETTOCELESTE) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE CASCADE);";
		
		try(Statement stmt = conn.createStatement()) {
			stmt.execute(createOggettoCelesteTable);
			stmt.execute(createArticoloTable);
		} catch(SQLException e) {
			e.printStackTrace();
		};
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Connection conn = DBManager.getConnection();
		String dropArticolo = "DROP TABLE IF EXISTS ARTICOLO;";
		String dropOggettoCeleste = "DROP TABLE IF EXISTS OGGETTOCELESTE;";
		
		try(Statement stmt = conn.createStatement()) {
			stmt.execute(dropArticolo);
			stmt.execute(dropOggettoCeleste);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		DBManager.closeConnection();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		Connection conn = DBManager.getConnection();
		
		String cleanWithCascade = "DELETE FROM OGGETTOCELESTE";
		
		try(Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(cleanWithCascade);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void preCondition(ArrayList<OggettoCeleste> objs, ArrayList<Articolo> arts) throws Exception{
		// TODO - riutilizzare questo codice per le classi DAO
		
		Connection conn = DBManager.getConnection();
		
		String insertObjs = "INSERT INTO OGGETTOCELESTE(TIPOOGGETTOCELESTE) VALUES(?);";
		String insertArts = "INSERT INTO ARTICOLO(TITOLO, OGGETTOCELESTE) "
				+ "VALUES(?, ?);";
		
		PreparedStatement stmt = conn.prepareStatement(insertObjs, Statement.RETURN_GENERATED_KEYS);
		for(OggettoCeleste obj : objs) {
			stmt.setString(1, obj.getTipoOggettoCeleste().toString());
			stmt.executeUpdate();

			ResultSet result = stmt.getGeneratedKeys();
			if(result.next()) {
				obj.setIdOggetto(result.getInt("IDOGGETTO"));
			}
		}
		
		stmt = conn.prepareStatement(insertArts, Statement.RETURN_GENERATED_KEYS);
		for(Articolo art : arts) {
			stmt.setString(1, art.getTitolo().toUpperCase());
			stmt.setInt(2, art.getOggettoCeleste().getIdOggetto());
			stmt.executeUpdate();
			
			ResultSet result = stmt.getGeneratedKeys();
			if(result.next()) {
				art.setIdArticolo(result.getInt("IDARTICOLO"));
			}
		}
	}
	
	// TODO - cambiare tutti i test alla luce dell'introduzione della gerarchia
	
	@Test
	public void test_01() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		Articolo art = new Articolo("Prova", obj);
		
		objs.add(obj);
		arts.add(art);
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo("BadInput", null);
			assertEquals(0, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_02() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		Articolo art = new Articolo("Prova", obj);
		
		objs.add(obj);
		arts.add(art);
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo("Prova", null);
			assertEquals(1, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}

	@Test
	public void test_03() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		Articolo art = new Articolo("Prova", obj);
		
		objs.add(obj);
		arts.add(art);
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo(null, TipoOggetto.Stella);
			assertEquals(0, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_04() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		Articolo art = new Articolo("Prova", obj);
		
		objs.add(obj);
		arts.add(art);
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo(null, TipoOggetto.Pianeta);
			assertEquals(1, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_05() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		objs.add(obj);
		
		Articolo art = new Articolo("Prova", obj);
		arts.add(art);
		art = new Articolo("Secondo", obj);
		arts.add(art);
		
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo("BadInput", null);
			assertEquals(0, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_06() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		objs.add(obj);
		
		Articolo art = new Articolo("Prova", obj);
		arts.add(art);
		art = new Articolo("Secondo", obj);
		arts.add(art);
		
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo("con", null);
			assertEquals(1, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_07() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		objs.add(obj);
		Articolo art = new Articolo("Prova", obj);
		arts.add(art);
		
		obj = new OggettoCeleste(null, TipoOggetto.Stella);
		objs.add(obj);
		art = new Articolo("Secondo", obj);
		arts.add(art);
		
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo(null, TipoOggetto.Galassia);
			assertEquals(0, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_08() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		objs.add(obj);
		Articolo art = new Articolo("Prova", obj);
		arts.add(art);
		
		obj = new OggettoCeleste(null, TipoOggetto.Stella);
		objs.add(obj);
		art = new Articolo("Secondo", obj);
		arts.add(art);
		
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo(null, TipoOggetto.Pianeta);
			assertEquals(1, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_09() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		objs.add(obj);
		Articolo art = new Articolo("Prova", obj);
		arts.add(art);
		
		obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		objs.add(obj);
		art = new Articolo("Secondo", obj);
		arts.add(art);
		
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo("o", TipoOggetto.Pianeta);
			assertEquals(2, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_10() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo("BadInput", null);
			assertEquals(0, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
	@Test
	public void test_11() {
		ArrayList<OggettoCeleste> objs = new ArrayList<OggettoCeleste>();
		ArrayList<Articolo> arts = new ArrayList<Articolo>();
		
		ArrayList<Articolo> output;
		
		OggettoCeleste obj = new OggettoCeleste(null, TipoOggetto.Pianeta);
		Articolo art = new Articolo("Prova", obj);
		
		objs.add(obj);
		arts.add(art);
		
		try {
			preCondition(objs, arts);
			output = GestioneArticoli.ricercaArticolo(null, null);
			assertEquals(1, output.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred");
		}
	}
	
}
