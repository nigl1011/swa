package de.shop.bestellverwaltung.rest;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Lieferung;
import de.shop.bestellverwaltung.rest.UriHelperBestellung;


@ApplicationScoped
public class UriHelperLieferung {
	@Inject
	private UriHelperBestellung uriHelperBestellung;
	
	public void updateUriLieferung(Lieferung lieferung, UriInfo uriInfo) {
		// URL fuer Bestellung setzen
		final Bestellung bestellung = lieferung.getBestellungs_ID();
		if (bestellung != null) {
			final URI bestellungUri = uriHelperBestellung.getUriBestellung(lieferung.getBestellungs_ID(), uriInfo);
			// TODO lieferung.setBestellungUri(bestellungUri)
						//lieferung.setBestellungUri(bestellungUri);
		}
		
	}

	public URI getUriLieferung(Lieferung lieferung, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
		                             .path(LieferungResource.class)
		                             .path(LieferungResource.class, "findLieferungById");
		final URI uri = ub.build(bestellung.getBestellungs_ID());
		return uri;
	}
}