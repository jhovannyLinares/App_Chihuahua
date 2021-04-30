package mx.morena.negocio.servicios;

import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.exception.CotException;

public interface ICasillasService {

	public Long save( EnvioActasDTO acta , long perfil, long idUsuario) throws CotException;
	
}
