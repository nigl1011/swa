package de.shop.artikelverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.domain.KategorieType;
import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.util.LocaleHelper;
import de.shop.util.NotFoundException;

@Path("/artikel")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped

public class ArtikelResource {

	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpHeaders headers;
	
	@Inject
	private UriHelperArtikel uriHelperArtikel;
	
	@Inject
	private ArtikelService as;
	
	@Inject
	private LocaleHelper localeHelper;
	
	@GET
	@Produces(TEXT_PLAIN)
	@Path("version")
	public String getVersion() {
		return "1.0";
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Artikel findArtikelById(@PathParam("id") Long id, @Context UriInfo uirInfo) {
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final Artikel artikel = as.findArtikelById(id, locale);
		if (artikel == null) {
			throw new NotFoundException("Kein Artikel mit der ID " + id + " gefunden.");
		}
		// URLs innerhalb des gefundenen Artikels anpassen
		uriHelperArtikel.updateUriArtikel(artikel, uriInfo);	
		return artikel;
	}
		
	@GET
	public Collection<Artikel> findArtikelByKategorie(@QueryParam("kategorie") 
	@DefaultValue("") KategorieType kategorie) {
		final Locale locale = localeHelper.getLocale(headers);
		
		Collection<Artikel> allArtikel = null;
		/* Alles löschen, weil Kategorie nie leer ist?!?!
		  if ("".equals(kategorie)) {
			// TODO Anwendungskern statt Mock, Verwendung von Locale
			allArtikel = as.findAllArtikel();
			if (allArtikel.isEmpty()) {
				throw new NotFoundException("Kein Artikel vorhanden.");
			}
		}
		else { */
			// TODO Anwendungskern statt Mock, Verwendung von Locale
		
			allArtikel = as.findArtikelByKategorie(kategorie, locale);
			if (allArtikel.isEmpty()) {
				throw new NotFoundException("Kein Artikel mit der Kategorie " + kategorie + " gefunden.");
			}
		//}
		
		for (Artikel artikel : allArtikel) {
			uriHelperArtikel.updateUriArtikel(artikel, uriInfo);
		}
		
		return allArtikel;
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createArtikel(Artikel artikel) {
		//@SuppressWarnings("unused")
		//final Locale locale = localeHelper.getLocale(headers);

		final Locale locale = localeHelper.getLocale(headers);
		artikel = as.createArtikel(artikel, locale);
		final URI artikelUri = uriHelperArtikel.getUriArtikel(artikel, uriInfo);
		return Response.created(artikelUri).build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateArtikel(Artikel artikel) {
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		as.updateArtikel(artikel, locale);
		return Response.noContent().build();
	}
	
}
