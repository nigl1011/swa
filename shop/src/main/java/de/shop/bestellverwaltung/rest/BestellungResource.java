package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;
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









import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.service.BestellungServiceImpl;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.service.KundeService;
import de.shop.util.LocaleHelper;
import de.shop.util.NotFoundException;

@Path("/bestellungen")
@Produces(APPLICATION_JSON)
@RequestScoped
@Consumes
public class BestellungResource {
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpHeaders headers;

	@Inject
	private UriHelperBestellung uriHelperBestellung;

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
			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
		}
		
		// URLs innerhalb der gefundenen Bestellung anpassen
		uriHelperBestellung.updateUriBestellung(bestellung, uriInfo);
		return bestellung;
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createBestellung(Bestellung bestellung) {
		//@SuppressWarnings("unused")
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final String kundeUri = bestellung.getKundeUri().toString();
		final int pos = kundeUri.indexOf("/kunden/")+8;
		final String kundeIdString = kundeUri.substring(pos);
		AbstractKunde kunde = ks.findKundeById(Long.valueOf(kundeIdString),locale);
		bestellung.setKunde(kunde);
		
		bestellung = bs.createBestellung(bestellung,locale);
		final URI bestellungUri = uriHelperBestellung.getUriBestellung(bestellung, uriInfo);
		return Response.created(bestellungUri).build();
		
	}
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateBestellung(Bestellung bestellung,AbstractKunde kunde) {
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		bs.updateBestellung(bestellung,kunde,locale);
		return Response.noContent().build();
	}
	
}
