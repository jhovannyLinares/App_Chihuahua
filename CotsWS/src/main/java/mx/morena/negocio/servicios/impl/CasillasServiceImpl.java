package mx.morena.negocio.servicios.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICasillasService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.EnvioActas;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class CasillasServiceImpl extends MasterService implements ICasillasService {

	@Autowired
	IEnvioActasRepository envioActasRepository;
	
	@Override
	public Long save(EnvioActasDTO actaDto, long perfil, long idUsuario) throws CotException {

		if (perfil != PERFIL_RG) {

			throw new CotException("No cuenta con los servicios suficientes", 401);

		} else {
			
			EnvioActas actas = new EnvioActas();
			
			actas.setTipo_votacion(actaDto.getTipoVotacion());
			actas.setRuta_acta(actaDto.getRutaActa());
			actas.setId_casilla(actaDto.getIdCasilla());
			actas.setRegistro_acta((new Timestamp(new Date().getTime())));
			
			if(actas.getTipo_votacion() != null && actas.getTipo_votacion() > 0
				&& actas.getRuta_acta() != null && actas.getRuta_acta() != " "
				&& actas.getId_casilla() > 0 && actas.getId_casilla() != null) {
			
				envioActasRepository.save(actas);
				
				envioActasRepository.getIdMax();
			}else {
				throw new CotException("Todos los campos son requeridos", 400);
			}
			
			
			
			
			

		}

		return null;
	}

}
