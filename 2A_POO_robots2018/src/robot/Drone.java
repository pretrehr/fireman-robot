package robot;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import cheminoptimal.cheminoptimal;
import simulateur.Remplissage;

public class Drone extends Robot {
	double vitesse = 100;
	double debit = 10000 / 30;
	int reservoirmax = 10000;

	public Drone(Carte carte) {
		super(carte);
	}

	@Override
	public void goRemplir() {
		Case meilleureCase = this.getCarte().getEau().get(0);
		long meilleurTemps;
		long temps;
		cheminoptimal co = new cheminoptimal(this);
		meilleurTemps = co.getShortestTime(meilleureCase);
		temps = meilleurTemps;
		for (Case caseEau : this.getCarte().getEau()) {
			temps = co.getShortestTime(caseEau);
			if (temps != -1 && temps < meilleurTemps) {
				meilleureCase = caseEau;
				meilleurTemps = temps;
			}
		}
		co.travelTo(meilleureCase);
		this.getSimulateur().ajouteEvenement(new Remplissage(this));
	}

	@Override
	public long dureeRemplirReservoir() {
		return 30 * 60;
	}

	@Override
	public void remplirReservoir() {
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			volume = reservoirmax;
		}
		this.incrementeDateWhereFree(dureeRemplirReservoir());
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		return vitesse;
	}

	public void setVitesse(double vitesse) {
		if (vitesse > 150) {
			vitesse = 150;
		}
		this.vitesse = vitesse;
	}

	@Override
	public boolean canMove(Direction dir) {
		// Le drone peut se deplacer sur tous les terrains
		return true;
	}

	@Override
	public boolean canMoveFrom(Case depart, Direction dir) {
		// Le drone peut se deplacer sur tous les terrains
		return true;
	}

	@Override
	public void modifVitesse(Direction dir) {
		// Ne fait rien, la vitesse du drone ne dépend pas du terrain

	}

	public int getReservoirmax() {
		return reservoirmax;
	}

}
