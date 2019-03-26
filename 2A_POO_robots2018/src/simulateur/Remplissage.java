package simulateur;

import robot.Robot;

public class Remplissage extends Evenement {

	public Remplissage(Robot robot) {
		super(robot);
		this.duree = robot.dureeRemplirReservoir();
	}
	
	@Override
	public long getDuree() {
		return robot.dureeRemplirReservoir();
	}



	@Override
	public void execute() {
		robot.remplirReservoir();
	}
	
	
	
}
