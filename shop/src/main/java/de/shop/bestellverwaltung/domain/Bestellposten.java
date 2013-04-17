package de.shop.bestellverwaltung.domain;


public class Bestellposten {

	private Long id;
	private Long menge;
	private Long version;
	private Long zwischenpreis;
	private Bestellung bestellungId;
	
	
	
	public Long getPositions_ID() {
		return id;
	}
	public void setPositions_ID(Long positions_ID) {
		id = positions_ID;
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
	public Long getZwischenpreis() {
		return zwischenpreis;
	}
	public void setZwischenpreis(Long zwischenpreis) {
		this.zwischenpreis = zwischenpreis;
	}
	public Bestellung getBestellung_ID() {
		return bestellungId;
	}
	public void setBestellung_ID(Bestellung bestellung_ID) {
		bestellungId = bestellung_ID;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Bestellposten [Positions_ID=" + id + ", Menge="
				+ menge + ", Version=" + version + ", Zwischenpreis="
				+ zwischenpreis + ", Bestellung_ID=" + bestellungId + "]";
	}	
}

