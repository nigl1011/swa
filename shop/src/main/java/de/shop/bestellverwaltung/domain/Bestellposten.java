package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.artikelverwaltung.domain.Artikel;
import static de.shop.util.Constants.KEINE_ID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;



public class Bestellposten implements Serializable {
	
	/**
	 * 
	 */
	//Gelb unterstrichen, da anscheinend kein "private static final long?"
	private static final long serialVersionUID = -1427065293027144676L;
	private static final long MIN_MENGE = 1;
	private static final int ANZAHL_MIN = 1;
	//private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@Column(name = "menge",nullable = false)
	@Min(value = MIN_MENGE , message = "{bestellverwaltung.bestellung.menge.Min}")
	private Long menge;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "artikel_fk", nullable = false)
	@NotNull(message = "{bestellverwaltung.bestellposition.artikel.notNull}")
	@JsonIgnore
	private Artikel artikel;
	
	@Column(nullable = false,updatable = true)
	private Long version;
	
	@Column(nullable = false, precision = 5, scale = 4)
	@NotNull(message = "{bestellverwaltung.bestellung.zwischenpreis.notEmpty}")
	private BigDecimal zwischenpreis;
	
	@JsonIgnore
	private Bestellung bestellung;
	
	@Column(name = "anzahl", nullable = false)
	@Min(value = ANZAHL_MIN, message = "{bestellverwaltung.bestellposition.anzahl.min}")
	private short anzahl;
	
	@Transient
	private URI bestellungUri;
	@Transient
	private URI artikelUri;
	
	public Bestellposten() {
		super();
	}
	
	public Bestellposten(Artikel artikel) {
		super();
		this.artikel = artikel;
		this.anzahl = 1;
	}
	
	public Bestellposten(Artikel artikel, short anzahl) {
		super();
		this.artikel = artikel;
		this.anzahl = anzahl;
	}
	
	
	public Long getPositionId() {
		return id;
	}
	public void setPositionId(Long positionId) {
		id = positionId;
	}
	public Long getMenge() {
		return menge;
	}
	public void setMenge(Long menge) {
		this.menge = menge;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public BigDecimal getZwischenpreis() {
		return zwischenpreis;
	}
	public void setZwischenpreis(BigDecimal zwischenpreis) {
		this.zwischenpreis = zwischenpreis;
	}
	public Bestellung getBestellung() {
		return bestellung;
	}
	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}

	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public URI getBestellungUri() {
		return bestellungUri;
	}
	public void setBestellungUri(URI bestellungUri) {
		this.bestellungUri = bestellungUri;
	}
	public URI getArtikelUri() {
		return artikelUri;
	}
	public void setArtikelUri(URI artikelUri) {
		this.artikelUri = artikelUri;
	}	

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menge == null) ? 0 : menge.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result
				+ ((zwischenpreis == null) ? 0 : zwischenpreis.hashCode());
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
		final Bestellposten other = (Bestellposten) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		}
		else if (!artikel.equals(other.artikel))
			return false;
		if (artikelUri == null) {
			if (other.artikelUri != null)
				return false;
		}
		else if (!artikelUri.equals(other.artikelUri))
			return false;
		if (bestellungUri == null) {
			if (other.bestellungUri != null)
				return false;
		} 
		else if (!bestellungUri.equals(other.bestellungUri))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		if (menge == null) {
			if (other.menge != null)
				return false;
		} 
		else if (!menge.equals(other.menge))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} 
		else if (!version.equals(other.version))
			return false;
		if (zwischenpreis == null) {
			if (other.zwischenpreis != null)
				return false;
		} 
		else if (!zwischenpreis.equals(other.zwischenpreis))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Bestellposten [id=" + id + ", menge=" + menge + ", version="
				+ version + ", zwischenpreis=" + zwischenpreis
				+ ", bestellung=" + bestellung + ", artikel=" + artikel
				+  "]";
	}

}

