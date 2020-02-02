package astrofili.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import astrofili.entity.Galassia;
import astrofili.entity.OggettoCeleste;
import astrofili.entity.Pianeta;
import astrofili.entity.Sistema;
import astrofili.entity.SistemaPlanetario;
import astrofili.entity.SistemaStellare;
import astrofili.entity.Stella;
import astrofili.enumeration.TipoOggetto;

public class OggettoCelesteDAO {
	
	private Connection conn;
	private SatelliteNaturaleDAO interfacciaSatelliti = new SatelliteNaturaleDAO();
	
	public OggettoCeleste create(OggettoCeleste oggetto) throws SQLException, DAOException {
		conn = DBManager.getConnection();
		
		PreparedStatement stmt = this.InheritanceToTable(oggetto);
		try {
			stmt.executeUpdate();
			ResultSet result = stmt.getGeneratedKeys();
			if(result.next()) {
				oggetto.setIdOggetto(result.getInt("IDOGGETTO"));
			}
		} catch(SQLException e) {
			throw new DAOException("Creation of object failed; abort transaction");
		}
		return oggetto;
	} 

	public OggettoCeleste read(int idOggetto) throws SQLException, DAOException {
		conn = DBManager.getConnection();
		
		String query = "SELECT * FROM OGGETTOCELESTE WHERE IDOGGETTO = ?;";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setInt(1, idOggetto);
		
		ResultSet result = stmt.executeQuery();
		if(result.next()) {
			return this.TableToInheritance(result);
		} else {
			throw new DAOException("Row not found");
		}
	}
	
	public List<? extends OggettoCeleste> read(OggettoCeleste oggetto) throws SQLException, DAOException {
		// TODO - confronta i campi
		throw new DAOException("Not implemented");
	}
	
	public OggettoCeleste update(OggettoCeleste oggetto) throws SQLException {
		// TODO
		return null;
	}
	
	public Boolean delete(OggettoCeleste oggetto) throws SQLException {
		// TODO
		return null;
	}
	
	private OggettoCeleste TableToInheritance(ResultSet result) throws SQLException, DAOException {
		TipoOggetto tipo = TipoOggetto.valueOf(result.getString("TIPOOGGETTOCELESTE"));
		
		Galassia galassiaDiAppartenenza;
		try {
			galassiaDiAppartenenza = (Galassia) this.read(result.getInt("GALASSIADIAPPARTENENZA"));
		} catch (DAOException e) {
			galassiaDiAppartenenza = null;
		} 
		SistemaPlanetario sistemaPlanetarioDiAppartenenza;
		try {
			sistemaPlanetarioDiAppartenenza = (SistemaPlanetario) this.read(result.getInt("SISTEMAPLANETARIODIAPPARTENENZA"));
		} catch (DAOException e) {
			sistemaPlanetarioDiAppartenenza = null;
		}
		SistemaStellare centroStellare;
		try {
			centroStellare = (SistemaStellare) this.read(result.getInt("CENTROSTELLARE"));
		} catch (DAOException e) {
			centroStellare = null;
		}
		SistemaStellare sistemaStellareDiAppartenenza;
		try {
			sistemaStellareDiAppartenenza = (SistemaStellare) this.read(result.getInt("SISTEMASTELLAREDIAPPARTENENZA"));
		} catch (DAOException e) {
			sistemaStellareDiAppartenenza = null;
		}
		
		switch(tipo) {
		
		case Pianeta:
			Pianeta pianeta = new Pianeta(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					galassiaDiAppartenenza,
					sistemaPlanetarioDiAppartenenza,
					centroStellare,
					sistemaStellareDiAppartenenza);
			
			try {
				pianeta.setListaSatelliti(interfacciaSatelliti.read(pianeta));
			} catch (DAOException e) {
				// nothing to do
			}
			return pianeta;
			
		case Stella:
			Stella stella = new Stella(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					galassiaDiAppartenenza,
					sistemaPlanetarioDiAppartenenza,
					centroStellare,
					sistemaStellareDiAppartenenza);
			
			return stella;
			
		case SistemaPlanetario:
			SistemaPlanetario sistemaPlanetario = new SistemaPlanetario(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					galassiaDiAppartenenza,
					sistemaPlanetarioDiAppartenenza,
					centroStellare,
					sistemaStellareDiAppartenenza);
			
			Pianeta queryPianeta = new Pianeta("");
			queryPianeta.setSistemaPlanetarioDiAppartenenza(sistemaPlanetario);
			try {
				sistemaPlanetario.setListaPianeti((List<Pianeta>) this.read(queryPianeta));
			} catch (DAOException e) {
				// nothing to do
			}
			return sistemaPlanetario;
			
		case SistemaStellare:
			SistemaStellare sistemaStellare = new SistemaStellare(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					galassiaDiAppartenenza,
					sistemaPlanetarioDiAppartenenza,
					centroStellare,
					sistemaStellareDiAppartenenza);
			
			Stella queryStella = new Stella("");
			queryStella.setSistemaStellareDiAppartenenza(sistemaStellare);
			try {
				sistemaStellare.setListaStelle((List<Stella>) this.read(queryStella));
			} catch (DAOException e) {
				// nothing to do
			}
			return sistemaStellare;
			
		case Galassia:
			Galassia galassia = new Galassia(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					galassiaDiAppartenenza,
					sistemaPlanetarioDiAppartenenza,
					centroStellare,
					sistemaStellareDiAppartenenza);
			
			Sistema querySistema = new Sistema("");
			querySistema.setGalassiaDiAppartenenza(galassia);
			try {
				galassia.setListaSistemi((List<Sistema>) this.read(querySistema));
			} catch (DAOException e) {
				// nothing to do
			}
			return galassia;
		
		default:
			throw new DAOException("Problem with the object type");
		}
	}
	
