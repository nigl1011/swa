package de.shop.kundenverwaltung.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.hibernate.validator.constraints.Email;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.util.IdGroup;



@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Privatkunde.class, name = AbstractKunde.PRIVATKUNDE),
	@Type(value = Firmenkunde.class, name = AbstractKunde.FIRMENKUNDE) 
	})
public abstract class AbstractKunde implements Serializable {
	private static final long serialVersionUID = 7401524595142572933L;
	
	public static final String PRIVATKUNDE = "P";
	public static final String FIRMENKUNDE = "F";
	
	//Pattern mit UTF-8 (statt Latin-1 bzw. ISO-8859-1) Schreibweise fuer Umlaute:
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+";
	private static final String NACHNAME_PREFIX = "(o'|von|von der|von und zu|van)?";
	
	private static final String VORNAME_PATTERN = NAME_PATTERN;
	private static final int VORNAME_LENGTH_MIN = 2;
	private static final int VORNAME_LENGTH_MAX = 32;
	public static final String NACHNAME_PATTERN = NACHNAME_PREFIX + NAME_PATTERN + "(-" + NAME_PATTERN + ")?";
	public static final int NACHNAME_LENGTH_MIN = 2;
	public static final int NACHNAME_LENGTH_MAX = 32;
	public static final int EMAIL_LENGTH_MAX = 128;
	private static final long MIN_ID = 1;

	

	@Min(value = MIN_ID, message = "{kundenverwaltung.kunde.id.min}", groups = IdGroup.class)
	private Long id;
	
	@NotNull(message = "{kundenverwaltung.kunde.vorname.notNull}")
	@Size(min = VORNAME_LENGTH_MIN, max = VORNAME_LENGTH_MAX,
	      message = "{kundenverwaltung.kunde.vorname.length}")
	@Pattern(regexp = VORNAME_PATTERN, message = "{kundenverwaltung.kunde.vorname.pattern}")
	private String vorname;
	
	@NotNull(message = "{kundenverwaltung.kunde.nachname.notNull}")
	@Size(min = NACHNAME_LENGTH_MIN, max = NACHNAME_LENGTH_MAX,
	      message = "{kundenverwaltung.kunde.nachname.length}")
	@Pattern(regexp = NACHNAME_PATTERN, message = "{kundenverwaltung.kunde.nachname.pattern}")
	private String nachname;
	
	@Enumerated
	private GeschlechtType geschlecht;

	@Past(message = "{kundenverwaltung.kunde.geburtsdatum.past}")
	private Date geburtsdatum;
	
	@Email(message = "{kundenverwaltung.kunde.email.pattern}")
	@NotNull(message = "{kundenverwaltung.kunde.email.notNull}")
	@Size(max = EMAIL_LENGTH_MAX, message = "{kundenverwaltung.kunde.email.length}")
	private String email;
	
	@JsonIgnore
	@Past(message = "{kundenverwaltung.kunde.erzeugt.past}")
	private Date erzeugt;
	
	@Temporal(TIMESTAMP)
	private Timestamp aktualisiert;
	
	
	@Valid
	@NotNull(message = "{kundenverwaltung.kunde.adresse.notNull}")
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
		return geburtsdatum == null ? null : (Date) geburtsdatum.clone();
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum == null ? null : (Date) geburtsdatum.clone();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty("Erzeugt am:")
	public Date getErzeugt() {
		return erzeugt == null ? null : (Date) erzeugt.clone();
	}
	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt == null ? null : (Date) erzeugt.clone();
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
		result = prime * result
				+ ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result
				+ ((bestellungen == null) ? 0 : bestellungen.hashCode());
		result = prime * result
				+ ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result
				+ ((erzeugt == null) ? 0 : erzeugt.hashCode());
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
		final AbstractKunde other = (AbstractKunde) obj;
		/*if (adresse == null) {
			if (other.adresse != null)
				return false;
		}
		else if (!adresse.equals(other.adresse))
			return false;*/
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
		if (erzeugt == null) {
			if (other.erzeugt != null)
				return false;
		} 
		else if (!erzeugt.equals(other.erzeugt))
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
				+ ", erzeugt=" + erzeugt + ", adresse=" + adresse 
				+ "]";
	}

}
