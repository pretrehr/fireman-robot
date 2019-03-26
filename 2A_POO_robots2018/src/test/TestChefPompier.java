package test;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import cheminoptimal.cheminoptimal;
import graphicinterface.GUI;
import gui.GUISimulator;
import io.LecteurDonnees;
import robot.Robot;
import simulateur.ChefPompier;
import simulateur.SimulateurBis;

public class TestChefPompier {

	public static void main(String[] args) {
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
		carte.setTailleCases(30);
		GUISimulator gui = new GUISimulator(100 * carte.getNbLignes(), 100 * carte.getNbColonnes(), Color.BLACK);
		GUI gui_carte = new GUI(carte, gui, args[0]);
		SimulateurBis simulateur = carte.getRobots().get(0).getSimulateur();
		gui_carte.setSimulateur(simulateur);
		ChefPompier chef = new ChefPompier();
		chef.setCarte(carte);
		chef.setSimulateur(simulateur);
		for (Robot robot : carte.getRobots()) {
			chef.getChemins().add(new cheminoptimal(robot));
		}

		long pas = 50; // Pas de simulation

		while (!simulateur.simulationTerminee() && carte.getIncendies().size()!=0) {
			chef.faireLeChef(pas);
		}
		System.out.println("Simulation terminee : merci d'avoir utilise notre algorithme");

		System.out.println("Vous pouvez relancer la simulation avec debut sur l'interface graphique ou appuyer sur le bouton quitter");
	}

}
