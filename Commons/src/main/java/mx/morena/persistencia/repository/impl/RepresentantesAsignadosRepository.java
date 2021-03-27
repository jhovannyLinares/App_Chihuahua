package mx.morena.persistencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.repository.IRepresentantesAsignadosRepository;

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
}
