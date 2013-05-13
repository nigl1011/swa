package de.shop.util;

import java.lang.invoke.MethodHandles;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellposten;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.StatusType;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Firmenkunde;
import de.shop.kundenverwaltung.domain.GeschlechtType;
import de.shop.kundenverwaltung.domain.HobbyType;
import de.shop.kundenverwaltung.domain.Privatkunde;
import de.shop.lieferverwaltung.domain.Lieferung;
import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.domain.KategorieType;

/**
 * Emulation des Anwendungskerns
 */
public final class Mock {
	
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final int MAX_ID = 99;
	private static final int MAX_KUNDEN = 8;
	private static final int MAX_BESTELLUNGEN = 4;
	private static final int MAX_ARTIKEL = 5;
	private static final int JAHR = 2001;
	private static final int MONAT = 0; // bei Calendar werden die Monate von 0 bis 11 gezaehlt
	private static final int TAG = 31;  // bei Calendar die Monatstage ab 1 gezaehlt



	public static AbstractKunde findKundeById(Long id) {
		if (id > MAX_ID) {
			return null;
		}
		
		final AbstractKunde kunde = id % 2 == 1 ? new Privatkunde() : new Firmenkunde();
		kunde.setId(id);
		kunde.setVorname("Vorname" + id);
		kunde.setNachname("Nachname" + id);
		kunde.setGeschlecht(GeschlechtType.WEIBLICH);
		kunde.setEmail("" + id + "@hska.de");
		kunde.setAktualisiert(null);
		
		final GregorianCalendar geburtsdatumCal = new GregorianCalendar(JAHR, MONAT, TAG);
		final Date geburtsdatum = geburtsdatumCal.getTime();
		kunde.setGeburtsdatum(geburtsdatum);
		
		
		final GregorianCalendar erzeugtCal = new GregorianCalendar(JAHR, MONAT, TAG);
		final Date erzeugt = erzeugtCal.getTime();
		kunde.setErzeugt(erzeugt);
		
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);        // andere ID fuer die Adresse
		adresse.setStrasse("Silverline");
		adresse.setHausnr("2");
		adresse.setPlz("12345");
		adresse.setOrt("Testort");
		adresse.setKunde(kunde);
		kunde.setAdresse(adresse);
		
		if (kunde.getClass().equals(Privatkunde.class)) {
			final Privatkunde privatkunde = (Privatkunde) kunde;
			final Set<HobbyType> hobbies = new HashSet<>();
			hobbies.add(HobbyType.LESEN);
			hobbies.add(HobbyType.REISEN);
			privatkunde.setHobbies(hobbies);
		}
		
