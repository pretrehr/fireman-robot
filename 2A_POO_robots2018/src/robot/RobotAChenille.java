package robot;
import java.util.ArrayList;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import cheminoptimal.cheminoptimal;
import simulateur.Remplissage;

public class RobotAChenille extends Robot {
	
	int reservoirmax = 2000;
	double debit = 100/5;
	double vitesse = 60;
	
	
	public RobotAChenille(Carte carte) {
		super(carte);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		if (nature.equals(NatureTerrain.FORET)) {
			return vitesse/2;
		}
		else {
			return vitesse;
		}
	}

	@Override
	public void remplirReservoir() {
	    for(Case voisin : position.getVoisins().values()){
	    	if (voisin.getNature() == NatureTerrain.EAU) {
	    		volume = reservoirmax;
				this.incrementeDateWhereFree(dureeRemplirReservoir());
	      }
	    
		
	}
	}
	
	

	@Override
	public void goRemplir() {
		ArrayList <Case> voisinsEau = new ArrayList<Case>();
		Direction directions[] = { Direction.NORD, Direction.SUD, Direction.EST, Direction.OUEST };
		for (Case eau:this.getCarte().getEau()) {
			for (Direction dir:directions) {
				if (eau.voisinExiste(dir) && eau.getVoisin(dir).getNature() != NatureTerrain.EAU) {
					voisinsEau.add(eau.getVoisin(dir));
				}
			}
		}
		Case meilleureCase = voisinsEau.get(0);
		long meilleurTemps;
		long temps;
		cheminoptimal co = new cheminoptimal(this);
		meilleurTemps = co.getShortestTime(meilleureCase);
		temps = meilleurTemps;
		for (Case voisinEau : voisinsEau) {
			temps = co.getShortestTime(voisinEau);
			if (temps !=-1 && temps < meilleurTemps) {
				meilleureCase = voisinEau;
				meilleurTemps = temps;
			}
		}
		co.travelTo(meilleureCase);
//		System.out.println("meilleure case = "+meilleureCase);
		this.getSimulateur().ajouteEvenement(new Remplissage(this));		
	}

	@Override
	public long dureeRemplirReservoir() {
		return 5*60;
	}

	@Override
	public void setVitesse(double vitesse) {
		if (vitesse > 80) {
			vitesse = 80;
		}
		super.setVitesse(vitesse);
	}

	@Override
	public boolean canMove(Direction dir) {
		// Cette fonction vérifie que le robot puisse aller là  ou il veut
		Case dest = position.getVoisin(dir);
		NatureTerrain natureDest = dest.getNature();
		switch (natureDest) {
		case ROCHE:
			return false;
		case EAU:
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean canMoveFrom(Case depart, Direction dir) {
		// Cette fonction vérifie que le robot puisse aller là  ou il veut à partir d'une case donnée
		Case dest = depart.getVoisin(dir);
		NatureTerrain natureDest = dest.getNature();
		switch (natureDest) {
		case ROCHE:
			return false;
		case EAU:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void modifVitesse(Direction dir) {
		Case dest = position.getVoisin(dir);
		NatureTerrain natureDest = dest.getNature();
		NatureTerrain naturePos = position.getNature();
		if ((naturePos == NatureTerrain.FORET)&&(natureDest!=NatureTerrain.FORET)){
			this.setVitesse(vitesse*2);
		}
		if ((naturePos != NatureTerrain.FORET)&&(natureDest==NatureTerrain.FORET)){
			this.setVitesse(vitesse/2);
		}
	}
	
}
