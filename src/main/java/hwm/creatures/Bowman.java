package hwm.creatures;

import hwm.creature.Creature;

import java.math.BigDecimal;

public class Bowman extends Creature {

	public Bowman() {
		super("Bowman", 1, 4, new BigDecimal("12.0"));
	}

	//for testing purposes
	public Bowman(BigDecimal startATB) {
		super("Bowman", 1, 4, new BigDecimal("12.0"), startATB);
	}
}
