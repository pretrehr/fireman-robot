package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import robot.Drone;
import robot.Robot;
import robot.RobotAChenille;
import robot.RobotAPattes;
import robot.RobotARoue;
import simulateur.SimulateurBis;

public class LecteurDonnees {
	static Carte carte;

	/**
	 * Lit et enregistre le contenu d'un fichier de donnees (cases, robots et
	 * incendies). Ceci est méthode de classe; utilisation:
	 * LecteurDonnees.lire(fichierDonnees)
	 *
	 * @param fichierDonnees
	 *            nom du fichier à lire
	 */
	public static void lire(String fichierDonnees) throws FileNotFoundException, DataFormatException {
		System.out.println("\n == Lecture du fichier" + fichierDonnees);
		LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
		lecteur.storeCarte();
		lecteur.lireIncendies();
		lecteur.lireRobots();
		scanner.close();
		System.out.println("\n == Lecture terminee");
		for (Robot rob : carte.getRobots()) {
			rob.setCarte(carte);
		}
	}

	private static Scanner scanner;

	/**
	 * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
	 *
	 * @param fichierDonnees
	 *            nom du fichier a lire
	 */
	private LecteurDonnees(String fichierDonnees) throws FileNotFoundException {
		scanner = new Scanner(new File(fichierDonnees));
		scanner.useLocale(Locale.US);
	}

	/**
	 * Lit et enregistre les donnees de la carte.
	 *
	 * @throws ExceptionFormatDonnees
	 */

	private void storeCarte() throws DataFormatException {
		ignorerCommentaires();
		try {
			int nbLignes = scanner.nextInt();
			int nbColonnes = scanner.nextInt();
			int tailleCases = scanner.nextInt();
			this.setCarte(new Carte(nbLignes, nbColonnes));
			carte.setTailleCases(tailleCases);
			// en m
			for (int lig = 0; lig < nbLignes; lig++) {
				for (int col = 0; col < nbColonnes; col++) {
					storeCase(lig, col, carte);
				}
			}

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. " + "Attendu: nbLignes nbColonnes tailleCases");
		}
		// une ExceptionFormat levee depuis lireCase est remontee telle quelle
	}

	/**
	 * Lit et enregistre les donnees d'une case.
	 */

	private void storeCase(int lig, int col, Carte carte) throws DataFormatException {
		ignorerCommentaires();
		String chaineNature = new String();
		Case[] map = carte.getMap();
		int nbC = carte.getNbColonnes();
		try {
			chaineNature = scanner.next();

			verifieLigneTerminee();
			map[lig * nbC + col].setNature(chaineNature);
			if (chaineNature.equals("EAU")) {
				carte.getEau().add(map[lig * nbC + col]);
			}


		} catch (NoSuchElementException e) {
			throw new DataFormatException("format de case invalide. " + "Attendu: nature altitude [valeur_specifique]");
		}
	}

	/**
	 * Lit et enregistre les donnees des incendies.
	 */
	private void lireIncendies() throws DataFormatException {
		ignorerCommentaires();
		try {
			int nbIncendies = scanner.nextInt();
			carte.setNbIncendies(nbIncendies);
			for (int i = 0; i < nbIncendies; i++) {
				lireIncendie(i);
			}

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. " + "Attendu: nbIncendies");
		}
	}

	/**
	 * Lit et enregistre les donnees du i-eme incendie.
	 *
	 * @param i
	 */
	private void lireIncendie(int i) throws DataFormatException {
		ignorerCommentaires();

		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			int intensite = scanner.nextInt();
			if (intensite <= 0) {
				throw new DataFormatException("incendie " + i + "nb litres pour eteindre doit etre > 0");
			}
			verifieLigneTerminee();
			carte.getMap()[lig*carte.getNbColonnes()+col].setIncendie(intensite);
			carte.getIncendies().add(carte.getMap()[lig*carte.getNbColonnes()+col]);

		} catch (NoSuchElementException e) {
			throw new DataFormatException("format d'incendie invalide. " + "Attendu: ligne colonne intensite");
		}
	}

	/**
	 * Lit et enregistre les donnees des robots.
	 */
	private void lireRobots() throws DataFormatException {
		ignorerCommentaires();
		SimulateurBis simulateur = new SimulateurBis();
		try {
			int nbRobots = scanner.nextInt();
			carte.setNbRobots(nbRobots);
			for (int i = 0; i < nbRobots; i++) {
				lireRobot(i);
			}
			for (Robot rob : carte.getRobots()) {
				rob.setSimulateur(simulateur);
			}

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. " + "Attendu: nbRobots");
		}
	}

	/**
	 * Lit et enregistre les donnees du i-eme robot.
	 *
	 * @param i
	 */
	private void lireRobot(int i) throws DataFormatException {
		ignorerCommentaires();


		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			String type = scanner.next();
			Robot robot;

			switch (type) {
			case "DRONE":
				robot = new Drone(carte);
				robot.setVolume(robot.getReservoirmax());
				carte.getRobots().add(robot);
				robot.setDebit(10000/30);
				break;
			case "ROUES":
				robot = new RobotARoue(carte);
				robot.setVolume(robot.getReservoirmax());
				robot.setDebit(100/5);
				carte.getRobots().add(robot);
				break;
			case "PATTES":
				robot = new RobotAPattes(carte);
				carte.getRobots().add(robot);
				robot.setVolume(10000000);
				robot.setDebit(10);
				break;
			case "CHENILLES":
				robot = new RobotAChenille(carte);
				robot.setVolume(robot.getReservoirmax());
				robot.setDebit(100/8);
				carte.getRobots().add(robot);
				break;
			default:
				robot=null;
				break;
			}
			robot.setNumeroRobot(i);
			robot.setPosition(carte.getMap()[lig*carte.getNbColonnes()+col]);
			robot.setPositionSimulee(carte.getMap()[lig*carte.getNbColonnes()+col]);
			// lecture eventuelle d'une vitesse du robot (entier)
			String s = scanner.findInLine("(\\d+)"); // 1 or more digit(s) ?
			// pour lire un flottant: ("(\\d+(\\.\\d+)?)");

			if (s == null) {
			} else {
				int vitesse = Integer.parseInt(s);
				robot.setVitesse(vitesse);
			}
			verifieLigneTerminee();

			System.out.println();

		} catch (NoSuchElementException e) {
			throw new DataFormatException(
					"format de robot invalide. " + "Attendu: ligne colonne type [valeur_specifique]");
		}
	}

	/** Ignore toute (fin de) ligne commencant par '#' */
	private void ignorerCommentaires() {
		while (scanner.hasNext("#.*")) {
			scanner.nextLine();
		}
	}

	/**
	 * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
	 *
	 * @throws ExceptionFormatDonnees
	 */
	private void verifieLigneTerminee() throws DataFormatException {
		if (scanner.findInLine("(\\d+)") != null) {
			throw new DataFormatException("format invalide, donnees en trop.");
		}
	}

	static public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		LecteurDonnees.carte = carte;
	}

}
