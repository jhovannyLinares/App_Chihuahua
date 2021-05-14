package mx.morena.persistencia.entidad;

import java.sql.Timestamp;

public class Preguntas {

	private Long idCasilla;
	private Integer tipoVotacion;

	private Boolean seInstaloCasilla;
	private Timestamp horaInstalacionCasillas;
	private Boolean instaloLugarDistinto;
	private String causaInstalacionDistinto;
	private Integer boletasRecibidas;
	private Integer boletasSobrantes;
	private Integer folioInicial;
	private Integer folioFinal;
	private Boolean sellaronBoletas;
	private Long partidoSellaBoletas;
	private Timestamp horaInicioVotacion;
	private Boolean tomaronFuncionariosFila;
	private String nombreCompletoPresidente;
	private String nombreCompletoSecretario;
	private String nombreCompletoEscrutador1;
	private String nombreCompletoEscrutador2;
	private String nombreCompletoEscrutador3;
	private Boolean sePresentoIncidente;
	private Long incidente;
	private Integer numeroPersonasVotaron;
	private Integer numeroRepresentantesVotaron;
	private Integer sumaVotantes;
	private Integer votosSacadosUrna;
	private Integer votosXPartidoYCoalicion;
	private Boolean esIgualVotosPersonaXVotosUrna;
	private Boolean esIgualVotosUrnaXTotalVotacion;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Integer getTipoVotacion() {
		return tipoVotacion;
	}

	public void setTipoVotacion(Integer tipoVotacion) {
		this.tipoVotacion = tipoVotacion;
	}

	public Boolean getSeInstaloCasilla() {
		return seInstaloCasilla;
	}

	public void setSeInstaloCasilla(Boolean seInstaloCasilla) {
		this.seInstaloCasilla = seInstaloCasilla;
	}

	public Timestamp getHoraInstalacionCasillas() {
		return horaInstalacionCasillas;
	}

	public void setHoraInstalacionCasillas(Timestamp horaInstalacionCasillas) {
		this.horaInstalacionCasillas = horaInstalacionCasillas;
	}

	public Boolean getInstaloLugarDistinto() {
		return instaloLugarDistinto;
	}

	public void setInstaloLugarDistinto(Boolean instaloLugarDistinto) {
		this.instaloLugarDistinto = instaloLugarDistinto;
	}

	public String getCausaInstalacionDistinto() {
		return causaInstalacionDistinto;
	}

	public void setCausaInstalacionDistinto(String causaInstalacionDistinto) {
		this.causaInstalacionDistinto = causaInstalacionDistinto;
	}

	public Integer getBoletasRecibidas() {
		return boletasRecibidas;
	}

	public void setBoletasRecibidas(Integer boletasRecibidas) {
		this.boletasRecibidas = boletasRecibidas;
	}

	public Integer getBoletasSobrantes() {
		return boletasSobrantes;
	}

	public void setBoletasSobrantes(Integer boletasSobrantes) {
		this.boletasSobrantes = boletasSobrantes;
	}

	public Integer getFolioInicial() {
		return folioInicial;
	}

	public void setFolioInicial(Integer folioInicial) {
		this.folioInicial = folioInicial;
	}

	public Integer getFolioFinal() {
		return folioFinal;
	}

	public void setFolioFinal(Integer folioFinal) {
		this.folioFinal = folioFinal;
	}

	public Boolean getSellaronBoletas() {
		return sellaronBoletas;
	}

	public void setSellaronBoletas(Boolean sellaronBoletas) {
		this.sellaronBoletas = sellaronBoletas;
	}

	public Long getPartidoSellaBoletas() {
		return partidoSellaBoletas;
	}

	public void setPartidoSellaBoletas(Long partidoSellaBoletas) {
		this.partidoSellaBoletas = partidoSellaBoletas;
	}

	public Timestamp getHoraInicioVotacion() {
		return horaInicioVotacion;
	}

	public void setHoraInicioVotacion(Timestamp horaInicioVotacion) {
		this.horaInicioVotacion = horaInicioVotacion;
	}

