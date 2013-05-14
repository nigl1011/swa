package de.shop.artikelverwaltung.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

//import static javax.persistence.TemporalType.TIMESTAMP;

import java.security.Timestamp;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;








import de.shop.util.IdGroup;


public class Artikel implements Serializable {
	private static final long serialVersionUID = 1472129607838538329L;
	
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+";
	private static final String FARBEN_PATTERN = "[a-z\u00E4\u00F6\u00FC\u00DF]+[/][a-z\u00E4\u00F6\u00FC\u00DF]+";
	
	
	private static final long MIN_ID = 1;
	private static final int BEZEICHNUNG_LENGTH_MIN = 3;
	private static final int BEZEICHNUNG_LENGTH_MAX = 32;
	private static final String BEZEICHNUNG_PATTERN = NAME_PATTERN;
	private static final int FARBE_LENGTH_MIN = 3;
	private static final int FARBE_LENGTH_MAX = 12;
	private static final String FARBE_PATTERN = FARBEN_PATTERN;
	
	

	@Min(value = MIN_ID, message = "{artikelverwaltung.artikel.id.min}", groups = IdGroup.class)
	private Long id;
	

	@NotNull(message = "{artikelverwaltung.artikel.bezeichnung.notNull}")
	@Size(min = BEZEICHNUNG_LENGTH_MIN, max = BEZEICHNUNG_LENGTH_MAX,
	      message = "{artikelverwaltung.artikel.bezeichnung.length}")
	@Pattern(regexp = BEZEICHNUNG_PATTERN, message = "{artikelverwaltung.artikel.bezeichnung.pattern}")
	private String bezeichnung;
	
	@Enumerated
	private KategorieType kategorie;
	
	@NotNull(message = "{artikelverwaltung.artikel.farbe.notNull}")
	@Size(min = FARBE_LENGTH_MIN, max = FARBE_LENGTH_MAX,
	      message = "{artikelverwaltung.artikel.farbe.length}")
	@Pattern(regexp = FARBE_PATTERN, message = "{artikelverwaltung.artikel.farbe.pattern}")
	private String farbe;
	
	@Digits(fraction=2, integer = 12)
	private BigDecimal preis;
	
	@AssertTrue 
	private boolean verfuegbar;
	
	@Temporal(TIMESTAMP)
	private Timestamp erstellt;
	
	@Temporal(TIMESTAMP)
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
	public KategorieType getKategorie() {
		return kategorie;
	}
	public void setKategorie(KategorieType kategorie) {
		this.kategorie = kategorie;
	}
	public String getFarbe() {
		return farbe;
	}
	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}
	public BigDecimal getPreis() {
		return preis;
	}
	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	public boolean isVerfuegbar() {
		return verfuegbar;
	}
	public void setVerfuegbar(boolean verfuegbar) {
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
				+ ", aktualisiert=" + aktualisiert +"]";
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
		result = prime * result + (verfuegbar ? 1231 : 1237);
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
		if (kategorie != other.kategorie)
			return false;
		if (preis == null) {
			if (other.preis != null)
				return false;
		} else if (!preis.equals(other.preis))
			return false;
		if (verfuegbar != other.verfuegbar)
			return false;
		return true;
	}
	
	

}
