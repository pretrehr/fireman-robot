package simulateur;

import carte.Direction;
import robot.Robot;
/**
 * 
 * Un evenenement de deplacement elementaire du Robot vers une direction
 *
 */

public class Deplacement extends Evenement {

	Direction dir;

	public Deplacement(Direction dir, Robot robot) {
		super(robot);
		this.dir = dir;
	}

	@Override
	public long getDuree() {
		return (long) (robot.getCarte().getTailleReelleCases() * 3600
				/ robot.getVitesse(robot.getPosition().getNature()));
	}

	@Override
	public void execute() {
		robot.move(dir);
	}

	@Override
	public String toString() {
		return robot + "Deplacement [dir=" + dir + "]\n";
	}

}
