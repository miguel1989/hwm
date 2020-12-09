package hwm.atb;

import hwm.creature.Creature;

import java.util.ArrayList;
import java.util.List;

public class ATB {

	private static final int SECONDS_IN_TOUR = 10;

	private int currentSecond = 0;

	private final List<Creature> creatures = new ArrayList<>();

	public void addCreature(Creature creature) {
		this.creatures.add(creature);
	}

	public void tick() {
		this.creatures
				.stream()
				.filter(Creature::isAlive)
				.forEach(Creature::atbTick);
	}
}
