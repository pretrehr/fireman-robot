package test;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Direction;
import graphicinterface.GUI;
import gui.GUISimulator;
import io.LecteurDonnees;
import simulateur.Deplacement;
import simulateur.Intervention;
import simulateur.Remplissage;
import simulateur.SimulateurBis;

public class TestExtinction {

	public static void main(String[] args) {
		/**
		 * Ce test est le deuxieme scenario proposee dans la partie 2, le robot va
		 * bouger eteindre, bouger, se remplir et finir d'eteindre l'incendie
		 */
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}

		try {
			LecteurDonnees.lire(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
		Carte carte = LecteurDonnees.getCarte();
		carte.setTailleCases(50);
		GUISimulator gui = new GUISimulator(800, 800, Color.BLACK);
		GUI gui_carte = new GUI(carte, gui, args[0]);
		SimulateurBis simulateur = new SimulateurBis();
		// Ajout d'évenements
		simulateur.ajouteEvenement(new Deplacement(Direction.NORD, carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Intervention(carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Deplacement(Direction.OUEST, carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Deplacement(Direction.OUEST, carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Remplissage(carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Deplacement(Direction.EST, carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Deplacement(Direction.EST, carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Intervention(carte.getRobots().get(1)));
		simulateur.ajouteEvenement(new Deplacement(Direction.NORD, carte.getRobots().get(0)));
		gui_carte.setSimulateur(simulateur);
		System.out.println("date = " + simulateur.getDateSimulation());

	}

}