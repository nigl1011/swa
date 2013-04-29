package de.shop.lieferverwaltung.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.net.URI;
import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Temporal;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.util.IdGroup;

public class Lieferung implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final long MIN_ID = 1;
	
	@Min(value = MIN_ID, message = "{lieferverwaltung.lieferung.id.min}", groups = IdGroup.class)
	private Long id;
	
	@Past(message = "{lieferverwaltung.lieferung.lieferdatum.past}")
	private Date lieferdatum;
	
	@Temporal(TIMESTAMP)
	private Timestamp aktuell;

	@JsonIgnore
	private Bestellung bestellung;
	private URI bestellungUri;

	/*
	 * public Lieferung(Long id, Date lieferdatum, Timestamp aktuell) { super();
	 * this.id = id; this.lieferdatum = lieferdatum; this.aktuell = aktuell; }
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLieferdatum() {
		return lieferdatum;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}

	public Timestamp getAktuell() {
		return aktuell;
	}

	public void setAktuell(Timestamp aktuell) {
		this.aktuell = aktuell;
	}

	public Bestellung getBestellung() {
		return bestellung;
	}

	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}

	public URI getBestellungUri() {
		return bestellungUri;
	}

	public void setBestellungUri(URI bestellungUri) {
		this.bestellungUri = bestellungUri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bestellung == null) ? 0 : bestellung.hashCode());
		result = prime * result + ((aktuell == null) ? 0 : aktuell.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lieferdatum == null) ? 0 : lieferdatum.hashCode());
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
		Lieferung other = (Lieferung) obj;
		if (bestellung == null) {
			if (other.bestellung != null)
				return false;
		} else if (!bestellung.equals(other.bestellung))
			return false;
		if (aktuell == null) {
			if (other.aktuell != null)
				return false;
		} else if (!aktuell.equals(other.aktuell))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lieferdatum == null) {
			if (other.lieferdatum != null)
				return false;
		} else if (!lieferdatum.equals(other.lieferdatum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lieferung [id=" + id + ", lieferdatum=" + lieferdatum
				+ ", bestellungUri=" + bestellungUri + "]";
	}

}