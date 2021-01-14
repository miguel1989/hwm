package hwm.dto;

import java.util.Comparator;

public class WarCreatureAtbComparator implements Comparator<WarCreatureBean> {
	@Override
	public int compare(WarCreatureBean creature1, WarCreatureBean creature2) {
		return creature2.currentATB.compareTo(creature1.currentATB);
	}
}
