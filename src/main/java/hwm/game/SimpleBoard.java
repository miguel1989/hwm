package hwm.game;

import hwm.enums.SimpleBoardCell;

import java.util.ArrayList;
import java.util.List;

public class SimpleBoard {
	private final int width;
	private final int height;
	private final List<List<SimpleBoardCell>> cells;

	public SimpleBoard(int width, int height, List<List<Cell>> cells) {
		this.width = width;
		this.height = height;

		this.cells = new ArrayList<>(height);
		for (int j = 0; j < height; j++) {
			List<SimpleBoardCell> cellList = new ArrayList<>(width);
			for (int i = 0; i < width; i++) {
				Cell originalCell = cells.get(j).get(i);
				if (originalCell.hasAliveCreature()) {
					cellList.add(SimpleBoardCell.OBSTACLE);
				} else {
					cellList.add(SimpleBoardCell.EMPTY);
				}
			}
			this.cells.add(cellList);
		}
	}
}
