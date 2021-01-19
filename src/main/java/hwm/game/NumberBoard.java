package hwm.game;

import hwm.dto.CellBean;
import hwm.enums.SimpleBoardCell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9B%D0%B8
// https://habr.com/post/198266/

public class NumberBoard {
	public final int width;
	public final int height;
	public final List<List<Integer>> cells;

	//Simplify board to 0 or -1
	//"0" - means cell can be accessed
	//"-1" - means it can not be accessed (no mater what, creature or obstacle or other stuff)
	public NumberBoard(int width, int height, List<List<CellBean>> cells) {
		this.width = width;
		this.height = height;

		this.cells = new ArrayList<>(height);
		for (int j = 0; j < height; j++) {
			List<Integer> cellList = new ArrayList<>(width);
			for (int i = 0; i < width; i++) {
				CellBean originalCell = cells.get(j).get(i);
				if (originalCell.hasAliveCreatures()) {
					cellList.add(-1);
				} else {
					cellList.add(0);
				}
			}
			this.cells.add(cellList);
		}
	}

	public String toStr() {
		List<String> strList = new ArrayList<>();
		for (int j = 0; j < height; j++) {
			List<Integer> cellList = cells.get(j);
			List<String> tmpStrList = cellList.stream().map(Object::toString).collect(Collectors.toList());
			strList.add(String.join(", ", tmpStrList));
		}

		return String.join("\n", strList);
	}
}
