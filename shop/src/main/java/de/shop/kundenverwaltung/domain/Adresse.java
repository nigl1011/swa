package de.shop.kundenverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;
import static de.shop.util.Constants.MIN_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.logging.Logger;

import de.shop.util.IdGroup;

@Entity
@Table(name = "adresse")
public class Adresse implements Serializable {
	private static final long serialVersionUID = -3029272617931844501L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+";
			
	

	private static final int HAUSNR_LENGTH_MIN = 1;
	private static final int HAUSNR_LENGTH_MAX = 5;
	public static final int PLZ_LENGTH_MAX = 5;
	public static final int ORT_LENGTH_MIN = 2;
	public static final int ORT_LENGTH_MAX = 32;
	

	private static final int STRASSE_LENGTH_MIN = 3;
	private static final int STRASSE_LENGTH_MAX = 32;



	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_ID, message = "{kundenverwaltung.adresse.id.min}", groups = IdGroup.class)
	private Long id = KEINE_ID;
	
	@Column(length = STRASSE_LENGTH_MAX, nullable = false)
	@NotNull(message = "{kundenverwaltung.adresse.strasse.notNull}")
	@Size(min = STRASSE_LENGTH_MIN, max = STRASSE_LENGTH_MAX, message = "{kundenverwaltung.adresse.strasse.length}")
	@Pattern(regexp = NAME_PATTERN, message = "{kundenverwaltung.kunde.strasse.pattern}")
	private String strasse;
	

	@NotNull(message = "{kundenverwaltung.adresse.hausnr.notNull}")
	@Size(min = HAUSNR_LENGTH_MIN, max = HAUSNR_LENGTH_MAX, message = "{kundenverwaltung.adresse.hausnr.length}")
	private String hausnr;
	
	@Column(length = PLZ_LENGTH_MAX, nullable = false)
	@NotNull(message = "{kundenverwaltung.adresse.plz.notNull}")
	@Pattern(regexp = "\\d{5}", message = "{kundenverwaltung.adresse.plz}")
	private String plz;
	
	@Column(length = ORT_LENGTH_MAX, nullable = false)
	@NotNull(message = "{kundenverwaltung.adresse.ort.notNull}")
	@Size(min = ORT_LENGTH_MIN, max = ORT_LENGTH_MAX, message = "{kundenverwaltung.adresse.ort.length}")
	@Pattern(regexp = NAME_PATTERN, message = "{kundenverwaltung.kunde.ort.pattern}")
	private String ort;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date aktualisiert;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date erzeugt;
	
	@OneToOne
	@JoinColumn(name = "kunde_fk", nullable = false)
	//@NotNull(message = "{kundenverwaltung.adresse.kunde.notNull}")
	@JsonIgnore
	private AbstractKunde kunde;
	
	@PrePersist
	private void prePersist() {
		erzeugt = new Date();
		aktualisiert = new Date();
	}
	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Adresse mit ID=%s", id);
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
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public String getHausnr() {
		return hausnr;
	}
	public void setHausnr(String hausnr) {
		this.hausnr = hausnr;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	public AbstractKunde getKunde() {
		return kunde;
	}
	public void setKunde(AbstractKunde kunde) {
		this.kunde = kunde;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
	result = prime * result
				+ ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result + ((hausnr == null) ? 0 : hausnr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
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
		final Adresse other = (Adresse) obj;
		
		if (plz == null) {
			if (other.plz != null) {
				return false;
			}
		}
		else if (!plz.equals(other.plz)) {
			return false;
		}
		
		if (ort == null) {
			if (other.ort != null) {
				return false;
			}
		}
		else if (!ort.equals(other.ort)) {
			return false;
		}
		
		if (strasse == null) {
			if (other.strasse != null) {
				return false;
			}
		}
		else if (!strasse.equals(other.strasse)) {
			return false;
		}
		
		if (hausnr == null) {
			if (other.hausnr != null) {
				return false;
			}
		}
		else if (!hausnr.equals(other.hausnr)) {
			return false;
		}
		
		return true;
	}	
	
	@Override
	public String toString() {
		return "Adresse [id=" + id 
				+ ", strasse=" + strasse 
				+ ", hausnr="
				+ hausnr + ", plz=" 
				+ plz + ", ort=" + ort 
			    + ", erzeugt=" + erzeugt
			    + ", aktualisiert=" + aktualisiert +"]";
	}

}
