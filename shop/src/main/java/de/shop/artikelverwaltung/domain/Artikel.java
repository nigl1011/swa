package de.shop.artikelverwaltung.domain;

import static de.shop.util.Constants.MIN_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nonnegative;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;

import de.shop.util.IdGroup;

@Cacheable
@Entity
@Table(name = "artikel")
@NamedQueries({
	@NamedQuery(name  = Artikel.FIND_ARTIKEL,
            query = "SELECT a"
			        + " FROM   Artikel a"),
	@NamedQuery(name  = Artikel.FIND_VERFUEGBARE_ARTIKEL,
            	query = "SELECT      a"
            	        + " FROM     Artikel a"
						+ " WHERE    a.verfuegbar = TRUE"
                        + " ORDER BY a.id ASC"),
	@NamedQuery(name  = Artikel.FIND_ARTIKEL_BY_BEZ,
            	query = "SELECT      a"
                        + " FROM     Artikel a"
						+ " WHERE    a.bezeichnung LIKE :" + Artikel.PARAM_BEZEICHNUNG
						+ "          AND a.verfuegbar = TRUE"
			 	        + " ORDER BY a.id ASC"),
	@NamedQuery(name  = Artikel.FIND_ARTIKEL_BY_KAT,
		        query = "SELECT      a"
		                + " FROM     Artikel a"
		                + " WHERE    a.kategorie LIKE :" + Artikel.PARAM_KATEGORIE
		                + "          AND a.verfuegbar = TRUE"
		                + " ORDER BY a.id ASC"),
	@NamedQuery(name  = Artikel.FIND_ARTIKEL_BY_KAT_AND_FAR,
				        query = "SELECT      a"
				                + " FROM     Artikel a"
				                + " WHERE    a.kategorie LIKE :" + Artikel.PARAM_KATEGORIE
				                + "          AND a.farbe LIKE :" + Artikel.PARAM_FARBE
				                + "          AND a.verfuegbar = TRUE"
				                + " ORDER BY a.id ASC"),
   	@NamedQuery(name  = Artikel.FIND_ARTIKEL_MAX_PREIS,
            	query = "SELECT      a"
                        + " FROM     Artikel a"
						+ " WHERE    a.preis < :" + Artikel.PARAM_PREIS
			 	        + " ORDER BY a.id ASC")
})

public class Artikel implements Serializable {

private static final long serialVersionUID = 1472129607838538329L;
private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());


private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+";
private static final String FARBEN_PATTERN = "[a-z\u00E4\u00F6\u00FC\u00DF]+";

private static final String PREFIX = "Artikel.";
public static final String FIND_ARTIKEL = PREFIX + "findArtikel";
public static final String FIND_VERFUEGBARE_ARTIKEL = PREFIX + "findVerfuegbareArtikel";
public static final String FIND_ARTIKEL_BY_BEZ = PREFIX + "findArtikelByBez";
public static final String FIND_ARTIKEL_BY_KAT = PREFIX + "findArtikelByKat";
public static final String FIND_ARTIKEL_BY_KAT_AND_FAR = PREFIX + "findArtikelByKatAndFar";
public static final String FIND_ARTIKEL_MAX_PREIS = PREFIX + "findArtikelByMaxPreis";
public static final String PARAM_BEZEICHNUNG = "bezeichnung";
public static final String PARAM_KATEGORIE = "kategorie";
public static final String PARAM_FARBE = "farbe";
public static final String PARAM_PREIS = "preis";

private static final int BEZEICHNUNG_LENGTH_MIN = 3;
private static final int BEZEICHNUNG_LENGTH_MAX = 32;
private static final String BEZEICHNUNG_PATTERN = NAME_PATTERN;
private static final int FARBE_LENGTH_MIN = 3;
private static final int FARBE_LENGTH_MAX = 12;
private static final String FARBE_PATTERN = FARBEN_PATTERN;

@Id
@GeneratedValue
@Column(nullable = false, updatable = false)
@Min(value = MIN_ID, message = "{artikelverwaltung.artikel.id.min}", groups = IdGroup.class)
private Long id;

