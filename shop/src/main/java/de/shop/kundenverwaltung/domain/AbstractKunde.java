package de.shop.kundenverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
//import java.security.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import de.shop.bestellverwaltung.domain.Bestellung;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Privatkunde.class, name = AbstractKunde.PRIVATKUNDE),
	@Type(value = Firmenkunde.class, name = AbstractKunde.FIRMENKUNDE) 
	})
public abstract class AbstractKunde implements Serializable {
	private static final long serialVersionUID = 7401524595142572933L;
	
	public static final String PRIVATKUNDE = "P";
	public static final String FIRMENKUNDE = "F";
	
	//muss noch bearbeitet werden
	
	
	
	
	
	private Long id;
	private String vorname;
	private String nachname;
	private GeschlechtType geschlecht;
	//in der Vergangenheit
	private Date geburtsdatum;
	private String email;
	//in der Vergangenheit
	private Date seit;
	//aktuelles Datum
	private Timestamp aktualisiert;
	
	private Adresse adresse;
	
	
	
	@JsonIgnore
	private List<Bestellung> bestellungen;
	private URI bestellungenUri;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public GeschlechtType getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(GeschlechtType geschlecht) {
		this.geschlecht = geschlecht;
	}
	
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getSeit() {
		return seit;
	}
	public void setSeit(Date seit) {
		this.seit = seit;
	}
	public Timestamp getAktualisiert() {
		return aktualisiert;
	}
	public void setAktualisiert(Timestamp aktualisiert) {
		this.aktualisiert = aktualisiert;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
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
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result
				+ ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result
				+ ((bestellungen == null) ? 0 : bestellungen.hashCode());
		result = prime * result
				+ ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result
				+ ((seit == null) ? 0 : seit.hashCode());
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
		AbstractKunde other = (AbstractKunde) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		}
		else if (!adresse.equals(other.adresse))
			return false;
		if (aktualisiert == null) {
			if (other.aktualisiert != null)
				return false;
		} 
		else if (!aktualisiert.equals(other.aktualisiert))
			return false;
		if (bestellungen == null) {
			if (other.bestellungen != null)
				return false;
		} 
		else if (!bestellungen.equals(other.bestellungen))
			return false;
		if (bestellungenUri == null) {
			if (other.bestellungenUri != null)
				return false;
		}
		else if (!bestellungenUri.equals(other.bestellungenUri))
			return false;
		if (seit == null) {
			if (other.seit != null)
				return false;
		} 
		else if (!seit.equals(other.seit))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		if (geburtsdatum == null) {
			if (other.geburtsdatum != null)
				return false;
		}
		else if (!geburtsdatum.equals(other.geburtsdatum))
			return false;
		if (geschlecht != other.geschlecht)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} 
		else if (!nachname.equals(other.nachname))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} 
		else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AbstractKunde [id=" + id + ", vorname=" + vorname
				+ ", nachname=" + nachname + ", geschlecht=" + geschlecht
				+ ", geburtsdatum=" + geburtsdatum + ", email=" + email
				+ ", seit=" + seit + ", adresse=" + adresse
				+ ", bestellungenUri=" + bestellungenUri + "]";
	}

}
