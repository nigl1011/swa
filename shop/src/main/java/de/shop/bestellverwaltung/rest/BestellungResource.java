package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;

//import com.jayway.restassured.response.Response;
import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.bestellverwaltung.domain.Bestellposten;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.service.BestellungServiceImpl;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.rest.UriHelperKunde;
import de.shop.kundenverwaltung.service.KundeService;
import de.shop.util.LocaleHelper;
import de.shop.util.NotFoundException;

@Path("/bestellungen")
@Produces(APPLICATION_JSON)
@RequestScoped
@Consumes
public class BestellungResource {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpHeaders headers;
	
	@Inject
	private ArtikelService as;
	
	@Inject
	private UriHelperBestellung uriHelperBestellung;
	
	@Inject
	private UriHelperKunde uriHelperKunde;
	
	@Inject
	private LocaleHelper localeHelper;
	
	@Inject
	private BestellungServiceImpl bs;
	@Inject
	private KundeService ks;
	
	@GET
	@Produces(TEXT_PLAIN)
	@Path("version")
	public String getVersion() {
		return "1.0";
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Bestellung findBestellungById(@PathParam("id") Long id) {
		final Locale locale = localeHelper.getLocale(headers);
		final Bestellung bestellung = bs.findBestellungById(id,locale);
		if (bestellung == null) {
			final String msg = "Kein Kunde gefunden mit der ID " + id;
			throw new NotFoundException(msg);
		}
		
		// URLs innerhalb der gefundenen Bestellung anpassen
		uriHelperBestellung.updateUriBestellung(bestellung, uriInfo);
		return bestellung;
	}
	
	
	// TODO findLieferungenByBestellungId noch nicht implementiert
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createBestellung(Bestellung bestellung) {
		// Schluessel des Kunden extrahieren
		final String kundeUriStr = bestellung.getKundeUri().toString();
		int startPos = kundeUriStr.lastIndexOf('/') + 1;
		final String kundeIdStr = kundeUriStr.substring(startPos);
		Long kundeId = null;
		try {
			kundeId = Long.valueOf(kundeIdStr);
		}
		catch (NumberFormatException e) {
			throw new NotFoundException("Kein Kunde vorhanden mit der ID " + kundeIdStr, e);
		}
		
		// persistente Artikel ermitteln
		final Collection<Bestellposten> bestellposten = bestellung.getBestellposten();
		final List<Long> artikelIds = new ArrayList<>(bestellposten.size());
		for (Bestellposten bp : bestellposten) {
			final String artikelUriStr = bp.getArtikelUri().toString();
			startPos = artikelUriStr.lastIndexOf('/') + 1;
			final String artikelIdStr = artikelUriStr.substring(startPos);
			Long artikelId = null;
			try {
				artikelId = Long.valueOf(artikelIdStr);
			}
			catch (NumberFormatException e) {
				// Ungueltige Artikel-ID: wird nicht beruecksichtigt
				continue;
			}
			artikelIds.add(artikelId);
		}
		
		if (artikelIds.isEmpty()) {
			// keine einzige gueltige Artikel-ID
			final StringBuilder sb = new StringBuilder("Keine Artikel vorhanden mit den IDs: ");
			for (Bestellposten bp : bestellposten) {
				final String artikelUriStr = bp.getArtikelUri().toString();
				startPos = artikelUriStr.lastIndexOf('/') + 1;
				sb.append(artikelUriStr.substring(startPos));
				sb.append(' ');
			}
			throw new NotFoundException(sb.toString());
		}

		final Collection<Artikel> gefundeneArtikel = as.findArtikelByIds(artikelIds);
		if (gefundeneArtikel.isEmpty()) {
			throw new NotFoundException("Keine Artikel vorhanden mit den IDs: " + artikelIds);
		}
		
		// Bestellpositionen haben URLs fuer persistente Artikel.
		// Diese persistenten Artikel wurden in einem DB-Zugriff ermittelt (s.o.)
		// Fuer jede Bestellposition wird der Artikel passend zur Artikel-URL bzw. Artikel-ID gesetzt.
		// Bestellpositionen mit nicht-gefundene Artikel werden eliminiert.
		int i = 0;
		final List<Bestellposten> neueBestellpositionen = new ArrayList<>(bestellposten.size());
		for (Bestellposten bp : bestellposten) {
			// Artikel-ID der aktuellen Bestellposition (s.o.):
			// artikelIds haben gleiche Reihenfolge wie bestellpositionen
			final long artikelId = artikelIds.get(i++);
			
			// Wurde der Artikel beim DB-Zugriff gefunden?
			for (Artikel artikel : gefundeneArtikel) {
				if (artikel.getId().longValue() == artikelId) {
					// Der Artikel wurde gefunden
					bp.setArtikel(artikel);
					neueBestellpositionen.add(bp);
					break;					
				}
			}
		}
		bestellung.setBestellpositionen(neueBestellpositionen);
		
		final Locale locale = localeHelper.getLocale(headers);
		bestellung = bs.createBestellung(bestellung, kundeId, locale);

		final URI bestellungUri = uriHelperBestellung.getUriBestellung(bestellung, uriInfo);
		final Response response = Response.created(bestellungUri).build();
		LOGGER.fatal(bestellungUri.toString());
		
		return response;
	}
	
	/*@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateBestellung(Bestellung bestellung, AbstractKunde kunde) {
		final Locale locale = localeHelper.getLocale(headers);
		
		bs.updateBestellung(bestellung, locale);
		return Response.noContent().build();
	}*/
	
}
