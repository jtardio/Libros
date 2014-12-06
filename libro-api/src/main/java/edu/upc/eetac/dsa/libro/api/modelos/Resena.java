package edu.upc.eetac.dsa.libro.api.modelos;

public class Resena {
	
	private int idresena;
	private int idlibro;
	private String creador;
	private String datos;
	private String fecha;
	
	public int getIdresena() {
		return idresena;
	}
	public void setIdresena(int idresena) {
		this.idresena = idresena;
	}
	public int getIdlibro() {
		return idlibro;
	}
	public void setIdlibro(int idlibro) {
		this.idlibro = idlibro;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
