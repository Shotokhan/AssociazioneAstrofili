package astrofili.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private SatelliteNaturaleDAO interfacciaSatelliti;
	
	public OggettoCeleste create(OggettoCeleste oggetto) throws SQLException {
		// TODO - InheritanceToTable
		return null;
	} 
	
	public OggettoCeleste read(int idOggetto) throws SQLException {
		conn = DBManager.getConnection();
		
		String query = "SELECT * FROM OGGETTOCELESTE WHERE IDOGGETTO = ?;";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setInt(1, idOggetto);
		
		ResultSet result = stmt.executeQuery();
		if(result.next()) {
			return this.TableToInheritance(result);
		} else {
			return null;
		}
	}
	
	public List<? extends OggettoCeleste> read(OggettoCeleste oggetto) throws SQLException {
		// TODO - confronta i campi
		return null;
	}
	
	public OggettoCeleste update(OggettoCeleste oggetto) throws SQLException {
		// TODO
		return null;
	}
	
	public Boolean delete(OggettoCeleste oggetto) throws SQLException {
		// TODO
		return null;
	}
	
	public OggettoCeleste TableToInheritance(ResultSet result) throws SQLException {
		TipoOggetto tipo = TipoOggetto.valueOf(result.getString("TIPOOGGETTOCELESTE"));
		switch(tipo) {
		
		case Pianeta:
			Pianeta pianeta = new Pianeta(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					(Galassia) this.read(result.getInt("GALASSIADIAPPARTENENZA")),
					(SistemaPlanetario) this.read(result.getInt("SISTEMAPLANETARIODIAPPARTENENZA")),
					(SistemaStellare) this.read(result.getInt("CENTROSTELLARE")),
					(SistemaStellare) this.read(result.getInt("SISTEMASTELLAREDIAPPARTENENZA")));
			
			pianeta.setListaSatelliti(interfacciaSatelliti.read(pianeta));
			return pianeta;
			
		case Stella:
			Stella stella = new Stella(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					(Galassia) this.read(result.getInt("GALASSIADIAPPARTENENZA")),
					(SistemaPlanetario) this.read(result.getInt("SISTEMAPLANETARIODIAPPARTENENZA")),
					(SistemaStellare) this.read(result.getInt("CENTROSTELLARE")),
					(SistemaStellare) this.read(result.getInt("SISTEMASTELLAREDIAPPARTENENZA")));
			
			return stella;
			
		case SistemaPlanetario:
			SistemaPlanetario sistemaPlanetario = new SistemaPlanetario(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					(Galassia) this.read(result.getInt("GALASSIADIAPPARTENENZA")),
					(SistemaPlanetario) this.read(result.getInt("SISTEMAPLANETARIODIAPPARTENENZA")),
					(SistemaStellare) this.read(result.getInt("CENTROSTELLARE")),
					(SistemaStellare) this.read(result.getInt("SISTEMASTELLAREDIAPPARTENENZA")));
			
			Pianeta queryPianeta = new Pianeta("");
			queryPianeta.setSistemaPlanetarioDiAppartenenza(sistemaPlanetario);
			sistemaPlanetario.setListaPianeti((List<Pianeta>) this.read(queryPianeta));
			return sistemaPlanetario;
			
		case SistemaStellare:
			SistemaStellare sistemaStellare = new SistemaStellare(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					(Galassia) this.read(result.getInt("GALASSIADIAPPARTENENZA")),
					(SistemaPlanetario) this.read(result.getInt("SISTEMAPLANETARIODIAPPARTENENZA")),
					(SistemaStellare) this.read(result.getInt("CENTROSTELLARE")),
					(SistemaStellare) this.read(result.getInt("SISTEMASTELLAREDIAPPARTENENZA")));
			
			Stella queryStella = new Stella("");
			queryStella.setSistemaStellareDiAppartenenza(sistemaStellare);
			sistemaStellare.setListaStelle((List<Stella>) this.read(queryStella));
			return sistemaStellare;
			
		case Galassia:
			Galassia galassia = new Galassia(
					result.getString("NOME"),
					result.getInt("IDOGGETTO"),
					(Galassia) this.read(result.getInt("GALASSIADIAPPARTENENZA")),
					(SistemaPlanetario) this.read(result.getInt("SISTEMAPLANETARIODIAPPARTENENZA")),
					(SistemaStellare) this.read(result.getInt("CENTROSTELLARE")),
					(SistemaStellare) this.read(result.getInt("SISTEMASTELLAREDIAPPARTENENZA")));
			
			Sistema querySistema = new Sistema("");
			querySistema.setGalassiaDiAppartenenza(galassia);
			galassia.setListaSistemi((List<Sistema>) this.read(querySistema));
			return galassia;
		
		default:
			return null;
		}
	}
	
}
