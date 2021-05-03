package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.repository.IRepresentantesAsignadosRepository;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.RepresentantesAsignadosRowMapper;

@Repository
public class RepresentantesAsignadosRepository implements IRepresentantesAsignadosRepository {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public void save(RepresentantesAsignados representantes) {
		String sql = " INSERT INTO app_asignacion_representantes (id, " + "representante_id, " + "usuario_id, " + "cargo, "
				+ "distrito_federal_id, " + "distrito_local_id, " + "municipio_id, " + "ruta_id, " + "seccion_electoral_id, "
				+ "casilla_id, " + "entidad_id) "
				+ "VALUES ((SELECT MAX(id)+1 FROM app_asignacion_representantes), ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		template.update(sql,
				new Object[] { representantes.getRepresentanteId(), representantes.getUsuarioId(), representantes.getCargo(),
						representantes.getDistritoFederalId(), representantes.getDistritoLocalId(), representantes.getMunicipioId(),
						representantes.getRutaId(), representantes.getSeccionElectoralId(), representantes.getCasillaId(),
						representantes.getEntidadId() });
	}

	@Override
	public List<RepresentantesAsignados> getByEntidadAndIdRepresentante(Long entidad, Long id) {
		String sql = " SELECT id, representante_id, usuario_id, cargo, distrito_federal_id, distrito_local_id, municipio_id, "
				+ "ruta_id, seccion_electoral_id, casilla_id, entidad_id, zona_id FROM app_representantes_asignados "
				+ "where entidad_id = ? and representante_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { entidad, id }, new int[] { Types.NUMERIC, Types.NUMERIC },
					new RepresentantesAsignadosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getRutaIdByRepresentante(Long usuario) {
		String sql = "select ruta_id from app_representantes_asignados ara where representante_id = ?";

		try {
			return template.queryForObject(sql, new Object[] { usuario }, new int[] { Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
