package de.shop.artikelverwaltung.service;

import java.util.Locale;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.Log;
import de.shop.util.MockService;

@MockService
@Log
public class ArtikelServiceMock extends ArtikelService {
	private static final long serialVersionUID = -2919310633845009282L;

	@Override
	public Artikel findArtikelById(Long id, Locale locale) {
		final Artikel artikel = new Artikel();
		artikel.setId(id);
		artikel.setBezeichnung("Bezeichnung_" + id + "_Mock");
		return artikel;
	}
}
