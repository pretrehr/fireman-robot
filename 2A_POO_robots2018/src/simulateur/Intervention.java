package simulateur;

import javax.swing.text.html.MinimalHTMLWriter;

import robot.Robot;

public class Intervention extends Evenement {

	public Intervention(Robot robot) {
		super(robot);
	}

	/**
	 * Renvoie la duree de l'intervention
	 */
	@Override
	public long getDuree() {

		return (long) (Math.min(robot.getPosition().getIncendie() / robot.getDebit(),
				robot.getVolume() / robot.getDebit()));
	}

	@Override
	public void execute() {
		robot.deverserEau(robot.getPosition().getIncendie());
	}

	@Override
	public String toString() {
		return "Intervention [robot=" + robot + "]";
	}

}
