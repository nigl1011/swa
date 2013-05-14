package de.shop.bestellverwaltung.service;

import java.util.List;
import java.util.Locale;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.AbstractKunde;


public interface BestellungService {
	Bestellung findBestellungById(Long id, Locale locale);
	List<Bestellung> findBestellungenByKundeId(Long kundeId, Locale locale);
	Bestellung createBestellung(Bestellung bestellung, Locale locale);
	Bestellung updateBestellung(Bestellung bestellung, AbstractKunde kunde, Locale locale); //nicht in denem ihrs
}
