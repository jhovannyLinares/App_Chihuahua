package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Cargo;

public interface ICargosRepository {

	List<Cargo> getCargos(Long tipoRepresentante);

//	List<Zona> getZonas(Long idFederal);

}
