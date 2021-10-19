package it.prova.gestioneordinijpamaven.test;

import java.util.List;

import it.prova.gestioneordinijpamaven.dao.EntityManagerUtil;
import it.prova.gestioneordinijpamaven.model.Articolo;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;
import it.prova.gestioneordinijpamaven.service.MyServiceFactory;
import it.prova.gestioneordinijpamaven.service.articolo.ArticoloService;
import it.prova.gestioneordinijpamaven.service.categoria.CategoriaService;
import it.prova.gestioneordinijpamaven.service.ordine.OrdineService;

public class GestioneOrdiniTest {

	public static void main(String[] args) {

		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();

		try {
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			testInserimentoNuovoOrdine(ordineServiceInstance);
			System.out.println();
			testAggiornamentoERimozioneOrdine(ordineServiceInstance);
			System.out.println();

			testInserisciArticoloCollegoAllOrdine(ordineServiceInstance, articoloServiceInstance);
			System.out.println();
			testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo(articoloServiceInstance, categoriaServiceInstance);
			System.out.println();
			testCreazioneCategoriaArticoloOrdineInUnColpo(ordineServiceInstance, articoloServiceInstance,
					categoriaServiceInstance);
			System.out.println();
			testCercaOrdiniPerUnaCategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			System.out.println();
			testCercaArticoliByOrdine(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			System.out.println();
			testCalcolaSommaArticoloDiCategoria(articoloServiceInstance, categoriaServiceInstance,
					ordineServiceInstance);
			System.out.println();

			System.out.println(
					"**************************** fine batteria di test **********************************************");
			System.out.println(
					"*************************************************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	// crea ORDINE LO AGGIORNO E LO RIMUOVO

	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println("######################## testInserimentoNuovoOrdine inizio ########################");
//		creo un nuovo ordine
		Ordine ordineInstance = new Ordine("ORDINE 1", "ORDINE_1");
//	 	inserisco l'ordine e controllo che sia stato inserito
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoOrdine fallito ");
		System.out.println("##################### testInserimentoNuovoOrdine fine: PASSED ########################");
	}

	private static void testAggiornamentoERimozioneOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println("##################### testEliminaOrdine inizio #######################################");

		Ordine ordine2 = new Ordine("ORDINE 2", "ORDINE_2");
		ordineServiceInstance.inserisciNuovo(ordine2);
		if (ordine2.getId() == null)
			throw new RuntimeException("testInserimentoNuovoOrdine fallito ");

		ordine2.setNoemDestinatario("ORDINE 3");
		ordine2.setNoemDestinatario("ORDINE_3");
		ordineServiceInstance.aggiorna(ordine2);
		System.out.println("############################# ORDINE AGGIORNATO ########################################");
		ordineServiceInstance.rimuovi(ordine2);
		Ordine ordineReloaded = ordineServiceInstance.caricaSingoloOrdine(ordine2.getId());
		if (ordineReloaded != null)
			throw new RuntimeException("testInserimentoNuovoOrdine fallito: genere e articolo non collegati ");
		System.out.println(
				"##################### testEliminaOrdine fine: PASSED ############################################");
	}
	// crea ARTICOLO COLLEGATO ORDINE

	private static void testInserisciArticoloCollegoAllOrdine(OrdineService ordineService,
			ArticoloService articoloService) throws Exception {
		System.out.println(
				"#################### testInserisciArticoloCollegoAllOrdine inizio ##########################");
		List<Ordine> listaOrdiniPresenti = ordineService.listAllOrdini();
		if (listaOrdiniPresenti.isEmpty())
			throw new RuntimeException("testInserisciArticolo fallito: non ci sono ordini a cui collegarci ");
		// creo un nuovo articolo e ci collego l'ordine
		Articolo nuovoArticolo = new Articolo("ARTICOLO_2", 2);
		nuovoArticolo.setOrdine(listaOrdiniPresenti.get(0));
		articoloService.inserisciNuovo(nuovoArticolo);
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testInserisciArticolo fallito ");
		if (nuovoArticolo.getOrdine() == null)
			throw new RuntimeException("testInserisciArticolo fallito: non ha collegato l'ordine ");
		System.out.println(
				"#################### testInserisciArticoloCollegoAllOrdine fine: PASSED ####################");
		System.out.println(
				"#################### testRimozione inizio ##################################################");
		articoloService.rimuovi(nuovoArticolo);
		Articolo articoloReloaded = articoloService.caricaSingoloElemento(nuovoArticolo.getId());
		if (articoloReloaded != null)
			throw new RuntimeException("testRimozione fallito: non è stato possibile rimuovere il l'articolo ");

		System.out.println(
				"#################### testRimozione fine: PASSED ############################################");
	}

	// crea CATEGORIA COLLEGATO ARTICOLO

	private static void testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo(
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(".......testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo inizio.............");

		Articolo articoloInstanceX = new Articolo("Articolo2", 550);
		Categoria categoriaX = new Categoria("Categoria 1", "categoria_1");
		articoloServiceInstance.creaECollegaArticoloECategoria(articoloInstanceX, categoriaX);
		if (articoloInstanceX.getId() == null)
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: articolo non inserito ");
		if (categoriaX.getId() == null)
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: genere non inserito ");
		// ricarico eager per forzare il test
		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategoria(articoloInstanceX.getId());
		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: genere e articolo non collegati ");
		System.out.println(
				"#################### testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fine: PASSED ####################");
	}

	// crea CATEGORIA COLLEGATO ARTICOLO COLLEGATO ORDINE TODO

	private static void testCreazioneCategoriaArticoloOrdineInUnColpo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(
				"################ testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo inizio ##################");
		Ordine ordineX = new Ordine("ORDINE 2", "ORDINE_2");
		ordineServiceInstance.inserisciNuovo(ordineX);
		ordineServiceInstance.caricaSingoloOrdine(ordineX.getId());
		Articolo articoloInstanceX = new Articolo("ARTICOLO_2", 20);
		articoloInstanceX.setOrdine(ordineX);
		Categoria categoriaX = new Categoria("Categoria 1", "categoria_1");
		articoloServiceInstance.creaECollegaArticoloECategoria(articoloInstanceX, categoriaX);
		if (ordineX.getId() == null)
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: ordine non inserito ");
		if (articoloInstanceX.getId() == null)
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: articolo non inserito ");
		if (categoriaX.getId() == null)
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: genere non inserito ");
		// ricarico eager per forzare il test
		Ordine ordineReloaded = ordineServiceInstance.caricaSingoloOrdine(ordineX.getId());
		if (ordineReloaded.getArticoli() == null)
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: genere e articolo non collegati ");
		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategoria(articoloInstanceX.getId());
		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException(
					"testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fallito: genere e articolo non collegati ");
		System.out.println(
				"#################### testCreazioneCategoriaECollegamentoArticoloInUnSoloColpo fine: PASSED ####################");

	}

	// ######################################### TEST NON CRUDE
	// ####################################################################################

	private static void testCercaOrdiniPerUnaCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out
				.println("##################### testStampaOrdiniPerUnaCategoria inizio ##############################");

		Ordine ordine50 = new Ordine("ORDINE 50", "ORDINE_50");
		ordineServiceInstance.inserisciNuovo(ordine50);
		Articolo articolo50 = new Articolo("ARTICOLO_50", 50);
		articolo50.setOrdine(ordine50);
		Categoria categoria50 = new Categoria("CATEGORIA 50", "CATEGORIA_50");

		articoloServiceInstance.creaECollegaArticoloECategoria(articolo50, categoria50);

		if (ordineServiceInstance.caricaOrdiniEffettuatiPerCategoria(categoria50).size() == 0)
			throw new RuntimeException("testStampaOrdiniPerUnaCategoria fallito: ricerca non avvenuta con successo ");
		System.out.println("nella Categoria ci sono N ordini: "
				+ ordineServiceInstance.caricaOrdiniEffettuatiPerCategoria(categoria50).size());
		System.out.println("##################### testStampaOrdiniPerUnaCategoria fine: PASSED #####################");
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------

	private static void testCercaArticoliByOrdine(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println("############## testCercaArticoliByOrdine inizio ############");

		Articolo articolo60 = new Articolo("ARTICOLO 60", 60);
		Categoria categoria60 = new Categoria("CATEGORIA 60", "CATEGORIA_60");
		Ordine ordine60 = new Ordine("ORDINE 60", "ORDINE_60");
		ordineServiceInstance.inserisciNuovo(ordine60);
		articolo60.setOrdine(ordine60);
		articoloServiceInstance.creaECollegaArticoloECategoria(articolo60, categoria60);
		System.out.println(categoriaServiceInstance.cercaTuttiArticoliByOrdine(ordine60).size());
		if (categoriaServiceInstance.cercaTuttiArticoliByOrdine(ordine60).size() == 0)
			throw new RuntimeException("testCercaTuttiByOrdineArticolo fallito: ricerca non avvenuta con successo ");

		System.out.println("################ testCercaArticoliByOrdine fine: PASSED #############################");
	}

// ----------------------------------------------------------------------------------------------------------------------------------------------
	private static void testCalcolaSommaArticoloDiCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(
				"################### testCalcolaSommaarticoloDiCategoria inizio ##############################");

		Articolo articolo80 = new Articolo("ARTICOLO 80", 80);

		Categoria categoria80 = new Categoria("CATEGORIA 80", "CATEGORIA_80");

		articoloServiceInstance.creaECollegaArticoloECategoria(articolo80, categoria80);

		System.out.println(articoloServiceInstance.calcolaSommaPrezziCategoria(categoria80));
		if (articoloServiceInstance.calcolaSommaPrezziCategoria(categoria80) == null)
			throw new RuntimeException(
					"testCalcolaSommaarticoloDiCategoria fallito: ricerca non avvenuta con successo ");

		System.out.println("################### testCalcolaSommaarticoloDiCategoria fine: PASSED ###################");
	}
}// class