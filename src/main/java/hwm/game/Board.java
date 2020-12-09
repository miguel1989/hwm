package hwm.game;

import hwm.creature.Creature;
import hwm.exceptions.WrongCoordinatesProvided;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	private final int width;
	private final int height;
	private final List<List<Cell>> cells;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		cells = new ArrayList<>(height);
		for (int j = 0; j < height; j++) {
			List<Cell> cellList = new ArrayList<>(width);
			for (int i = 0; i < width; i++) {
				cellList.add(new Cell(i, j));
			}
			cells.add(cellList);
		}
	}

	public void addCreature(int x, int y, Creature creature) {
		checkCoordinates(x, y);
		this.findCell(x, y).addCreature(creature);
	}

	public Cell findCell(int x, int y) {
		checkCoordinates(x, y);
		return cells.get(y).get(x);
	}

	private void checkCoordinates(int x, int y) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
			throw new WrongCoordinatesProvided();
		}
	}

	List<List<Cell>> cells() {
		return cells;
	}

	@Override
	public String toString() {
		List<String> strList1 = cells.stream().map(cellList -> {
			List<String> strList2 = cellList.stream().map(Cell::toStr).collect(Collectors.toList());
			return String.join(", ", strList2);
		}).collect(Collectors.toList());
		return String.join("\n", strList1);
	}
}
