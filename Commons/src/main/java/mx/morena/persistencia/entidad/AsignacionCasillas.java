package mx.morena.persistencia.entidad;

public class AsignacionCasillas {

	private Long id;

	private Long federalId;

	private String nombreDistrito;

	private Long zonaCrg;

	private String idZonaCrg;

	private Long ruta;

	private Long idCasilla;

	private String tipoCasilla;

	private Long seccionId;

	private Long status;

	private String idRutaRg;

	private Long idCrg;

	private Long open;
	
	private Boolean seInstalo;
	
	private String llegoRc;
	
	private Boolean comenzoVotacion;
	
	private AfluenciasVoto afluenciasVoto;
	
	private ActasVotacion actasVotacion; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFederalId() {
		return federalId;
	}

	public void setFederalId(Long federalId) {
		this.federalId = federalId;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public Long getZonaCrg() {
		return zonaCrg;
	}

	public void setZonaCrg(Long zonaCrg) {
		this.zonaCrg = zonaCrg;
	}

	public String getIdZonaCrg() {
		return idZonaCrg;
	}

	public void setIdZonaCrg(String idZonaCrg) {
		this.idZonaCrg = idZonaCrg;
	}

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public Long getSeccionId() {
		return seccionId;
	}

	public void setSeccionId(Long seccionId) {
		this.seccionId = seccionId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getIdRutaRg() {
		return idRutaRg;
	}

	public void setIdRutaRg(String idRutaRg) {
		this.idRutaRg = idRutaRg;
	}

	public Long getIdCrg() {
		return idCrg;
	}

	public void setIdCrg(Long idCrg) {
		this.idCrg = idCrg;
	}

	public Long getOpen() {
		return open;
	}

	public void setOpen(Long open) {
		this.open = open;
	}

	public Boolean getSeInstalo() {
		return seInstalo;
	}

	public void setSeInstalo(Boolean seInstalo) {
		this.seInstalo = seInstalo;
	}

	public String getLlegoRc() {
		return llegoRc;
	}

	public void setLlegoRc(String llegoRc) {
		this.llegoRc = llegoRc;
	}

	public Boolean getComenzoVotacion() {
		return comenzoVotacion;
	}

	public void setComenzoVotacion(Boolean comenzoVotacion) {
		this.comenzoVotacion = comenzoVotacion;
	}

	public AfluenciasVoto getAfluenciasVoto() {
		return afluenciasVoto;
	}

	public void setAfluenciasVoto(AfluenciasVoto afluenciasVoto) {
		this.afluenciasVoto = afluenciasVoto;
	}

	public ActasVotacion getActasVotacion() {
		return actasVotacion;
	}

	public void setActasVotacion(ActasVotacion actasVotacion) {
		this.actasVotacion = actasVotacion;
	}
}
