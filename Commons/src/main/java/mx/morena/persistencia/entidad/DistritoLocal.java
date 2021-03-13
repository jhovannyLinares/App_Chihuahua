//package mx.morena.persistencia.entidad;
//
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
////@Entity
////@Table(name = "app_distritoLocal")
//public class DistritoLocal {
//
//	public DistritoLocal() {
//	}
//
//	//@Id
//	//@Column(unique = true, name = "id")
//	private String id;
//
//	//@Column(name = "cabecera")
//	private String cabecera;
//
//	//@ManyToOne
//	@JoinColumn(name = "id_distrito_federal")
//	private DistritoFederal distritoFederal;
//
//	//@OneToMany(mappedBy = "distritoLocal",fetch = FetchType.LAZY)
//	private List<Municipio> municipios;
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getCabecera() {
//		return cabecera;
//	}
//
//	public void setCabecera(String cabecera) {
//		this.cabecera = cabecera;
//	}
//
//	public DistritoFederal getDistritoFederal() {
//		return distritoFederal;
//	}
//
//	public void setDistritoFederal(DistritoFederal distritoFederal) {
//		this.distritoFederal = distritoFederal;
//	}
//
//	public List<Municipio> getMunicipios() {
//		return municipios;
//	}
//
//	public void setMunicipios(List<Municipio> municipios) {
//		this.municipios = municipios;
//	}
//
//}
