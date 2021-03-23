package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.repository.IRutasRepository;
import mx.morena.persistencia.rowmapper.RutasRowMapper;

@Repository
public class RutasRepository implements IRutasRepository{
	
	@Autowired
	private JdbcTemplate templete;
	
	private String campos =" id, distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg, ruta, id_casilla, tipo_casilla, seccion_id, id_ruta_rg, status, id_crg ";

	@Override
	public List<Representantes> getAllCrg(int tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rutas> findByCrgId(Long crgId, Long tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rutas getByIdAndEstatus(Long idCrg, char estatus, Long tipo) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public List<Rutas> findById(Long idRutas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateIdCrg(Long idRuta, Long idCrg) {
		// TODO Auto-generated method stub
		
	}

	
	///////////////////////////////////// consulta de rutas
	
	@Override
	public List<Rutas> getByFederal(Long idFederal) {
		String sql = "SELECT " + campos 
				+ " FROM app_rutas2"
				+ " where distrito_federal_id = ? ";
		try {
			return templete.queryForObject(sql, new Object[] {idFederal}, new int[] {Types.NUMERIC}, new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public List<Rutas> getByFedAndZonaCrg(Long idFederal, Long zonaCRG) {
		String sql = "SELECT " + campos 
				+ " FROM app_rutas2"
				+ " where distrito_federal_id = ? and zona_crg = ? ";
		try {
			return templete.queryForObject(sql, new Object[] {idFederal, zonaCRG}, new int[] {Types.NUMERIC, Types.NUMERIC}, new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getByFedAndZonaCrgAndRuta(Long idFederal, Long zonaCRG, Long ruta) {
		String sql = "SELECT " + campos 
				+ " FROM app_rutas2"
				+ " where distrito_federal_id = ? and zona_crg = ? and ruta = ?";
		try {
			return templete.queryForObject(sql, new Object[] {idFederal, zonaCRG, ruta}, new int[] {Types.NUMERIC, Types.NUMERIC, Types.NUMERIC}, new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getByFedAndZonaCrgAndRutaAndCasilla(Long idFederal, Long zonaCRG, Long ruta, Long casilla) {
		String sql = "SELECT " + campos 
				+ " FROM app_rutas2"
				+ " where distrito_federal_id = ? and zona_crg = ? and ruta = ? and id_casilla = ?";
		try {
			return templete.queryForObject(sql, new Object[] {idFederal, zonaCRG, ruta, casilla}, new int[] {Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC}, new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
