package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Votacion;
import mx.morena.persistencia.repository.IVotosPartidoAmbitoRepository;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.VotacionRowMapper;

@Repository
public class VotosPartidoAmbitoRepository implements IVotosPartidoAmbitoRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public Long getVotosByDistritoAndMunicipioAndPartido(Long idEntidad, Long df, Long idMunicipio, Long idEleccion) {
		String sql = "select SUM(avpa.votos) from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? and ac.federal_id = ? and ac.municpio_id = ? "
				+ "and avpa.id_ambito = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, df, idMunicipio, idEleccion },
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}

	@Override
	public Long getVotosByEleccionAndPartido(Long idEntidad, Long idEleccion, Long idPartido) {
		String sql = "select avpa.votos from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? "
				+ "and avpa.id_ambito = ? and avpa.id_partido = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, idEleccion, idPartido },
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}

	@Override
	public List<Votacion> getByAmbitoAndPartido(Long idEstado, Long idFederal, Long idMunicipio, Long idEleccion, Long idPartido) {
		String sql = "select avpa.id_casilla , avpa.id_ambito, avpa.id_partido, avpa.votos, avpa.id_coalision, avpa.is_coalision "
				+ "from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? and ac.federal_id = ? and ac.municpio_id = ? "
				+ "and avpa.id_ambito = ? and avpa.id_partido = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEstado, idFederal, idMunicipio, idEleccion, idPartido }, new int[] 
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC }, new VotacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getVotosPartidoByDistrito(Long idMunicipio, Long idDistrito, Long idEleccion, Long idPartido) {
//		String sql ="select sum(votos) from app_votos_partido_ambito avpa " + 
//				"inner join app_casilla ac " + 
//				"on ac.id = avpa.id_casilla " + 
//				"where ac.municpio_id = ? and ac.federal_id = ? and avpa.id_ambito = ? and avpa.id_partido = ?";
		
		
		String sql ="select case "  
				+ "when sum(votos) is null then 0 " 
				+ "else sum(votos) " 
				+ "end "
				+ "from app_votos_partido_ambito avpa "  
				+ "inner join app_casilla ac " 
				+ "on ac.id = avpa.id_casilla "  
				+ "where ac.municpio_id = ? and ac.federal_id = ? and avpa.id_ambito = ? and avpa.id_partido = ?";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio, idDistrito, idEleccion, idPartido }, new int[] 
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC}, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getNulosByMunicipio(Long idMunicipio, Long idFederal, Long idEleccion, String partido) {
		
		String inner = null;
		
		if (idEleccion ==1) 
			inner =" inner join app_partidos_entidad ape ";
		
		if (idEleccion ==2) 
			inner =" inner join app_partidos_municipio ape ";
			
		if (idEleccion ==3) 
			inner =" inner join app_partidos_sindico ape ";
				
		if (idEleccion ==4) 
			inner =" inner join app_partidos_diputado_local ape ";
					
		if (idEleccion ==5) 
			inner =" inner join app_partidos_diputado_federal ape ";
		
			

		String sql = "select sum(votos) from app_votos_partido_ambito avpa "  
				+ "inner join app_casilla ac " 
				+ "on avpa.id_casilla = ac.id "  
				+ inner  
				+ "on avpa.id_partido = ape.id "  
				+ "where ac.municpio_id = ? and ac.federal_id = ? and avpa.id_ambito  = ? and ape.partido = ? ";

		try {
			return template.queryForObject(sql, new Object[] { idMunicipio, idFederal, idEleccion, partido }, new int[] 
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR}, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getCoalicionByIdCoalicion(Long idMunicipio, Long idDistrito, Long idEleccion, Long idCoalicion) {
		String sql = "select case "
				+ "when sum(votos) is null then 0 " 
				+ "else sum(votos) " 
				+ "end "
				+ "from app_votos_partido_ambito avpa "
				+ "inner join app_casilla ac on ac.id = avpa.id_casilla " 
				+ "where  ac.municpio_id = ? and ac.federal_id = ? and avpa.id_ambito = ? and avpa.id_coalision = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio, idDistrito, idEleccion, idCoalicion }, new int[] 
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC}, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
