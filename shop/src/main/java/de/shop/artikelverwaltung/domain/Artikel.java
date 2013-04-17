package de.shop.artikelverwaltung.domain;

import java.net.URI;

//import static javax.persistence.TemporalType.TIMESTAMP;

import java.security.Timestamp;


public class Artikel {
	
	private Long id;
	private String bezeichnung;
	private String kategorie;
	private String farbe;
	private Double preis;
	private Boolean verfuegbar;
	private Timestamp erstellt;
	private Timestamp aktualisiert;
	
	
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
	public Double getPreis() {
		return preis;
	}
	public void setPreis(Double preis) {
		this.preis = preis;
	}
	public Boolean isVerfuegbar() {
		return verfuegbar;
	}
	public void setVerfuegbar(Boolean verfuegbar) {
		this.verfuegbar = verfuegbar;
	}
	public Timestamp getErstellt() {
		return erstellt;
	}
	public void setErstellt(Timestamp erstellt) {
		this.erstellt = erstellt;
	}
	public Timestamp getAktualisiert() {
		return aktualisiert;
	}
	public void setAktualisiert(Timestamp aktualisiert) {
		this.aktualisiert = aktualisiert;
	}



	@Override
	public String toString() {
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", kategorie=" + kategorie + ", farbe=" + farbe + ", preis="
				+ preis + ", verfügbar=" + verfuegbar + ", erstellt=" + erstellt
				+ ", aktualisiert=" + aktualisiert + "]";
	}

	public void setArtikelUri(URI artikelUri) {
		// TODO Auto-generated method stub
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
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
		result = prime * result
				+ ((verfuegbar == null) ? 0 : verfuegbar.hashCode());
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
		final Artikel other = (Artikel) obj;
		if (aktualisiert == null) {
			if (other.aktualisiert != null)
				return false;
		}
		else if (!aktualisiert.equals(other.aktualisiert))
			return false;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		}
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (erstellt == null) {
			if (other.erstellt != null)
				return false;
		}
		else if (!erstellt.equals(other.erstellt))
			return false;
		if (farbe == null) {
			if (other.farbe != null)
				return false;
		}
		else if (!farbe.equals(other.farbe))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		}
		else if (!kategorie.equals(other.kategorie))
			return false;
		if (preis == null) {
			if (other.preis != null)
				return false;
		}
		else if (!preis.equals(other.preis))
			return false;
		if (verfuegbar == null) {
			if (other.verfuegbar != null)
				return false;
		}
		else if (!verfuegbar.equals(other.verfuegbar))
			return false;
		return true;
	}
}
