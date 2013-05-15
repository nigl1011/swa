package de.shop.lieferverwaltung.service;

import java.util.Locale;

import de.shop.lieferverwaltung.domain.Lieferung;
import de.shop.util.Log;
import de.shop.util.MockService;

@MockService
@Log
public class LieferServiceMock extends LieferService {
	private static final long serialVersionUID = 1L;

	@Override
	public Lieferung findLieferungById(Long id, Locale locale) {
		final Lieferung lieferung = new Lieferung();
		lieferung.setId(id);
		return lieferung;
	}
}
