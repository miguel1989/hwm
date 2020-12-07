package hwm.creatures;

import hwm.creature.Creature;

import java.math.BigDecimal;

public class Horse extends Creature {

	public Horse() {
		super("Horse", 1, 8, new BigDecimal(16));
	}

	//for testing purposes
	public Horse(BigDecimal startATB) {
		super("Horse", 1, 8, new BigDecimal(16), startATB);
	}
}
