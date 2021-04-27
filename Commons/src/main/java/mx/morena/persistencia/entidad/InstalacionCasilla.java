package mx.morena.persistencia.entidad;

import java.sql.Timestamp;

public class InstalacionCasilla {

	private Long id;
	
	private Long idCasilla;
	
	private Timestamp horaInstalacion;
	
	private String llegaronFuncionarios;
	
	private String funcionariosFila;
	
	private String paqueteCompleto;
	
	private String llegoRg;
	
	private String desayuno;
	
	private Timestamp inicioVotacion;
	
	private String llegoRc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Timestamp getHoraInstalacion() {
		return horaInstalacion;
	}

	public void setHoraInstalacion(Timestamp horaInstalacion) {
		this.horaInstalacion = horaInstalacion;
	}

	public String getLlegaronFuncionarios() {
		return llegaronFuncionarios;
	}

	public void setLlegaronFuncionarios(String llegaronFuncionarios) {
		this.llegaronFuncionarios = llegaronFuncionarios;
	}

	public String getFuncionariosFila() {
		return funcionariosFila;
	}

	public void setFuncionariosFila(String funcionariosFila) {
		this.funcionariosFila = funcionariosFila;
	}

	public String getPaqueteCompleto() {
		return paqueteCompleto;
	}

	public void setPaqueteCompleto(String paqueteCompleto) {
		this.paqueteCompleto = paqueteCompleto;
	}

	public String getLlegoRg() {
		return llegoRg;
	}

	public void setLlegoRg(String llegoRg) {
		this.llegoRg = llegoRg;
	}

	public String getDesayuno() {
		return desayuno;
	}

	public void setDesayuno(String desayuno) {
		this.desayuno = desayuno;
	}

	public Timestamp getInicioVotacion() {
		return inicioVotacion;
	}

	public void setInicioVotacion(Timestamp inicioVotacion) {
		this.inicioVotacion = inicioVotacion;
	}

	public String getLlegoRc() {
		return llegoRc;
	}

	public void setLlegoRc(String llegoRc) {
		this.llegoRc = llegoRc;
	}
	
}
