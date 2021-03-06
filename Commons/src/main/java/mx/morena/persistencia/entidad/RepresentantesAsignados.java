package mx.morena.persistencia.entidad;

public class RepresentantesAsignados {
	
	private Long id;
	private Long representanteId;
	private Long usuarioId;
	private int cargo;
	private Long distritoFederalId;
	private Long distritoLocalId;
	private Long municipioId;
	private Long rutaId;
	private Long seccionElectoralId;
	private Long casillaId;
	private Long entidadId;

	private Long ZonaId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRepresentanteId() {
		return representanteId;
	}

	public void setRepresentanteId(Long representanteId) {
		this.representanteId = representanteId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public Long getDistritoFederalId() {
		return distritoFederalId;
	}

	public void setDistritoFederalId(Long distritoFederalId) {
		this.distritoFederalId = distritoFederalId;
	}

	public Long getDistritoLocalId() {
		return distritoLocalId;
	}

	public void setDistritoLocalId(Long distritoLocalId) {
		this.distritoLocalId = distritoLocalId;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public Long getRutaId() {
		return rutaId;
	}

	public void setRutaId(Long rutaId) {
		this.rutaId = rutaId;
	}

	public Long getSeccionElectoralId() {
		return seccionElectoralId;
	}

	public void setSeccionElectoralId(Long seccionElectoralId) {
		this.seccionElectoralId = seccionElectoralId;
	}

	public Long getCasillaId() {
		return casillaId;
	}

	public void setCasillaId(Long casillaId) {
		this.casillaId = casillaId;
	}

	public Long getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Long entidadId) {
		this.entidadId = entidadId;
	}

	public Long getZonaId() {
		return ZonaId;
	}

	public void setZonaId(Long zonaId) {
		ZonaId = zonaId;
	}
}
