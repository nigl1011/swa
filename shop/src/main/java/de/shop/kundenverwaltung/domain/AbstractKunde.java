package de.shop.kundenverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;
import static de.shop.util.Constants.MIN_ID;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.ScriptAssert;
import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.util.IdGroup;

@Entity
@Table(name = "kunde")
@Inheritance
@DiscriminatorColumn(name = "art", length = 1)
@NamedQueries({
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN,
                query = "SELECT k"
				        + " FROM   AbstractKunde k"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN_FETCH_BESTELLUNGEN,
				query = "SELECT  DISTINCT k"
						+ " FROM AbstractKunde k LEFT JOIN FETCH k.bestellungen"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN_ORDER_BY_ID,
		        query = "SELECT   k"
				        + " FROM  AbstractKunde k"
		                + " ORDER BY k.id"),
	@NamedQuery(name  = AbstractKunde.FIND_IDS_BY_PREFIX,
		        query = "SELECT   k.id"
		                + " FROM  AbstractKunde k"
		                + " WHERE CONCAT('', k.id) LIKE :" + AbstractKunde.PARAM_KUNDE_ID_PREFIX
		                + " ORDER BY k.id"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN_BY_NACHNAME,
	            query = "SELECT k"
				        + " FROM   AbstractKunde k"
	            		+ " WHERE  UPPER(k.nachname) = UPPER(:" + AbstractKunde.PARAM_KUNDE_NACHNAME + ")"),
	@NamedQuery(name  = AbstractKunde.FIND_NACHNAMEN_BY_PREFIX,
   	            query = "SELECT   DISTINCT k.nachname"
				        + " FROM  AbstractKunde k "
	            		+ " WHERE UPPER(k.nachname) LIKE UPPER(:"
	            		+ AbstractKunde.PARAM_KUNDE_NACHNAME_PREFIX + ")"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN_BY_NACHNAME_FETCH_BESTELLUNGEN,
	            query = "SELECT DISTINCT k"
			            + " FROM   AbstractKunde k LEFT JOIN FETCH k.bestellungen"
			            + " WHERE  UPPER(k.nachname) = UPPER(:" + AbstractKunde.PARAM_KUNDE_NACHNAME + ")"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDE_BY_ID_FETCH_BESTELLUNGEN,
	            query = "SELECT DISTINCT k"
			            + " FROM   AbstractKunde k LEFT JOIN FETCH k.bestellungen"
			            + " WHERE  k.id = :" + AbstractKunde.PARAM_KUNDE_ID),
   	@NamedQuery(name  = AbstractKunde.FIND_KUNDE_BY_ID_FETCH_WARTUNGSVERTRAEGE,
   	            query = "SELECT DISTINCT k"
   			            + " FROM   AbstractKunde k LEFT JOIN FETCH k.wartungsvertraege"
   			            + " WHERE  k.id = :" + AbstractKunde.PARAM_KUNDE_ID),
   	@NamedQuery(name  = AbstractKunde.FIND_KUNDE_BY_EMAIL,
   	            query = "SELECT DISTINCT k"
   			            + " FROM   AbstractKunde k"
   			            + " WHERE  k.email = :" + AbstractKunde.PARAM_KUNDE_EMAIL),
    @NamedQuery(name  = AbstractKunde.FIND_KUNDEN_BY_PLZ,
	            query = "SELECT k"
				        + " FROM  AbstractKunde k"
			            + " WHERE k.adresse.plz = :" + AbstractKunde.PARAM_KUNDE_ADRESSE_PLZ),
	@NamedQuery(name = AbstractKunde.FIND_KUNDEN_BY_DATE,
			    query = "SELECT k"
			            + " FROM  AbstractKunde k"
			    		+ " WHERE k.seit = :" + AbstractKunde.PARAM_KUNDE_SEIT),
	@NamedQuery(name = AbstractKunde.FIND_PRIVATKUNDEN_FIRMENKUNDEN,
			    query = "SELECT k"
			            + " FROM  AbstractKunde k"
			    		+ " WHERE TYPE(k) IN (Privatkunde, Firmenkunde)")
})
@ScriptAssert(lang = "javascript",
	          script = "(_this.password == null && _this.passwordWdh == null)"
	                   + "|| (_this.password != null && _this.password.equals(_this.passwordWdh))",
	          message = "{kundenverwaltung.kunde.password.notEqual}",
	          groups = PasswordGroup.class)


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Privatkunde.class, name = AbstractKunde.PRIVATKUNDE),
	@Type(value = Firmenkunde.class, name = AbstractKunde.FIRMENKUNDE) 
	})
