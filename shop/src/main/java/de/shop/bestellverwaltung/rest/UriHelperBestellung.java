package de.shop.bestellverwaltung.rest;


import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.rest.UriHelperArtikel;
import de.shop.bestellverwaltung.domain.Bestellposten;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.rest.UriHelperKunde;
import de.shop.util.Log;


@ApplicationScoped
@Log
public class UriHelperBestellung {
	@Inject
	private UriHelperKunde uriHelperKunde;
	
	@Inject
	private UriHelperArtikel uriHelperArtikel;
	
	public void updateUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
		// URL fuer Kunde setzen
		final AbstractKunde kunde = bestellung.getKunde();
		if (kunde != null) {
			
			final URI kundeUri = uriHelperKunde.getUriKunde(bestellung.getKunde(), uriInfo);
			bestellung.setKundeUri(kundeUri);
		}
		
		// URLs fuer Artikel in den Bestellpositionen setzen
		final List<Bestellposten> bestellposten = bestellung.getBestellposten();
		if (bestellposten != null && !bestellposten.isEmpty()) {
			for (Bestellposten bp : bestellposten) {
				final URI artikelUri = uriHelperArtikel.getUriArtikel(bp.getArtikel(), uriInfo);
				bp.setArtikelUri(artikelUri);
			}
		}
		
		// URL fuer Lieferungen setzen
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
                                     .path(BestellungResource.class)
                                     .path(BestellungResource.class, "findLieferungenByBestellungId");
		final URI uri = ub.build(bestellung.getId());
		bestellung.setLieferungenUri(uri);
		
	}

	public URI getUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
		                             .path(BestellungResource.class)
		                             .path(BestellungResource.class, "findBestellungById");
		final URI uri = ub.build(bestellung.getId());
		return uri;
	}
}