package de.shop.artikelverwaltung.domain;

import java.net.URI;
import java.security.Timestamp;
import java.util.Date;


public class Artikel {
	
	private Long id;
	private String bezeichnung;
	private String kategorie;
	private String farbe;
	private double preis;
	private boolean verfügbar;
	private Date erstellt;
	private Timestamp aktualisiert;
	
	public Artikel(Long id, String bezeichnung, String kategorie, String farbe, double preis,
			boolean verfügbar, Date erstellt, Timestamp aktualisiert) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.kategorie = kategorie;
		this.farbe = farbe;
		this.preis = preis;
		this.verfügbar = verfügbar;
		this.erstellt = erstellt;
		this.aktualisiert = aktualisiert;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public String getKategorie() {
		return kategorie;
	}
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	public String getFarbe() {
		return farbe;
	}
	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public boolean isVerfügbar() {
		return verfügbar;
	}
	public void setVerfügbar(boolean verfügbar) {
		this.verfügbar = verfügbar;
	}
	public Date getErstellt() {
		return erstellt;
	}
	public void setErstellt(Date erstellt) {
		this.erstellt = erstellt;
	}
	public Timestamp getAktualisiert() {
		return aktualisiert;
	}
	public void setAktualisiert(Timestamp aktualisiert) {
		this.aktualisiert = aktualisiert;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result
				+ ((erstellt == null) ? 0 : erstellt.hashCode());
		result = prime * result + ((farbe == null) ? 0 : farbe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kategorie == null) ? 0 : kategorie.hashCode());
		long temp;
		temp = Double.doubleToLongBits(preis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (verfügbar ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikel other = (Artikel) obj;
		if (aktualisiert == null) {
			if (other.aktualisiert != null)
				return false;
		} else if (!aktualisiert.equals(other.aktualisiert))
			return false;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (erstellt == null) {
			if (other.erstellt != null)
				return false;
		} else if (!erstellt.equals(other.erstellt))
			return false;
		if (farbe == null) {
			if (other.farbe != null)
				return false;
		} else if (!farbe.equals(other.farbe))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		} else if (!kategorie.equals(other.kategorie))
			return false;
		if (Double.doubleToLongBits(preis) != Double
				.doubleToLongBits(other.preis))
			return false;
		if (verfügbar != other.verfügbar)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", kategorie=" + kategorie + ", farbe=" + farbe + ", preis="
				+ preis + ", verfügbar=" + verfügbar + ", erstellt=" + erstellt
				+ ", aktualisiert=" + aktualisiert + "]";
	}

	public void setArtikelUri(URI artikelUri) {
		// TODO Auto-generated method stub
	}
}
