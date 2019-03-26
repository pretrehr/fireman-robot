package carte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import robot.Robot;

public class Carte implements Cloneable{
	private int tailleCases;
	// en km
	private int tailleReelleCases = 1;
	private int nbLignes;
	private int nbColonnes;
	private int nbIncendies = 0;
	private int nbRobots = 0;
	private ArrayList<Robot> robots;
	private Case[] map;
	private ArrayList <Case> eau = new ArrayList<Case>();
	private ArrayList <Case> incendies = new ArrayList<Case>();

	public Carte(int nbL, int nbC) {
		this.nbLignes = nbL;
		this.nbColonnes = nbC;
		this.map = new Case[nbL * nbC];
		this.robots = new ArrayList<Robot>();
		for (int i = 0; i < nbL; i++) {
			for (int j = 0; j < nbC; j++) {
				this.map[i * nbC + j] = new Case();
				this.map[i * nbC + j].setLigne(i);
				this.map[i * nbC + j].setColonne(j);
				this.map[i * nbC + j].setNbLignes(nbL);
				this.map[i * nbC + j].setNbColonnes(nbC);
				this.map[i * nbC + j].setTailleCases(tailleCases);
				this.map[i * nbC + j].setVoisins(new HashMap<Direction, Case>());
			}
		}
		for (Case cur : map) {
			int i = cur.getLigne();
			int j = cur.getColonne();
			for (Direction dir : Direction.values()) {
				if (cur.voisinExiste(dir)) {
					if (dir == Direction.NORD) {
						cur.getVoisins().put(dir, this.getCase(i - 1, j));
					}

					else if (dir == Direction.SUD) {
						cur.getVoisins().put(dir, this.getCase(i + 1, j));
					}

					else if (dir == Direction.EST) {
						cur.getVoisins().put(dir, this.getCase(i, j + 1));
					}

					else if (dir == Direction.OUEST) {
						cur.getVoisins().put(dir, this.getCase(i, j - 1));
					}

				}
			}
		}
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public Case[] getMap() {
		return map;
	}

	public Case getCase(int ligne, int colonne) {
		return this.map[ligne * this.nbColonnes + colonne];
	}

	public int getTailleCases() {
		return tailleCases;
	}

	public void setTailleCases(int tailleCases) {
		this.tailleCases = tailleCases;
		for (Case cur : map) {
			cur.setTailleCases(tailleCases);
		}
	}

	public void setNbLignes(int nbLignes) {
		this.nbLignes = nbLignes;
	}

	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}

	public void setMap(Case[] map) {
		this.map = map;
	}

	public int getNbIncendies() {
		return nbIncendies;
	}

	public void setNbIncendies(int nbIncendies) {
		this.nbIncendies = nbIncendies;
	}

	public int getNbRobots() {
		return nbRobots;
	}

	public void setNbRobots(int nbRobots) {
		this.nbRobots = nbRobots;
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}

	public void setRobots(ArrayList<Robot> robots) {
		this.robots = robots;
	}

	public ArrayList<Case> getEau() {
		return eau;
	}


	public ArrayList<Case> getIncendies() {
		return incendies;
	}

	public void setIncendies(ArrayList<Case> incendies) {
		this.incendies = incendies;
	}

	public int getTailleReelleCases() {
		return tailleReelleCases;
	}

	public Object clone() {
		Object o = null;
		try {
			// On recupere l'instance e renvoyer par l'appel de la 
			// methode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implementons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return o;
	}

	@Override
	public String toString() {
		return "Carte [map=" + Arrays.toString(map) + "]\n\n" + "nbrobots" + nbRobots + "\n liste robots: \n"
				+ robots.toString();
	}



}
