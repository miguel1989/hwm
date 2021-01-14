package hwm.dto;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//todo cell can contain rocks/walls/grenades
@NoArgsConstructor //default constructor for jackson
public class CellBean {
	public final List<WarCreatureBean> creatures = new ArrayList<>();
	public int x;
	public int y;

	public CellBean(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void addCreature(WarCreatureBean creature) {
		this.creatures.add(creature);
	}

//	public boolean hasAliveCreature() {
//		return this.creatures.stream().anyMatch(Creature::isAlive);
//	}
}
