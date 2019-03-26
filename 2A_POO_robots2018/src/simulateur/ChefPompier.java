package simulateur;

import java.util.ArrayList;
import java.util.Iterator;

import carte.Carte;
import carte.Case;
import cheminoptimal.cheminoptimal;
import robot.Robot;

public class ChefPompier {

	/**
	 * La classe chef des pompiers va servir a diriger les differents robots au
	 * travers des incendies
	 */

	private Carte carte;
	private SimulateurBis simulateur;
	private ArrayList<cheminoptimal> chemins = new ArrayList<cheminoptimal>();

	public void assign2Robot() {
		/**
		 * La methode d'affectation des robots. Si un robot est disponible et a un
		 * reservoir non vide il est envoye en intervention Si le roobot est disponible
		 * mais a un reservoir vide, on l'envoie se remplir au point d'eau le plus
		 * proche Dans cette strategie, un incendie est traite e la fois
		 */

		long minimum = 999999999;
		Robot robotMin = carte.getRobots().get(0);

		for (Robot rob : carte.getRobots()) {
			if (rob.isFree(simulateur.getDateSimulation()) && (rob.getVolume() > 0)) {
				this.getChemins().add(rob.getNumeroRobot(), new cheminoptimal(rob));
				if (!carte.getIncendies().isEmpty()) {
					chemins.get(rob.getNumeroRobot()).getShortestTime(carte.getIncendies().get(0));
					if (chemins.get(rob.getNumeroRobot()).getShortestTime(carte.getIncendies().get(0)) != -1 && chemins
							.get(rob.getNumeroRobot()).getShortestTime(carte.getIncendies().get(0)) < minimum) {
						minimum = chemins.get(rob.getNumeroRobot()).getShortestTime(carte.getIncendies().get(0));
						robotMin = rob;
					}
				}
			}
		}
		if (!carte.getIncendies().isEmpty()) {
			chemins.get(robotMin.getNumeroRobot()).travelTo(carte.getIncendies().get(0));
		}
		else {
			simulateur.setSimulationTerminee(true);
		}
		Intervention i = new Intervention(robotMin);
		i.setDuree(
				(long) (Math.min(robotMin.getPosition().getIncendie() / robotMin.getDebit(), robotMin.getVolume() / robotMin.getDebit())));
		simulateur.ajouteEvenement(i);
		if (robotMin.isFree(simulateur.getDateSimulation()) && (robotMin.getVolume() == 0)) {
			robotMin.goRemplir();
		}
	}

	public void assignRobot() {
		/**
		 * La methode d'affectation des robots. Si un robot est disponible et a un
		 * reservoir non vide il est envoye en intervention Si le roobot est disponible
		 * mais a un reservoir vide, on l'envoie se remplir au point d'eau le plus
		 * proche Dans cette strategie, un incendie est traite e la fois
		 */
		for (Robot rob : carte.getRobots()) {
			if (rob.isFree(simulateur.getDateSimulation()) && (rob.getVolume() > 0)) {
				this.getChemins().add(rob.getNumeroRobot(), new cheminoptimal(rob));
				if (!carte.getIncendies().isEmpty()) {
					// On intervient que si y'a un incendie
					chemins.get(rob.getNumeroRobot()).getShortestTime(carte.getIncendies().get(0));
					if (chemins.get(rob.getNumeroRobot()).getShortestTime(carte.getIncendies().get(0)) != -1) {
						chemins.get(rob.getNumeroRobot()).travelTo(carte.getIncendies().get(0));
						Intervention i = new Intervention(rob);
						i.setDuree((long) (Math.min(rob.getPosition().getIncendie() / rob.getDebit(),
								rob.getVolume() / rob.getDebit())));
						simulateur.ajouteEvenement(i);
					}
				}
			} else if (rob.isFree(simulateur.getDateSimulation()) && (rob.getVolume() == 0)) {
				rob.goRemplir();
			}

		}
	}

	public void updateIncendies() {
		/**
		 * Relit la carte pour informer le chef des robots des endroits ou des incendies
		 * sont en cours
		 */
		Iterator<Case> iter = carte.getIncendies().iterator();
		while (iter.hasNext()) {
			Case cur = iter.next();
			if (cur.getIncendie() == 0) {
				iter.remove();
			}
		}
	}

	public void faireLeChef(long pas) {
		/**
		 * Dirige les robots avec la strategie la plus evoluee possible implementee.
		 * Le pas est malheureusement non implemente actuellement
		 */
		updateIncendies();
		assign2Robot();
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public SimulateurBis getSimulateur() {
		return simulateur;
	}

	public void setSimulateur(SimulateurBis simulateur) {
		this.simulateur = simulateur;
	}

	public ArrayList<cheminoptimal> getChemins() {
		return chemins;
	}

}
