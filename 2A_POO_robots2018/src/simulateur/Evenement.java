package simulateur;

import robot.Robot;

/**
 * Classe abstraite Evenement qui vont servir a etre ajoutes au simulateur. Ils
 * representent des actions elementaires que les robots doivent effectuer
 *
 */

public abstract class Evenement {

	private long date;
	protected Robot robot;
	protected long duree;

	public Evenement(Robot robot) {
		this.robot = robot;
	}

	public long getDate() {
		return date;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public Robot getRobot() {
		return robot;
	}

	abstract public long getDuree();

	public void setDuree(long duree) {
		this.duree = duree;
	}

	abstract public void execute();
}