@Column(length = BEZEICHNUNG_LENGTH_MAX, nullable = false)
@NotEmpty(message = "{artikelverwaltung.artikel.bezeichnung.notEmpty}")
@Size(min = BEZEICHNUNG_LENGTH_MIN, max = BEZEICHNUNG_LENGTH_MAX,
message = "{artikelverwaltung.artikel.bezeichnung.length}")
@Pattern(regexp = BEZEICHNUNG_PATTERN, message = "{artikelverwaltung.artikel.bezeichnung.pattern}")
private String bezeichnung = "";

@Column(name = "kategorie_fk", nullable = false)
@Enumerated
private KategorieType kategorie;

@Column(length = FARBE_LENGTH_MAX, nullable = false)
@NotNull(message = "{artikelverwaltung.artikel.farbe.notNull}")
@Size(min = FARBE_LENGTH_MIN, max = FARBE_LENGTH_MAX,
message = "{artikelverwaltung.artikel.farbe.length}")
@Pattern(regexp = FARBE_PATTERN, message = "{artikelverwaltung.artikel.farbe.pattern}")
private String farbe;

@NotNull
@Nonnegative
@Column(nullable = false, precision = 7, scale = 2)
private BigDecimal preis;

private boolean verfuegbar;

@Column(nullable = false)
@Temporal(TIMESTAMP)
@JsonIgnore
private Date erstellt;

@Column(nullable = false)
@Temporal(TIMESTAMP)
@JsonIgnore
private Date aktualisiert;

public void setValues(Artikel a) {
	bezeichnung = a.bezeichnung;
	farbe = a.farbe;
	preis = a.preis;
	kategorie = a.kategorie;
	
}
public Artikel() {
	super();
}

public Artikel(Long id, String bezeichnung, KategorieType kategorie, String farbe, BigDecimal preis) {
	super();
	this.id = id;
	this.bezeichnung = bezeichnung;
	this.kategorie = kategorie;
	this.farbe = farbe;
	this.preis = preis;
}

public Artikel(Artikel artikel) {
	super();
	this.id = artikel.id;
	this.bezeichnung = artikel.bezeichnung;
}

@PrePersist
private void prePersist() {
	erstellt = new Date();
	aktualisiert = new Date();
}

@PostPersist
private void postPersist() {
	LOGGER.debugf("Neuer Artikel mit ID=%d", id);
}

@PreUpdate
private void preUpdate() {
	aktualisiert = new Date();
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
public Date getErstellt() {
return erstellt == null ? null : (Date) erstellt.clone();
}
public void setErstellt(Date erstellt) {
this.erstellt = erstellt == null ? null : (Date) erstellt.clone();
}
public Date getAktualisiert() {
return aktualisiert == null ? null : (Date) aktualisiert.clone();
}
public void setAktualisiert(Date aktualisiert) {
this.aktualisiert = aktualisiert == null ? null : (Date) aktualisiert.clone();
}

@Override
public String toString() {
return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
+ ", kategorie=" + kategorie + ", farbe=" + farbe + ", preis="
+ preis + ", verf�gbar=" + verfuegbar + ", erstellt=" + erstellt
+ ", aktualisiert=" + aktualisiert + "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
			+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
	result = prime * result + ((farbe == null) ? 0 : farbe.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((kategorie == null) ? 0 : kategorie.hashCode());
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
	final Artikel other = (Artikel) obj;
	if (bezeichnung == null) {
		if (other.bezeichnung != null)
			return false;
	}
	else if (!bezeichnung.equals(other.bezeichnung))
		return false;
	else if (!farbe.equals(other.farbe))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	}
	else if (!id.equals(other.id))
		return false;
	if (kategorie != other.kategorie)
		return false;
	if (preis == null) {
		if (other.preis != null)
			return false;
	}
	else if (!preis.equals(other.preis))
		return false;
	if (verfuegbar != other.verfuegbar)
		return false;
	return true;
}


}
