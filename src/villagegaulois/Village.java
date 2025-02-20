package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}

	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int etalLibre = marche.trouverEtalLibre();
		if (etalLibre != -1) {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			etalLibre++;
			return "Le vendeur " + vendeur.getNom() + " vend des fleurs à l'état n°" + etalLibre + ". \n\n";
		}
		return "Aucune étable libre.\n\n";
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etals = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		if (etals.length == 0) {
			return "Il n'y a pas de vendeur qui propose des " + produit + " au marché. \n\n";
		}
		chaine.append("Les vendeur proposnt des " + produit + " sont : \n")
		for (int i = 0; i < etals.length; i++) {
			
		}
		return chaine.toString();
	}
	
	private static class Marche {

		private Etal[] etals;

		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				Etal etal = new Etal();
				etals[i] = etal;
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int etalLibre = -1;
			for (int i = 0; i < etals.length && etalLibre == -1; i++) {
				if (!etals[i].isEtalOccupe()) {
					etalLibre = i;
				}
			}
			return etalLibre;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalAvecProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalAvecProduit++;
				}
			}
			Etal etalAvecProduit[] = new Etal[nbEtalAvecProduit];
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalAvecProduit[j] = etals[i];
					j++;
				}
			}
			return etalAvecProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalDuVendeur = new Etal();
			for (int i = 0; i < etals.length && !etalDuVendeur.isEtalOccupe(); i++) {
				if (etals[i].getVendeur() == gaulois) {
					etalDuVendeur = etals[i];
				}
			}
			return etalDuVendeur;
		}
		
		private String afficherMarche() {
			StringBuilder affichage = new StringBuilder();
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					affichage.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide > 0) {
				affichage.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return affichage.toString();
		}
		
	}

}