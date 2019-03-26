package robot;

import java.util.ArrayList;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import cheminoptimal.cheminoptimal;
import simulateur.Remplissage;

public class RobotAPattes extends Robot {

	double debit = 10;
	double vitesse = 30;
	int volume = 1;

	public RobotAPattes(Carte carte) {
		super(carte);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void remplirReservoir() {
		// Le Robot à Pattes n'a jamais besoin de se remplir
	}

	@Override
	public long dureeRemplirReservoir() {
		return 0;
	}

	@Override
	public void goRemplir() {
		//Inutile le robot n'a pas de besoin de se remplir	
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		if (nature == NatureTerrain.ROCHE) {
			return 10;
		} else {
			return 30;
		}
	}

	@Override
	public boolean canMove(Direction dir) {
		// Cette fonction vérifie que le robot puisse aller là ou il veut au niveau de
		// la nature du terrain
		Case dest = position.getVoisin(dir);
		NatureTerrain natureDest = dest.getNature();
		switch (natureDest) {
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
		if ((naturePos == NatureTerrain.ROCHE) && (natureDest != NatureTerrain.ROCHE)) {
			this.setVitesse(30);
		}
		if ((naturePos != NatureTerrain.ROCHE) && (natureDest == NatureTerrain.ROCHE)) {
			this.setVitesse(30);
		}

	}

}