	private PreparedStatement InheritanceToTable(OggettoCeleste oggetto) throws SQLException, DAOException {
		String query = "INSERT INTO OGGETTOCELESTE (TIPOOGGETTOCELESTE, NOME, GALASSIADIAPPARTENENZA, "
				+ "SISTEMAPLANETARIODIAPPARTENENZA, CENTROSTELLARE, SISTEMASTELLAREDIAPPARTENENZA) VALUES("
				+ "?, ?, ?, ?, ?, ?);";
		PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(2, oggetto.getNome());
		if(oggetto instanceof Pianeta) {
			stmt.setString(1, TipoOggetto.Pianeta.toString());
			if(((Pianeta) oggetto).getSistemaPlanetarioDiAppartenenza() != null) {
				stmt.setInt(4, ((Pianeta) oggetto).getSistemaPlanetarioDiAppartenenza().getIdOggetto());
			} else {
				stmt.setNull(4, java.sql.Types.INTEGER);
			}
			stmt.setNull(3, java.sql.Types.INTEGER);
			stmt.setNull(5, java.sql.Types.INTEGER);
			stmt.setNull(6, java.sql.Types.INTEGER);
		} else if(oggetto instanceof Stella) {
			stmt.setString(1, TipoOggetto.Stella.toString());
			if(((Stella) oggetto).getSistemaStellareDiAppartenenza() != null) {
				stmt.setInt(6, ((Stella) oggetto).getSistemaStellareDiAppartenenza().getIdOggetto());
			} else {
				stmt.setNull(6, java.sql.Types.INTEGER);
			}
			stmt.setNull(3, java.sql.Types.INTEGER);
			stmt.setNull(4, java.sql.Types.INTEGER);
			stmt.setNull(5, java.sql.Types.INTEGER);
		} else if(oggetto instanceof SistemaPlanetario) {
			stmt.setString(1, TipoOggetto.SistemaPlanetario.toString());
			if(((SistemaPlanetario) oggetto).getGalassiaDiAppartenenza() != null) {
				stmt.setInt(3, ((SistemaPlanetario) oggetto).getGalassiaDiAppartenenza().getIdOggetto());
			} else {
				stmt.setNull(3, java.sql.Types.INTEGER);
			}
			if(((SistemaPlanetario) oggetto).getCentroStellare() != null) {
				stmt.setInt(5, ((SistemaPlanetario) oggetto).getCentroStellare().getIdOggetto());
			} else {
				stmt.setNull(5, java.sql.Types.INTEGER);
			}
			stmt.setNull(4, java.sql.Types.INTEGER);
			stmt.setNull(6, java.sql.Types.INTEGER);
		} else if(oggetto instanceof SistemaStellare) {
			stmt.setString(1, TipoOggetto.SistemaStellare.toString());
			if(((SistemaStellare) oggetto).getGalassiaDiAppartenenza() != null) {
				stmt.setInt(3, ((SistemaStellare) oggetto).getGalassiaDiAppartenenza().getIdOggetto());
			} else {
				stmt.setNull(3, java.sql.Types.INTEGER);
			}
			stmt.setNull(4, java.sql.Types.INTEGER);
			stmt.setNull(5, java.sql.Types.INTEGER);
			stmt.setNull(6, java.sql.Types.INTEGER);
		} else if(oggetto instanceof Galassia) {
			stmt.setString(1, TipoOggetto.Galassia.toString());
			stmt.setNull(3, java.sql.Types.INTEGER);
			stmt.setNull(4, java.sql.Types.INTEGER);
			stmt.setNull(5, java.sql.Types.INTEGER);
			stmt.setNull(6, java.sql.Types.INTEGER);
		} else {
			throw new DAOException("Not a valid type of 'OggettoCeleste'");
		}
		return stmt;
	}
	
}
