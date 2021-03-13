package mx.morena.negocio.servicios;

import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IRepresentanteService {
	//public String save(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	public String saveRepFederal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public String saveRepLocal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public String saveRepMunicipal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public String saveRepCRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public String saveRepRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
	public String saveRepRC(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException;
	
}
