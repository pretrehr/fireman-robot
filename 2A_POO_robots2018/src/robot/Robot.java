package robot;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
//import simulateur.Simulateur;
import simulateur.SimulateurBis;

public abstract class Robot {
	/**
	 * La classe abstraite Robot regroupe les méthodes <b>générales</b> à tous
	 * les robots Cela permet de factoriser grandement le code notamment les
	 * methodes de mouvement des robots
	 */
	protected Carte carte;
	protected Case position;
	protected Case positionSimulee;
	protected double vitesse = 0;
	protected int reservoirmax;
	protected int volume;
	protected double debit;
	protected long dateWhereFree = 0;
	protected SimulateurBis simulateur;
	protected int numeroRobot = 0;

	public Robot(Carte carte) {
		super();
		this.carte = carte;
	}

	/**
	 * Deverse au max le volume entre en parametre mais peut verser moins si
	 * l'incendie est moins violent que prevu Utilise uniquement le volume d'eau
	 * requis
	 *
	 * @param vol
	 *            est le volume d'eau a verser
	 *
	 */
	public void deverserEau(int vol) {
		int i = position.getLigne();
		int j = position.getColonne();
		int extinction;
		if (vol > position.getIncendie()) {
			// Au cas ou l'incendie est moins violent que prevu voir eteint
			extinction = position.getIncendie();
		} else {
			extinction = vol;
		}
		if (extinction >= volume) {
			carte.getCase(i, j).setIncendie(position.getIncendie() - volume);
			this.setVolume(0);
		} else {
			carte.getCase(i, j).setIncendie(carte.getCase(i, j).getIncendie() - extinction);
			this.setVolume(volume - extinction);
		}
	}

	/**
	 * renvoie si le robot est libre pour une intervention ou un remplissage a la
	 * date rentree en parametre
	 *
	 * @param date
	 *            la date a laquelle on veut savoir si le robot est libre
	 * @return
	 */
	public boolean isFree(long date) {
		return (simulateur.getDateSimulation() >= dateWhereFree);
	}

	/**
	 * Remplit au maximum le reservoir
	 */
	abstract public void remplirReservoir();

	/**
	 * Donne la duree de remplissage du reservoir
	 * @return
	 */
	abstract public long dureeRemplirReservoir();

	public Case getPosition() {
		return position;
	}

	public abstract double getVitesse(NatureTerrain nature);

	public double getVitesse() {
		return vitesse;
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public void setPosition(Case position) {
		this.position = position;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public int getNumeroRobot() {
		return numeroRobot;
	}

	public void setNumeroRobot(int numeroRobot) {
		this.numeroRobot = numeroRobot;
	}

	public boolean voisinExiste(Direction dir) {
		return (position.getVoisins().containsKey(dir));
	}

	/**
	 * La fonction goRemplir trouve le meilleur parcours pour le type de robot donne
	 * et ajoute au simulateur les Evenement de Deplacement et de remplissage
	 */
	public abstract void goRemplir();

	/**
	 * Dit si le rebot est capable d'aller sur la case dans la direction dir vis e
	 * vis de la nature du terrain
	 *
	 * @param dir
	 *            est la direction testee
	 * @return
	 */
	public abstract boolean canMove(Direction dir);

	public abstract boolean canMoveFrom(Case depart, Direction dir);

	/**
	 * modif la vitesse du robot en fonction de la ou il s'apprete a aller vu que
	 * cela peut varier en fonction de la nature du terrain
	 *
	 * @param dir
	 */
	public abstract void modifVitesse(Direction dir);

	public void move(Direction dir) {
		/**
		 * Le robot verifie si la case existe et si oui s'y deplace
		 *
		 * @param dir
		 *            est la direction vers laquelle veut se deplacer le robot
		 */
		if (voisinExiste(dir) && this.canMove(dir)) {
			this.modifVitesse(dir);
			this.setPosition(position.getVoisin(dir));
		} else {
			System.out.println(this);
			System.out.println(dir);
			System.out.println(voisinExiste(dir) + "," + this.canMove(dir));
			throw new IllegalArgumentException("La case doit exister et etre accessible au robot");
		}
	}

	@Override
	public String toString() {
		return "Robot [position=" + positionSimulee + ", vitesse=" + vitesse + ", volume=" + volume + "]\n";
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public int getReservoirmax() {
		return reservoirmax;
	}

	public long getDateWhereFree() {
		return dateWhereFree;
	}

	public void setDateWhereFree(long dateWhereFree) {
		this.dateWhereFree = dateWhereFree;
	}

	public Case getPositionSimulee() {
		return positionSimulee;
	}

	public void setPositionSimulee(Case positionSimulee) {
		this.positionSimulee = positionSimulee;
	}

	public SimulateurBis getSimulateur() {
		return simulateur;
	}

	public void setSimulateur(SimulateurBis simulateur) {
		this.simulateur = simulateur;
	}

	/**
	 * incremente la dateWhereFree
	 *
	 * @param date
	 *            est la duree e ajouter
	 */
	public void incrementeDateWhereFree(long date) {
		this.dateWhereFree += date;
	}

}
