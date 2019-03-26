package graphicinterface;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import carte.Carte;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import io.LecteurDonnees;
import robot.Robot;
import simulateur.Evenement;
import simulateur.SimulateurBis;

public class GUI implements Simulable {
	private GUISimulator gui;
	private Carte carte;
	private String fichierDonnees;
	public SimulateurBis simulateur;
	private long intervalle = 5000;
	public long date_actuelle = 0;

	public GUI(Carte carte, GUISimulator gui, String fichierDonnees) {
		super();
		this.gui = gui;
		this.carte = carte;
		this.fichierDonnees = fichierDonnees;
		gui.setSimulable(this);
		afficher(carte, gui);
	}

	public void afficher(Carte carte, GUISimulator gui) {
		gui.reset();
		for (int i = 0; i < carte.getNbLignes(); i++) {
			for (int j = 0; j < carte.getNbColonnes(); j++) {
				switch (carte.getCase(i, j).getNature()) {
				case EAU:
					gui.addGraphicalElement(new Rectangle(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
							carte.getTailleCases() / 2 + i * carte.getTailleCases(), Color.black, Color.blue,
							carte.getTailleCases()));
					break;
				case FORET:
					gui.addGraphicalElement(new Rectangle(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
							carte.getTailleCases() / 2 + i * carte.getTailleCases(), Color.black, Color.green,
							carte.getTailleCases()));
					break;
				case ROCHE:
					gui.addGraphicalElement(new Rectangle(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
							carte.getTailleCases() / 2 + i * carte.getTailleCases(), Color.black, Color.gray,
							carte.getTailleCases()));
					break;
				case TERRAIN_LIBRE:
					gui.addGraphicalElement(new Rectangle(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
							carte.getTailleCases() / 2 + i * carte.getTailleCases(), Color.black, new Color(91, 60, 17),
							carte.getTailleCases()));
					break;
				default:
					gui.addGraphicalElement(new Rectangle(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
							carte.getTailleCases() / 2 + i * carte.getTailleCases(), Color.black,
							new Color(200, 173, 127), carte.getTailleCases()));
				}
				if (carte.getCase(i, j).getIncendie() > 0) {
					gui.addGraphicalElement(new Rectangle(carte.getTailleCases() / 4 + j * carte.getTailleCases(),
							carte.getTailleCases() / 4 + i * carte.getTailleCases(), Color.black,
							new Color(237, 127, 16), carte.getTailleCases() / 2));
					gui.addGraphicalElement(new Text(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
							carte.getTailleCases() / 4 + i * carte.getTailleCases(), Color.blue,
							Integer.toString(carte.getCase(i, j).getIncendie())));
				}
			}
		}
		for (Robot robot : carte.getRobots()) {
			int i = robot.getPosition().getLigne();
			int j = robot.getPosition().getColonne();
			gui.addGraphicalElement(new Rectangle(3 * carte.getTailleCases() / 4 + j * carte.getTailleCases(),
					3 * carte.getTailleCases() / 4 + i * carte.getTailleCases(), Color.black, Color.red,
					carte.getTailleCases() / 2));
			gui.addGraphicalElement(new Text(carte.getTailleCases() / 2 + j * carte.getTailleCases(),
					3 * carte.getTailleCases() / 4 + i * carte.getTailleCases(), Color.white,
					Integer.toString(robot.getVolume())));
		}
	}

	@Override
	public void next() {
		if (!simulateur.simulationTerminee() && (simulateur.getEvents().size() - simulateur.getRangEvent() > 0)) {
			this.date_actuelle += intervalle;
			ArrayList<Evenement> iterateurEvenement = simulateur.getEvents();
			Evenement event = iterateurEvenement.get(simulateur.getRangEvent());
			while (!simulateur.simulationTerminee()
					&& (simulateur.getDateSimulation() + event.getDuree() < date_actuelle)) {
				simulateur.execEvenement();
				if (!simulateur.simulationTerminee()) {
					event = iterateurEvenement.get(simulateur.getRangEvent());
				}
			}
		}
		afficher(carte, gui);
	}

	@Override
	public void restart() {
		System.out.println("RESTART");
		try {
			LecteurDonnees.lire(fichierDonnees);
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + fichierDonnees + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + fichierDonnees + " invalide: " + e.getMessage());
		}
		gui.reset();
		this.carte = LecteurDonnees.getCarte();
		this.carte.setTailleCases(30);
		afficher(carte, gui);
		this.simulateur.setRangEvent(0);
		this.simulateur.setDateSimulation(0);
		this.simulateur.setCurrentEvent(null);
		int numeroAncienRobot;
		for (int i = 0; i < simulateur.getEvents().size(); i++) {
			numeroAncienRobot = simulateur.getEvents().get(i).getRobot().getNumeroRobot();
			simulateur.getEvents().get(i).setRobot(carte.getRobots().get(numeroAncienRobot));
		}
		this.date_actuelle = 0;
	}

	public SimulateurBis getSimulateur() {
		return simulateur;
	}

	public void setSimulateur(SimulateurBis simulateur) {
		this.simulateur = simulateur;
	}

}