public abstract class AbstractKunde implements Serializable {
	private static final long serialVersionUID = 7401524595142572933L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
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
	public static final int DETAILS_LENGTH_MAX = 128 * 1024;
	public static final int PASSWORD_LENGTH_MAX = 256;

	
	private static final String PREFIX = "AbstractKunde.";
	public static final String FIND_KUNDEN = PREFIX + "findKunden";
	public static final String FIND_KUNDEN_FETCH_BESTELLUNGEN = PREFIX + "findKundenFetchBestellungen";
	public static final String FIND_KUNDEN_ORDER_BY_ID = PREFIX + "findKundenOrderById";
	public static final String FIND_IDS_BY_PREFIX = PREFIX + "findIdsByPrefix";
	public static final String FIND_KUNDEN_BY_NACHNAME = PREFIX + "findKundenByNachname";
	public static final String FIND_KUNDEN_BY_NACHNAME_FETCH_BESTELLUNGEN =
		                       PREFIX + "findKundenByNachnameFetchBestellungen";
	public static final String FIND_NACHNAMEN_BY_PREFIX = PREFIX + "findNachnamenByPrefix";
	public static final String FIND_KUNDE_BY_ID_FETCH_WARTUNGSVERTRAEGE =
		                       PREFIX + "findKundenByNachnameFetchWartungsvertraege";
	public static final String FIND_KUNDE_BY_ID_FETCH_BESTELLUNGEN =
		                       PREFIX + "findKundeByIdFetchBestellungen";
	public static final String FIND_KUNDE_BY_EMAIL = PREFIX + "findKundeByEmail";
	public static final String FIND_KUNDEN_BY_PLZ = PREFIX + "findKundenByPlz";
	public static final String FIND_KUNDEN_BY_DATE = PREFIX + "findKundenByDate";
	public static final String FIND_PRIVATKUNDEN_FIRMENKUNDEN = PREFIX + "findPrivatkundenFirmenkunden";
	
