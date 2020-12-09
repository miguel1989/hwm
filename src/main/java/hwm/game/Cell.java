package hwm.game;

import hwm.creature.Creature;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private final List<Creature> creatures = new ArrayList<>();
	private final int x;
	private final int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public List<Creature> creatures() {
		return creatures;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public String toStr() {
		return "(x = " + x + ", y = " + y + ")";
	}
}