		return kunde;
	}

	public static List<AbstractKunde> findAllKunden() {
		final int anzahl = MAX_KUNDEN;
		final List<AbstractKunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final AbstractKunde kunde = findKundeById(Long.valueOf(i));
			kunden.add(kunde);			
		}
		return kunden;
	}

	public static List<AbstractKunde> findKundenByNachname(String nachname) {
		final int anzahl = nachname.length();
		final List<AbstractKunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final AbstractKunde kunde = findKundeById(Long.valueOf(i));
			kunde.setNachname(nachname);
			kunden.add(kunde);			
		}
		return kunden;
	}
	
	public static List<AbstractKunde> findKundenByVorname(String vorname) {
		final int anzahl = vorname.length();
		final List<AbstractKunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final AbstractKunde kunde = findKundeById(Long.valueOf(i));
			kunde.setVorname(vorname);
			kunden.add(kunde);			
		}
		return kunden;
	}
	
	public static AbstractKunde findKundeByEmail(String email) {
		if (email.startsWith("x")) {
			return null;
		}
		
		final AbstractKunde kunde = email.length() % 2 == 1 ? new Privatkunde() : new Firmenkunde();
		kunde.setId(Long.valueOf(email.length()));
		kunde.setVorname("Vorname");
		kunde.setNachname("Nachname");
		kunde.setEmail(email);
		
		final GregorianCalendar erzeugtCal = new GregorianCalendar(JAHR, MONAT, TAG);
		final Date erzeugt = erzeugtCal.getTime();
		kunde.setErzeugt(erzeugt);
		
		final Adresse adresse = new Adresse();
		adresse.setId(kunde.getId() + 1);        // andere ID fuer die Adresse
		adresse.setStrasse("Silverline");
		adresse.setHausnr("2");
		adresse.setPlz("12345");
		adresse.setOrt("Testort");
		adresse.setKunde(kunde);
		kunde.setAdresse(adresse);
		
		if (kunde.getClass().equals(Privatkunde.class)) {
			final Privatkunde privatkunde = (Privatkunde) kunde;
			final Set<HobbyType> hobbies = new HashSet<>();
			hobbies.add(HobbyType.LESEN);
			hobbies.add(HobbyType.REISEN);
			privatkunde.setHobbies(hobbies);
		}
		
		return kunde;
	}
	

	public static List<Bestellung> findBestellungenByKundeId(Long kundeId) {
		final AbstractKunde kunde = findKundeById(kundeId);
		
		// Beziehungsgeflecht zwischen Kunde und Bestellungen aufbauen
		final int anzahl = kundeId.intValue() % MAX_BESTELLUNGEN + 1;  // 1, 2, 3 oder 4 Bestellungen
		final List<Bestellung> bestellungen = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Bestellung bestellung = findBestellungById(Long.valueOf(i));
			bestellung.setKunde(kunde);
			bestellungen.add(bestellung);			
		}
		kunde.setBestellungen(bestellungen);
		
		return bestellungen;
	}
	
	public static Bestellposten findBestellpostenById(Long id) {
		Bestellposten posten = new Bestellposten();
		posten.setPositionId(id);
		posten.setMenge((long) 3);
		Artikel artikel = findArtikelById((long) 3);
		posten.setArtikel(artikel);
		posten.setZwischenpreis((long) (artikel.getPreis()*posten.getMenge()));
		return posten;
	}

	public static Bestellung findBestellungById(Long id) {
		if (id > MAX_ID) {
			return null;
		}

		final AbstractKunde kunde = findKundeById(id + 1);  // andere ID fuer den Kunden

		final Bestellung bestellung = new Bestellung();
		bestellung.setId(id);
		bestellung.setKunde(kunde);
		bestellung.setStatus(StatusType.INBEARBEITUNG);
		bestellung.setGesamtpreis(12.99);
		
		List<Bestellposten> posten = new ArrayList<Bestellposten>();
		Bestellposten bp =findBestellpostenById((long)3);
		bp.setBestellung(bestellung);
		posten.add(bp);
		bestellung.setBestellposten(posten);
		//bestellung.setVersion(null);
		bestellung.setBestelldatum(new Date());
		//bestellung.setAktualisiert (new Timestamp(new Date(), null));
		
		
		return bestellung;
	}

	public static AbstractKunde createKunde(AbstractKunde kunde) {
		// Neue IDs fuer Kunde und zugehoerige Adresse
		// Ein neuer Kunde hat auch keine Bestellungen
		final String nachname = kunde.getNachname();
		kunde.setId(Long.valueOf(nachname.length()));
		final String vorname = kunde.getVorname();
		kunde.setId(Long.valueOf(vorname.length()));
		final Timestamp aktualisiert = kunde.getAktualisiert();
		kunde.setAktualisiert(aktualisiert);
		final Adresse adresse = kunde.getAdresse();
		adresse.setId((Long.valueOf(nachname.length())) + 1);
		adresse.setKunde(kunde);
		kunde.setBestellungen(null);
		
		LOGGER.infof("Neuer Kunde: " + kunde);
		return kunde;
	}

	public static void updateKunde(AbstractKunde kunde) {
		LOGGER.infof("Aktualisierter Kunde: " + kunde);
	}
	public static void deleteKunde(AbstractKunde kunde) {
		LOGGER.infof("Geloeschter Kunde: %s", kunde);
	}
	
	
	private Mock() { /**/ }

	public static Bestellung createBestellung(Bestellung bestellung) {
		// TODO Auto-generated method stub
		final Long id = bestellung.getId();
		bestellung.setId(id);
		final StatusType status = bestellung.getStatus();
		bestellung.setStatus(status);
		final Long version = bestellung.getVersion();
		bestellung.setVersion(version);
		final double gesamtpreis = bestellung.getGesamtpreis();
		bestellung.setGesamtpreis(gesamtpreis);
		final Timestamp aktualisiert = bestellung.getAktualisiert();
		bestellung.setAktualisiert(aktualisiert);
		final List<Bestellposten> posten = bestellung.getBestellposten();
		bestellung.setBestellposten(posten);

		return bestellung;
	}
	public static void updateBestellung(Bestellung bestellung) {
		LOGGER.infof("Aktualisierte Bestellung: " + bestellung);
	}

	
	//Mock: Artikel
			public static Artikel findArtikelById(Long id) {
				if (id > MAX_ID) {
					return null;
				}
				final Artikel artikel = new Artikel();
				artikel.setId(id);
				artikel.setBezeichnung("Bezeichung_" + id);
				artikel.setKategorie(KategorieType.BADEZIMMER);
				artikel.setFarbe("blau");
				artikel.setPreis(12.30);
				artikel.setVerfuegbar(true);
				artikel.setErstellt(null);
				artikel.setAktualisiert(null);
				
				return artikel;
			}
			
			public static List<Artikel> findArtikelByKategorie(KategorieType kategorie) {
			final int anzahl = MAX_ARTIKEL;
				final List<Artikel> allArtikel = new ArrayList<>(anzahl);
				for (int i = 1; i <= anzahl; i++) {
					final Artikel artikel = findArtikelById(Long.valueOf(i));
					if (artikel.getKategorie().equals(kategorie)) {
						allArtikel.add(artikel);
					}
				}
				return allArtikel;
			}
			public static List<Artikel> findAllArtikel() {
				final int anzahl = MAX_ARTIKEL;
				final List<Artikel> allArtikel = new ArrayList<>(anzahl);
				for (int i = 1; i <= anzahl; i++) {
					final Artikel artikel = findArtikelById(Long.valueOf(i));
					allArtikel.add(artikel);			
				}
				return allArtikel;
			}
			/////////////////////////////////////
			//System.out.println - LOGGER.infof
			public static void updateArtikel(Artikel artikel) {
				LOGGER.infof("Aktualisierter Artikel: " + artikel);
			
			}
			public static Artikel createArtikel(Artikel artikel) {
				// Neue IDs fuer Artikel 
				final String bezeichnung = artikel.getBezeichnung();
				artikel.setId(Long.valueOf(bezeichnung.length()));

			
				LOGGER.infof("Neuer Artikel: " + artikel);
				return artikel;
			}
			
			//Mock Lieferung
			
			public static Lieferung findLieferungById(Long id) {
				if (id > MAX_ID) {
					return null;
				}
				final Lieferung lieferung = new Lieferung();
				lieferung.setId(id);
				lieferung.setAktuell(new Timestamp(null, null));
				//lieferung.setLieferdatum(new Date(113, 5, 5));
								
				return lieferung;
			}
			public static Lieferung createLieferung(Lieferung lieferung) {
				// Neue IDs fuer Lieferung 
				final Long id = lieferung.getId();
				lieferung.setId(Long.valueOf(id));
				final Timestamp aktuell = lieferung.getAktuell();
				lieferung.setAktuell(aktuell);
				
				LOGGER.infof("Die Lieferung " + id + " wurde aktualisiert am: " + aktuell);
				return lieferung;
			}
			public static void updateLieferung(Lieferung lieferung) {
				LOGGER.infof("Aktualisierte Lieferungung: " + lieferung);
			}
			
			
	
}
