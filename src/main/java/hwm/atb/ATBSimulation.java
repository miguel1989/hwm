package hwm.atb;

import hwm.creature.ATBCreature;
import hwm.creature.ATBCreatureComparator;
import hwm.creature.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ATBSimulation {

	private static final int SECONDS_IN_TOUR = 10;
	private static final int TOURS = 10;

	private final List<ATBCreature> creatures = new ArrayList<>();
	private List<ATBCreature> moveList;

	public void addCreature(Creature creature) {
		this.creatures.add(creature.toAtbSimulationCreature());
	}

	public void run() {
		run(TOURS);
	}

	void run(int tours) {
		moveList = new ArrayList<>();
		for (int i = 0; i < tours; i++) {
			for (int j = 0; j < SECONDS_IN_TOUR; j++) {
				this.creatures.forEach(ATBCreature::atbTick);
				List<ATBCreature> creaturesToMoveNow = this.creatures.stream()
						.filter(ATBCreature::isTimeToMove)
						.sorted(new ATBCreatureComparator())
						.collect(Collectors.toList());
				moveList.addAll(creaturesToMoveNow);

				this.creatures.forEach(ATBCreature::atbAfterMove);
			}
		}
	}

	public List<ATBCreature> moveList() {
		return moveList;
	}
}
