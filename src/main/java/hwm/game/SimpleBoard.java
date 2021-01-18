package hwm.game;

import hwm.dto.CellBean;
import hwm.enums.SimpleBoardCell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleBoard {
	public final int width;
	public final int height;
	public final List<List<SimpleBoardCell>> cells;

	public SimpleBoard(int width, int height, List<List<CellBean>> cells) {
		this.width = width;
		this.height = height;

		this.cells = new ArrayList<>(height);
		for (int j = 0; j < height; j++) {
			List<SimpleBoardCell> cellList = new ArrayList<>(width);
			for (int i = 0; i < width; i++) {
				CellBean originalCell = cells.get(j).get(i);
				if (originalCell.hasAliveCreatures()) {
					cellList.add(SimpleBoardCell.OBSTACLE);
				} else {
					cellList.add(SimpleBoardCell.EMPTY);
				}
			}
			this.cells.add(cellList);
		}
	}

	public String toStr() {
		List<String> strList = new ArrayList<>();
		for (int j = 0; j < height; j++) {
			List<SimpleBoardCell> cellList = cells.get(j);
			List<String> tmpStrList = cellList.stream().map(it -> it.name().substring(0, 1)).collect(Collectors.toList());
			strList.add(String.join(", ", tmpStrList));
		}

		return String.join("\n", strList);
	}
}
