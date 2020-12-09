package hwm.game;

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

	public Cell findCell(int x, int y) {
		return cells.get(y).get(x);
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
