package hwm.dto;

import hwm.exceptions.WrongCoordinatesProvided;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor //default constructor for jackson
public class BoardBean {
	public int width;
	public int height;
	public List<List<CellBean>> cells;

	public BoardBean(int width, int height) {
		this.width = width;
		this.height = height;

		cells = new ArrayList<>(height);
		for (int j = 0; j < height; j++) {
			List<CellBean> cellList = new ArrayList<>(width);
			for (int i = 0; i < width; i++) {
				cellList.add(new CellBean(i, j));
			}
			cells.add(cellList);
		}
	}

	public void addCreature(WarCreatureBean creature) {
		checkCoordinates(creature.x, creature.y);
		this.findCell(creature.x, creature.y).addCreature(creature);
	}

	public CellBean findCell(int x, int y) {
		checkCoordinates(x, y);
		return cells.get(y).get(x);
	}

	//todo think here how to place the hero outside the board
	public void checkCoordinates(int x, int y) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
			throw new WrongCoordinatesProvided();
		}
	}
}
