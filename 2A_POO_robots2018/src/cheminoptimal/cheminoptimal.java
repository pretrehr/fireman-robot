package cheminoptimal;

import java.util.Stack;

import carte.Case;
import carte.Direction;
import robot.Robot;
import simulateur.Deplacement;

/**
 *
 * Cette classe sert a calculer les chemins optimaux pour un robot
 *
 */

public class cheminoptimal {

	/** attribut necessaires pour le calcul du plus court chemin **/

	private Robot robot;

	/** calcul du plus court chemin **/

	// initialisation des elements utiles a l'algorithme

	private int coordCaseDepart[] = new int[2];
	private int n;
	private int m;
	private long[] tabRechercheTemps;

	private Direction directions[] = { Direction.NORD, Direction.SUD, Direction.EST, Direction.OUEST };
	private Direction tabRecherchePrecedent[];

	public cheminoptimal(Robot robot) {
		super();
		this.robot = robot;

		this.coordCaseDepart[0] = robot.getPositionSimulee().getLigne();
		this.coordCaseDepart[1] = robot.getPositionSimulee().getColonne();
		this.n = robot.getCarte().getNbLignes();
		this.m = robot.getCarte().getNbColonnes();
		this.tabRechercheTemps = new long[n * m];
		this.tabRecherchePrecedent = new Direction[n * m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.tabRechercheTemps[i * m + j] = -1;
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.tabRecherchePrecedent[i * m + j] = Direction.SUD;
			}
		}
		this.tabRechercheTemps[coordCaseDepart[0] * m + coordCaseDepart[1]] = 0;
	}

	private Stack<int[]> aExplorer = new Stack<int[]>();
	{
		aExplorer.push(coordCaseDepart);
	}

	private void calcul() {
		/**
		 *
		 * Utilisation de l'algorithme de Dijkstra pour remplir tabRechercheTemps et
		 * tabRecherchePrecedent. On fait en sorte de ne pas appliquer ce calcul plus
		 * d'une fois par objet CheminOptimal.
		 *
		 */
		while (!aExplorer.empty()) {
			Stack<int[]> newAExplorer = new Stack<int[]>();
			while (!aExplorer.empty()) {
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
					}
				}
				int[] current = aExplorer.pop();
				double vitesse = robot.getVitesse(robot.getCarte().getCase(current[0], current[1]).getNature());
				for (Direction dir : directions) {
					if (robot.getCarte().getCase(current[0], current[1]).getVoisins().containsKey(dir)
							&& robot.canMoveFrom(robot.getCarte().getCase(current[0], current[1]), dir)) {
						Case voisin = robot.getCarte().getCase(current[0], current[1]).getVoisin(dir);
						int coord[] = { voisin.getLigne(), voisin.getColonne() };
						if (tabRechercheTemps[coord[0] * m + coord[1]] == -1) {
							tabRechercheTemps[coord[0] * m + coord[1]] = (long) (3600 / vitesse
									+ tabRechercheTemps[current[0] * m + current[1]]);
							tabRecherchePrecedent[coord[0] * m + coord[1]] = dir;
							newAExplorer.push(coord);
						} else {
							if (tabRechercheTemps[coord[0] * m + coord[1]] > (long) (3600 / vitesse
									+ tabRechercheTemps[current[0] * m + current[1]])) {
								tabRechercheTemps[coord[0] * m + coord[1]] = (long) (3600 / vitesse
										+ tabRechercheTemps[current[0] * m + current[1]]);
								tabRecherchePrecedent[coord[0] * m + coord[1]] = dir;
								newAExplorer.push(coord);
							}
						}
					}
				}
			}
			aExplorer = newAExplorer;
		}
	}

	public long getShortestTime(Case caseVisee) {
		/**
		 *
		 * Cette methode sert a calculer le temps minimum necessaire pour atteindre une
		 * case donnee.
		 *
		 */
		if (!aExplorer.empty()) { // teste si l'algorithme de Dijkstra a ete applique auparavant dans
									// cette situation
			this.calcul();
		}
		return tabRechercheTemps[caseVisee.getLigne() * m + caseVisee.getColonne()];
	}

	public void travelTo(Case caseVisee) {
		/**
		 *
		 * Cette methode sert a envoyer les instructions de deplacement au simulateur,
		 * de telle sorte a prendre le chemin le plus court
		 *
		 */

		if (!aExplorer.empty()) {
			this.calcul();
		}
		Case current = caseVisee;
		Stack<Direction> chemin = new Stack<Direction>();
		while (current.getLigne() != robot.getPositionSimulee().getLigne()
				|| current.getColonne() != robot.getPositionSimulee().getColonne()) {
			chemin.push(tabRecherchePrecedent[current.getLigne() * m + current.getColonne()]);
			switch (chemin.peek()) {
			case NORD:
				current = current.getVoisin(Direction.SUD);
				break;
			case SUD:
				current = current.getVoisin(Direction.NORD);
				break;
			case EST:
				current = current.getVoisin(Direction.OUEST);
				break;
			case OUEST:
				current = current.getVoisin(Direction.EST);
				break;
			default:
				break;
			}
		}
		while (!chemin.empty()) {
			Deplacement dep = new Deplacement(chemin.pop(), robot);
			dep.setDuree((long) (robot.getCarte().getTailleReelleCases() * 3600
					/ robot.getVitesse(robot.getPosition().getNature())));
			robot.getSimulateur().ajouteDeplacement(dep);
		}

	}

}
