package de.shop.lieferverwaltung.rest;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.rest.UriHelperBestellung;
import de.shop.lieferverwaltung.domain.Lieferung;


@ApplicationScoped
public class UriHelperLieferung {
	@Inject
	private UriHelperBestellung uriHelperBestellung;
	
	public void updateUriLieferung(Lieferung lieferung, UriInfo uriInfo) {
		// URL fuer Bestellung setzen
		final Bestellung bestellung = lieferung.getBestellung();
		if (bestellung != null) {
			final URI bestellungUri = uriHelperBestellung.getUriBestellung(lieferung.getBestellung(), uriInfo);
			lieferung.setBestellungUri(bestellungUri);
						//lieferung.setBestellungUri(bestellungUri);
		}
		
	}

	public URI getUriLieferung(Lieferung lieferung, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
		                             .path(LieferungResource.class)
		                             .path(LieferungResource.class, "findLieferungById");
		final URI uri = ub.build(lieferung.getId());
		return uri;
	}
}