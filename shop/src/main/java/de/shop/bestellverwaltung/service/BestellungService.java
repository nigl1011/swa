package de.shop.bestellverwaltung.service;

import java.util.List;
import java.util.Locale;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.AbstractKunde;

public interface BestellungService {

	Bestellung findBestellungById(Long id);
	Bestellung findBestellungByIdMitLieferungen(Long id);
	AbstractKunde findKundeById(Long id);
	List<Bestellung> findBestellungenByKunde(AbstractKunde kunde);
	Bestellung createBestellung(Bestellung bestellung, Long kundeId, Locale locale);
	Bestellung createBestellung(Bestellung bestellung, AbstractKunde kunde, Locale locale);
	List<Artikel> ladenhueter(int anzahl);
}