	public static final String PARAM_KUNDE_ID = "kundeId";
	public static final String PARAM_KUNDE_ID_PREFIX = "idPrefix";
	public static final String PARAM_KUNDE_NACHNAME = "nachname";
	public static final String PARAM_KUNDE_NACHNAME_PREFIX = "nachnamePrefix";
	public static final String PARAM_KUNDE_ADRESSE_PLZ = "plz";
	public static final String PARAM_KUNDE_SEIT = "seit";
	public static final String PARAM_KUNDE_EMAIL = "email";
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_ID, message = "{kundenverwaltung.kunde.id.min}", groups = IdGroup.class)
	private Long id = KEINE_ID;
	
	@Column(length = VORNAME_LENGTH_MAX)
	@NotNull(message = "{kundenverwaltung.kunde.vorname.notNull}")
	@Size(min = VORNAME_LENGTH_MIN, max = VORNAME_LENGTH_MAX,
	      message = "{kundenverwaltung.kunde.vorname.length}")
	@Pattern(regexp = VORNAME_PATTERN, message = "{kundenverwaltung.kunde.vorname.pattern}")
	private String vorname;
	
	@Column(length = NACHNAME_LENGTH_MAX)
	@NotNull(message = "{kundenverwaltung.kunde.nachname.notNull}")
	@Size(min = NACHNAME_LENGTH_MIN, max = NACHNAME_LENGTH_MAX,
	      message = "{kundenverwaltung.kunde.nachname.length}")
	@Pattern(regexp = NACHNAME_PATTERN, message = "{kundenverwaltung.kunde.nachname.pattern}")
	private String nachname;
	
	
	@Temporal(DATE)
	@Past(message = "{kundenverwaltung.kunde.seit.past}")
	private Date seit;
	
	@Column(nullable = false)
	@Enumerated
	private GeschlechtType geschlecht;
	

	@Temporal(TIMESTAMP)
	@Past(message = "{kundenverwaltung.kunde.geburtsdatum.past}")
	private Date geburtsdatum;
	
	@Column(length = EMAIL_LENGTH_MAX, nullable = false, unique = true)
	@Email(message = "{kundenverwaltung.kunde.email.pattern}")
	@NotNull(message = "{kundenverwaltung.kunde.email.notNull}")
	@Size(max = EMAIL_LENGTH_MAX, message = "{kundenverwaltung.kunde.email.length}")
	private String email;
	
	private boolean newsletter = false;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@Past(message = "{kundenverwaltung.kunde.erzeugt.past}")
	@JsonIgnore
	private Date erzeugt;
	
	@Column(nullable = false, precision = 5, scale = 4)
	private BigDecimal rabatt;
	
	@Column(nullable = false, precision = 15, scale = 3)
	private BigDecimal umsatz;
	
	@Column(length = PASSWORD_LENGTH_MAX)
	private String password;
	
	@Transient
	@JsonIgnore
	private String passwordWdh;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date aktualisiert;
	
	@OneToOne(cascade = { PERSIST, REMOVE }, mappedBy = "kunde")
	@Valid
	@NotNull(message = "{kundenverwaltung.kunde.adresse.notNull}")
	private Adresse adresse;

	// Default: fetch=LAZY
	@OneToMany
	@JoinColumn(name = "kunde_fk", nullable = false)
	@OrderColumn(name = "idx", nullable = false)
	@JsonIgnore
	private List<Bestellung> bestellungen;
	
	//Transient wird nicht in der Datenbank abgespeichert
	@Transient
	private URI bestellungenUri;

	@OneToMany
	@JoinColumn(name = "kunde_fk", nullable = false)
	@OrderColumn(name = "idx", nullable = false)
	@JsonIgnore
	private List<Wartungsvertrag> wartungsvertraege;
	
	@PrePersist
	protected void prePersist() {
		erzeugt = new Date();
		aktualisiert = new Date();
	}
	
	@PostPersist
	protected void postPersist() {
		LOGGER.debugf("Neuer Kunde mit ID=%d", id);
	}
	
	@PreUpdate
	protected void preUpdate() {
		aktualisiert = new Date();
	}
	
	@PostLoad
	protected void postLoad() {
		passwordWdh = password;
	}
	
	public void setValues(AbstractKunde k) {
		nachname = k.nachname;
		vorname = k.vorname;
		umsatz = k.umsatz;
		rabatt = k.rabatt;
		geburtsdatum = k.geburtsdatum;
		seit = k.seit;
		email = k.email;
		password = k.password;
		passwordWdh = k.password;
	}
	
	
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
	public String getGeburtsdatumAsString(int style, Locale locale) {
		Date temp = geburtsdatum;
		if (temp == null) {
			temp = new Date();
		}
		final DateFormat f = DateFormat.getDateInstance(style, locale);
		return f.format(temp);
	}
	
	// Parameter, z.B. DateFormat.MEDIUM, Locale.GERMANY
	// MEDIUM fuer Format dd.MM.yyyy
	public void setGeburtsdatum(String geburtsdatumStr, int style, Locale locale) {
		final DateFormat f = DateFormat.getDateInstance(style, locale);
		try {
			this.geburtsdatum = f.parse(geburtsdatumStr);
		}
		catch (ParseException e) {
			throw new RuntimeException("Kein gueltiges Datumsformat fuer: " + geburtsdatumStr, e);
		}
	}
	public Date getSeit() {
		return seit == null ? null : (Date) seit.clone();
	}
	public void setSeit(Date seit) {
		this.seit = seit == null ? null : (Date) seit.clone();
	}

	// Parameter, z.B. DateFormat.MEDIUM, Locale.GERMANY
	// MEDIUM fuer Format dd.MM.yyyy
	public String getSeitAsString(int style, Locale locale) {
		Date temp = seit;
		if (temp == null) {
			temp = new Date();
		}
		final DateFormat f = DateFormat.getDateInstance(style, locale);
		return f.format(temp);
	}
	
	// Parameter, z.B. DateFormat.MEDIUM, Locale.GERMANY
	// MEDIUM fuer Format dd.MM.yyyy
	public void setSeit(String seitStr, int style, Locale locale) {
		final DateFormat f = DateFormat.getDateInstance(style, locale);
		try {
			this.seit = f.parse(seitStr);
		}
		catch (ParseException e) {
			throw new RuntimeException("Kein gueltiges Datumsformat fuer: " + seitStr, e);
		}
	}

	public BigDecimal getUmsatz() {
		return umsatz;
	}

	public void setUmsatz(BigDecimal umsatz) {
		this.umsatz = umsatz;
	}
	
	public BigDecimal getRabatt() {
		return rabatt;
	}
	public void setRabatt(BigDecimal rabatt) {
		this.rabatt = rabatt;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}
	public boolean isNewsletter() {
		return newsletter;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordWdh() {
		return passwordWdh;
	}

	public void setPasswordWdh(String passwordWdh) {
		this.passwordWdh = passwordWdh;
	}
	@JsonProperty("Erzeugt am:")
	public Date getErzeugt() {
		return erzeugt == null ? null : (Date) erzeugt.clone();
	}
	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt == null ? null : (Date) erzeugt.clone();
	}
	public Date getAktualisiert() {
		return aktualisiert;
	}
	public void setAktualisiert(Date aktualisiert) {
		this.aktualisiert = aktualisiert;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public List<Bestellung> getBestellungen() {
		if (bestellungen == null) {
			return null;
		}		
		return Collections.unmodifiableList(bestellungen);
	}
	
	public void setBestellungen(List<Bestellung> bestellungen) {
		if (this.bestellungen == null) {
			this.bestellungen = bestellungen;
			return;
		}
		
		// Wiederverwendung der vorhandenen Collection
		this.bestellungen.clear();
		if (bestellungen != null) {
			this.bestellungen.addAll(bestellungen);
		}
	}
	
	public AbstractKunde addBestellung(Bestellung bestellung) {
		if (bestellungen == null) {
			bestellungen = new ArrayList<>();
		}
		bestellungen.add(bestellung);
		return this;
	}
	
	public URI getBestellungenUri() {
		return bestellungenUri;
	}
	public void setBestellungenUri(URI bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
	}

	public List<Wartungsvertrag> getWartungsvertraege() {
		if (wartungsvertraege == null) {
			return null;
		}
		
		return Collections.unmodifiableList(wartungsvertraege);
	}

	public void setWartungsvertraege(List<Wartungsvertrag> wartungsvertraege) {
		if (this.wartungsvertraege == null) {
			this.wartungsvertraege = wartungsvertraege;
			return;
		}
		
		// Wiederverwendung der vorhandenen Collection
		this.wartungsvertraege.clear();
		if (wartungsvertraege != null) {
			this.wartungsvertraege.addAll(wartungsvertraege);
		}
	}
	
	public AbstractKunde addWartungsvertrag(Wartungsvertrag wartungsvertrag) {
		if (wartungsvertraege == null) {
			wartungsvertraege = new ArrayList<>();
		}
		wartungsvertraege.add(wartungsvertrag);
		return this;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result + ((bestellungen == null) ? 0 : bestellungen.hashCode());
		result = prime * result + ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((geburtsdatum == null) ? 0 : geburtsdatum.hashCode());
		result = prime * result + ((geschlecht == null) ? 0 : geschlecht.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractKunde other = (AbstractKunde) obj;
		
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		}
		else if (!email.equals(other.email)) {
			return false;
		}
		
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "AbstractKunde [id=" + id
			   + ", nachname=" + nachname + ", vorname=" + vorname
			   + ", geschlecht=" + geschlecht
			   + ", geburtsdatum=" + getGeburtsdatumAsString(DateFormat.MEDIUM, Locale.GERMANY)
			   + ", seit=" + getSeitAsString(DateFormat.MEDIUM, Locale.GERMANY)
			   + ", umsatz=" + umsatz
			   + ", email=" + email
			   + ", password=" + password + ", passwordWdh=" + passwordWdh
			   + ", erzeugt=" + erzeugt
			   + ", aktualisiert=" + aktualisiert 
			   + "]";
	}


}
