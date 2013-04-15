package de.shop.bestellverwaltung.domain;


import java.security.Timestamp;
import java.util.Date;

public class Lieferung {
	
	private Long id;
	private Date lieferdatum;
	private Timestamp aktuell;
	private Bestellung Bestellungs_ID;
	
	
	public Lieferung(Long id, Date lieferdatum, Timestamp aktuell) {
		super();
		this.id = id;
		this.lieferdatum = lieferdatum;
		this.aktuell = aktuell;
	}
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
	
	
	public Bestellung getBestellungs_ID() {
		return Bestellungs_ID;
	}
	public void setBestellungs_ID(Bestellung bestellungs_ID) {
		Bestellungs_ID = bestellungs_ID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Bestellungs_ID == null) ? 0 : Bestellungs_ID.hashCode());
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
		if (Bestellungs_ID == null) {
			if (other.Bestellungs_ID != null)
				return false;
		} else if (!Bestellungs_ID.equals(other.Bestellungs_ID))
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
		return "Lieferung [id=" + id + ", lieferdatum=" + lieferdatum + "]";
	}
	public Lieferung(Long id, Date lieferdatum, Timestamp aktuell,
			Bestellung bestellungs_ID) {
		super();
		this.id = id;
		this.lieferdatum = lieferdatum;
		this.aktuell = aktuell;
		Bestellungs_ID = bestellungs_ID;
	}
	

}