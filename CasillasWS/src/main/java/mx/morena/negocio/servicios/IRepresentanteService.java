package mx.morena.negocio.servicios;

import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IRepresentanteService {
	//public String save(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	public Long saveRepFederal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public Long saveRepLocal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public Long saveRepMunicipal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public Long saveRepCRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public Long saveRepRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public Long saveRepRC(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
}
