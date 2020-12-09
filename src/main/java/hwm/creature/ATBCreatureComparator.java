package hwm.creature;

import java.util.Comparator;

public class ATBCreatureComparator implements Comparator<ATBCreature> {

	@Override
	public int compare(ATBCreature creature1, ATBCreature creature2) {
		return creature2.currentATB().compareTo(creature1.currentATB());
	}
}