	public Boolean getTomaronFuncionariosFila() {
		return tomaronFuncionariosFila;
	}

	public void setTomaronFuncionariosFila(Boolean tomaronFuncionariosFila) {
		this.tomaronFuncionariosFila = tomaronFuncionariosFila;
	}

	public String getNombreCompletoPresidente() {
		return nombreCompletoPresidente;
	}

	public void setNombreCompletoPresidente(String nombreCompletoPresidente) {
		this.nombreCompletoPresidente = nombreCompletoPresidente;
	}

	public String getNombreCompletoSecretario() {
		return nombreCompletoSecretario;
	}

	public void setNombreCompletoSecretario(String nombreCompletoSecretario) {
		this.nombreCompletoSecretario = nombreCompletoSecretario;
	}

	public String getNombreCompletoEscrutador1() {
		return nombreCompletoEscrutador1;
	}

	public void setNombreCompletoEscrutador1(String nombreCompletoEscrutador1) {
		this.nombreCompletoEscrutador1 = nombreCompletoEscrutador1;
	}

	public String getNombreCompletoEscrutador2() {
		return nombreCompletoEscrutador2;
	}

	public void setNombreCompletoEscrutador2(String nombreCompletoEscrutador2) {
		this.nombreCompletoEscrutador2 = nombreCompletoEscrutador2;
	}

	public String getNombreCompletoEscrutador3() {
		return nombreCompletoEscrutador3;
	}

	public void setNombreCompletoEscrutador3(String nombreCompletoEscrutador3) {
		this.nombreCompletoEscrutador3 = nombreCompletoEscrutador3;
	}

	public Boolean getSePresentoIncidente() {
		return sePresentoIncidente;
	}

	public void setSePresentoIncidente(Boolean sePresentoIncidente) {
		this.sePresentoIncidente = sePresentoIncidente;
	}

	public Long getIncidente() {
		return incidente;
	}

	public void setIncidente(Long incidente) {
		this.incidente = incidente;
	}

	public Integer getNumeroPersonasVotaron() {
		return numeroPersonasVotaron;
	}

	public void setNumeroPersonasVotaron(Integer numeroPersonasVotaron) {
		this.numeroPersonasVotaron = numeroPersonasVotaron;
	}

	public Integer getNumeroRepresentantesVotaron() {
		return numeroRepresentantesVotaron;
	}

	public void setNumeroRepresentantesVotaron(Integer numeroRepresentantesVotaron) {
		this.numeroRepresentantesVotaron = numeroRepresentantesVotaron;
	}

	public Integer getSumaVotantes() {
		return sumaVotantes;
	}

	public void setSumaVotantes(Integer sumaVotantes) {
		this.sumaVotantes = sumaVotantes;
	}

	public Integer getVotosSacadosUrna() {
		return votosSacadosUrna;
	}

	public void setVotosSacadosUrna(Integer votosSacadosUrna) {
		this.votosSacadosUrna = votosSacadosUrna;
	}

	public Integer getVotosXPartidoYCoalicion() {
		return votosXPartidoYCoalicion;
	}

	public void setVotosXPartidoYCoalicion(Integer votosXPartidoYCoalicion) {
		this.votosXPartidoYCoalicion = votosXPartidoYCoalicion;
	}

	public Boolean getEsIgualVotosPersonaXVotosUrna() {
		return esIgualVotosPersonaXVotosUrna;
	}

	public void setEsIgualVotosPersonaXVotosUrna(Boolean esIgualVotosPersonaXVotosUrna) {
		this.esIgualVotosPersonaXVotosUrna = esIgualVotosPersonaXVotosUrna;
	}

	public Boolean getEsIgualVotosUrnaXTotalVotacion() {
		return esIgualVotosUrnaXTotalVotacion;
	}

	public void setEsIgualVotosUrnaXTotalVotacion(Boolean esIgualVotosUrnaXTotalVotacion) {
		this.esIgualVotosUrnaXTotalVotacion = esIgualVotosUrnaXTotalVotacion;
	}

}
