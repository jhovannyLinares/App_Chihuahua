package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.AsignacionCasillas;

public interface IAsignacionCasillasRepository {

	List<AsignacionCasillas> getRutasByIdCrg(long idCrg);

	List<AsignacionCasillas> getRutasByFederal(Long idFederal);

	Long countCasillasByIdCrgAndRuta(Long idCrg, Long casillaRuta, Long tipo, Long idFederal, Long municipio);

}
