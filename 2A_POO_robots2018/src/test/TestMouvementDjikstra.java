package test;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import cheminoptimal.cheminoptimal;
import graphicinterface.GUI;
import gui.GUISimulator;
import io.LecteurDonnees;
import simulateur.Intervention;
import simulateur.SimulateurBis;

public class TestMouvementDjikstra {

	public static void main(String[] args) {
        /**
         * Ce test a pour but de tester si chaque Robot se déplace correctement en utilisant l'algo de Dijkstra
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
        SimulateurBis simulateur = carte.getRobots().get(0).getSimulateur();
        gui_carte.setSimulateur(simulateur);
        cheminoptimal co1 = new cheminoptimal(carte.getRobots().get(1));
        co1.travelTo(carte.getCase(7,7));
        simulateur.ajouteEvenement(new Intervention(carte.getRobots().get(1)));
        carte.getRobots().get(1).goRemplir();
        System.out.println(simulateur.getEvents());
        System.out.println("Le robot devrait etre en 7,7");
        carte.getRobots().get(0).toString();
	}

}
