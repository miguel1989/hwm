package hwm.game;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AvailableMovesList {
	@Getter
	private final List<Point> list = new ArrayList<>();

	public void add(Point point) {
		this.list.add(point);
	}

	public boolean contains(Point point) {
		if (point == null) {
			return false;
		}
		return this.list.stream().anyMatch(tmpPoint -> tmpPoint.x == point.x && tmpPoint.y == point.y);
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		return "AvailableMovesList{" +
				"list=" + list +
				'}';
	}

}
