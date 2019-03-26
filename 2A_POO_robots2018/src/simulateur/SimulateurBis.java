package simulateur;

import java.util.ArrayList;

/**
 * Cette classe contient la liste des evenements elementaires a appliquer a la
 * simulation. Le rangEvent permet d'ordonner les evenements dans l'ArrayList
 * qui les contient. La date de simulation va avancer et va servir a rendre les
 * robots disponibles a nouveau pour une intervention une fois que l'evenement
 * requis est passe
 *
 */

public class SimulateurBis {
	private long dateSimulation = 0; // Date courante de simulation
	ArrayList<Evenement> events = new ArrayList<Evenement>(); // Liste des evenements
	private Evenement currentEvent;
	private int rangEvent = 0;
	private boolean simulationTerminee = false; // sert a sortir de la simulation s'il n'y a plus d'incendie et qu'il
												// est inutile de remplir les robots

	/**
	 *
	 * @param e
	 *            l'evenement a ajouter
	 */
	public void ajouteEvenement(Evenement e) {
		events.add(e);
	}

	/**
	 * Ajotue un deplacement elementaire
	 *
	 * @param e
	 *            le deplacement a ajouter
	 */
	public void ajouteDeplacement(Deplacement e) {
		events.add(e);
		if (e instanceof Deplacement) {
			e.getRobot().setPositionSimulee(e.getRobot().getPositionSimulee().getVoisin(e.dir));
		}
	}

	public void execEvenement() {
		/**
		 * Cette methode va prendre le prochain evenement a faire, maj le moment ou le
		 * robot sera libre et lance l'execution de l'evenement
		 */
		currentEvent = events.get(rangEvent);
		incrementeDate();
		rangEvent++;
		currentEvent.getRobot().setDateWhereFree(currentEvent.getRobot().getDateWhereFree() + currentEvent.getDuree());
		currentEvent.execute();
	}

	private void incrementeDate() {
		dateSimulation += currentEvent.getDuree();
	}

	/**
	 *
	 * @return si la simulation est terminee ou non
	 */
	public boolean simulationTerminee() {
		return (simulationTerminee || events.size() > 0 && rangEvent == events.size());
	}

	public long getDateSimulation() {
		return dateSimulation;
	}

	public void setDateSimulation(long dateSimulation) {
		this.dateSimulation = dateSimulation;
	}

	public ArrayList<Evenement> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Evenement> events) {
		this.events = events;
	}

	public int getRangEvent() {
		return rangEvent;
	}

	public void setRangEvent(int rangEvent) {
		this.rangEvent = rangEvent;
	}

	public boolean isSimulationTerminee() {
		return simulationTerminee;
	}

	public void setSimulationTerminee(boolean simulationTerminee) {
		simulationTerminee = simulationTerminee;
	}

	public Evenement getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Evenement currentEvent) {
		this.currentEvent = currentEvent;
	}

}
