package mx.morena.negocio.servicios.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.ResultadoVotacionDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICasillasService;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.persistencia.entidad.EnvioActas;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class CasillasServiceImpl extends MasterService implements ICasillasService {

	@Autowired
	IEnvioActasRepository envioActasRepository;
	
	@Autowired
	IInstalacionCasillaService  instalacionCasillaService;

	@Override
	public Long save(EnvioActasDTO actaDto, long perfil, long idUsuario) throws CotException {

		if (perfil != PERFIL_RG) {

			throw new CotException("No cuenta con los servicios suficientes", 401);

		} else {

			EnvioActas actas = new EnvioActas();

			actas.setTipoVotacion(actaDto.getTipoVotacion());
			actas.setRutaActa(actaDto.getRutaActa());
			actas.setIdCasilla(actaDto.getIdCasilla());
			actas.setRegistroActa((new Timestamp(new Date().getTime())));

			if (actas.getTipoVotacion() != null && actas.getTipoVotacion() > 0) {
			} else {
				throw new CotException("El campo Tipo Votacion es Requerido", 400);
			}
			if (actas.getRutaActa() != null && actas.getRutaActa() != " ") {
			} else {
				throw new CotException("El campo Ruta Acta es Requerido", 400);
			}

			if (actas.getIdCasilla() > 0 && actas.getIdCasilla() != null) {
			} else {
				throw new CotException("El campo Id Casilla es Requerido", 400);
			}
			if (actas.getTipoVotacion() != null && actas.getTipoVotacion() > 0 && actas.getRutaActa() != null
					&& actas.getRutaActa() != " " && actas.getIdCasilla() > 0 && actas.getIdCasilla() != null) {
				envioActasRepository.save(actas);

				return envioActasRepository.getIdMax();

			}
		}
//
		return null;
	}

	@Override
	public ResultadoOkDTO saveResultados(ResultadoVotacionDTO actas, long perfil, long usuario) throws CotException {
		
		// TODO Auto-generated method stub
		return new ResultadoOkDTO(1,"OK");
		
	}

	@Override
	public List<VotacionesDTO> getActas(Long idCasilla) throws CotException {

		List<EnvioActas> actas = envioActasRepository.getCasilla(idCasilla);

		List<VotacionesDTO> votacionesDTOs = instalacionCasillaService.getVotaciones(idCasilla);

		for (VotacionesDTO votacionesDTO : votacionesDTOs) {
			for (EnvioActas acta : actas) {

				if (votacionesDTO.getId() == acta.getTipoVotacion()) {
					
					votacionesDTO.setCapturada(true);
				}

			}

		}

		return votacionesDTOs;
	}

}
