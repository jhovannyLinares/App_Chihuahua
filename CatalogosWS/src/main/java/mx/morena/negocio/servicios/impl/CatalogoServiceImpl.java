package mx.morena.negocio.servicios.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.DistritoLocal;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IDistritoLocalRepository;
import mx.morena.persistencia.repository.ILocalidadRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;

@Service
public class CatalogoServiceImpl implements ICatalogoService{
	
	@Autowired
	private IDistritoFederalRepository federalRepocitory;
	
	@Autowired
	private IDistritoLocalRepository localRepository;
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	@Autowired
	private ILocalidadRepository localidadRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	@Override
	public List<DistritoFederal> getFederalByEntidad(HttpServletResponse response, Long id) { //

		List<DistritoFederal> federal = federalRepocitory.findAll();
		
//		List<DistritoFederal> federal = federalRepocitory.findByEntidad(id);
		
		return federal;
	}

	@Override
	public List<DistritoLocal> getLocalByFederal(HttpServletResponse response, Long id) {
//		List<DistritoLocal> local = federalRepocitory.findById(id).get().getDistritosLocales();
		
		List<DistritoLocal> local = localRepository.findAll();
		
//		List<DistritoLocal> local = localRepository.findByFederal(id);
		
		return local;
	}

	@Override
	public List<Municipio> getMunicipioByLocal(HttpServletResponse response, Long id) {
		
//		List<Municipio> muncipio = localRepository.findById(id).get().getMunicipios();
		
		List<Municipio> muncipio = municipioRepository.findAll();
		
//		List<Municipio> muncipio = municipioRepository.findByLocal(id);
		
		return muncipio;
	}

	@Override
	public List<Localidad> getLocalidadByMunicipio(HttpServletResponse response, Long id) {
		
//		List<Localidad> localidad = municipioRepository.findById(id).get().getLocalidades();
		
		List<Localidad> localidad = localidadRepository.findAll();
		
//		List<Localidad> localidad = localidadRepository.findByMunicipio(id);
		
		return localidad;
	}

	@Override
	public List<SeccionElectoral> getSeccionByLocalidad(HttpServletResponse response, Long id) {
		
//		List<SeccionElectoral> seccion = localidadRepository.findById(id).get().getSeccionElectorales();
		
		List<SeccionElectoral> seccion = seccionRepository.findAll();
		
//		List<SeccionElectoral> seccion = seccionRepository.findByLocalidad(id);
		
		return seccion;
	}

}

