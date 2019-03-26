package carte;

import java.util.HashMap;

public class Case {
	public int ligne, colonne;
	private NatureTerrain nature = NatureTerrain.TERRAIN_LIBRE;
	protected int nbLignes;
	protected int nbColonnes;
	private int incendie = 0;
	private int tailleCases;
	protected HashMap <Direction,Case> voisins;

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public NatureTerrain getNature() {
		return nature;
	}
	
	public int getIncendie() {
		return incendie;
	}

	public void setIncendie(int incendie) {
		this.incendie = incendie;
	}


	public boolean voisinExiste(Direction dir) {
		if (dir == Direction.NORD) {
			return this.ligne != 0;
		}

		else if (dir == Direction.SUD) {
			return this.ligne != (this.nbLignes - 1);
		}

		else if (dir == Direction.EST) {
			return this.colonne != (this.nbColonnes - 1);
		}

		else {
			return this.colonne != 0;
		}
	}

	public void setNature(String chaineNature) {
		if (chaineNature.equals("FORET"))
			this.nature=NatureTerrain.FORET;
		else if (chaineNature.equals("EAU"))
			this.nature=NatureTerrain.EAU;
		else if (chaineNature.equals("ROCHE"))
			this.nature=NatureTerrain.ROCHE;
		else if (chaineNature.equals("TERRAIN_LIBRE"))
			this.nature=NatureTerrain.TERRAIN_LIBRE;
		else 
			this.nature=NatureTerrain.HABITAT;
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public void setNbLignes(int nbLignes) {
		this.nbLignes = nbLignes;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public void setNature(NatureTerrain nature) {
		this.nature = nature;
	}

	public HashMap<Direction, Case> getVoisins() {
		return voisins;
	}

	public void setVoisins(HashMap<Direction, Case> voisins) {
		this.voisins = voisins;
	}

	
	
	public int getTailleCases() {
		return tailleCases;
	}

	public void setTailleCases(int tailleCases) {
		this.tailleCases = tailleCases;
	}

	@Override
	public String toString() {
		return "Case [ligne=" + ligne + ", colonne=" + colonne + ", nature=" + nature + ", incendie=" + incendie + "]\n";
	}
	
	public Case getVoisin(Direction dir) {
		return this.getVoisins().get(dir);
	}

	
	
}
