package mx.morena.negocio.servicios.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class InstalacionCasillaServiceImpl extends MasterService implements IInstalacionCasillaService {
	
	@Autowired
	private IInstalacionCasillasRepository  casillasRepository;

	@Override
	public Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario)
			throws CotException, IOException {
		
//		if(perfil == PERFIL_RG || perfil == PERFIL_RC ) {
			
			InstalacionCasilla ic = new InstalacionCasilla();
			
			ic.setIdCasilla(dto.getIdCasilla());
			ic.setHoraInstalacion(dto.getHoraInstalacion());
			ic.setLlegaronFuncionarios(dto.getLlegaronFuncionarios());
			ic.setFuncionariosFila(dto.getFuncionariosFila());
			ic.setPaqueteCompleto(dto.getPaqueteCompleto());
			ic.setLlegoRg(dto.getLlegoRg());
			ic.setDesayuno(dto.getDesayuno());
			ic.setInicioVotacion(dto.getInicioVotacion());
			
			
			
			casillasRepository.save(ic);
//		}else {
//			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion", 409);
//		}
		
		return casillasRepository.getMaxId();
	}

}
