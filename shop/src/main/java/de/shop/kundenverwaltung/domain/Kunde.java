package de.shop.kundenverwaltung.domain;


import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.bestellverwaltung.domain.Bestellung;


@Entity
public class Kunde implements Serializable {

	
	private static final long serialVersionUID = 6040046722846055034L;
	
	public Kunde(Long id, String vorname, String nachname,
			Date geburtsdatum, String geschlecht, String email,
			Date eintrittsdatum, Date aktualisiert,
			List<Bestellung> bestellungen, URI bestellungenUri) {
		
		super();
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.geschlecht = geschlecht;
		this.email = email;
		Eintrittsdatum = eintrittsdatum;
		Aktualisiert = aktualisiert;
		this.bestellungen = bestellungen;
		this.bestellungenUri = bestellungenUri;
	}
	private Long id;
	private String vorname;
	private String nachname;
	private Date geburtsdatum;
	private String geschlecht;
	private String email;
	private Date Eintrittsdatum;
	private Date Aktualisiert;
	
	@JsonIgnore
	private List<Bestellung> bestellungen;
	private URI bestellungenUri;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	//JsonProperty() seite24
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	public String getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getEintrittsdatum() {
		return Eintrittsdatum;
	}
	public void setEintrittsdatum(Date eintrittsdatum) {
		Eintrittsdatum = eintrittsdatum;
	}
	public Date getAktualisiert() {
		return Aktualisiert;
	}
	public void setAktualisiert(Date aktualisiert) {
		Aktualisiert = aktualisiert;
	}
	public List<Bestellung> getBestellungen() {
		return bestellungen;
	}
	public void setBestellungen(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen;
	}
	public URI getBestellungenUri() {
		return bestellungenUri;
	}
	public void setBestellungenUri(URI bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result
				+ ((Aktualisiert == null) ? 0 : Aktualisiert.hashCode());
		result = prime * result
				+ ((Eintrittsdatum == null) ? 0 : Eintrittsdatum.hashCode());
		result = prime * result
				+ ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((geburtsdatum == null) ? 0 : geburtsdatum.hashCode());
		result = prime * result
				+ ((geschlecht == null) ? 0 : geschlecht.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
		Kunde other = (Kunde) obj;
		if (Aktualisiert == null) {
			if (other.Aktualisiert != null)
				return false;
		} else if (!Aktualisiert.equals(other.Aktualisiert))
			return false;
		if (Eintrittsdatum == null) {
			if (other.Eintrittsdatum != null)
				return false;
		} else if (!Eintrittsdatum.equals(other.Eintrittsdatum))
			return false;
		if (bestellungenUri == null) {
			if (other.bestellungenUri != null)
				return false;
		} else if (!bestellungenUri.equals(other.bestellungenUri))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (geburtsdatum == null) {
			if (other.geburtsdatum != null)
				return false;
		} else if (!geburtsdatum.equals(other.geburtsdatum))
			return false;
		if (geschlecht == null) {
			if (other.geschlecht != null)
				return false;
		} else if (!geschlecht.equals(other.geschlecht))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} else if (!nachname.equals(other.nachname))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Kunde [id=" + id + ", vorname=" + vorname
				+ ", nachname=" + nachname + ", geburtsdatum=" + geburtsdatum
				+ ", geschlecht=" + geschlecht + ", email=" + email
				+  ", bestellungenUri=" + bestellungenUri + "]";
	}
// nicht sicher
	public void setAdresse(Adresse adresse) {
		// TODO Auto-generated method stub
		
	}
	public Adresse getAdresse() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
}
