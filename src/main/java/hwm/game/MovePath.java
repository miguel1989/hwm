package hwm.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovePath {
	private final NumberBoard numberBoard;
	private final Point start;
	private final Point end;
	private final List<Point> pathList = new ArrayList<>();

	public MovePath(NumberBoard numberBoard, Point start, Point end) {
		this.numberBoard = numberBoard;
		this.start = start;
		this.end = end;

		this.calc();
	}

	private void calc() {
		int indicator = 1;
		int totalSize = numberBoard.width + numberBoard.height;
		int idx = 0;
		numberBoard.cells.get(start.y).set(start.x, indicator);
		do {
			idx++;
			//do stuff
			for (int j = 0; j < numberBoard.height; j++) {
				for (int i = 0; i < numberBoard.width; i++) {
					if (numberBoard.cells.get(j).get(i) == indicator) {
						int nextIndicator = indicator + 1;
						//left cell
						if (i - 1 >= 0 && numberBoard.cells.get(j).get(i - 1) == 0) {
							numberBoard.cells.get(j).set(i - 1, nextIndicator);
						}
						//up
						if (j - 1 >= 0 && numberBoard.cells.get(j - 1).get(i) == 0) {
							numberBoard.cells.get(j - 1).set(i, nextIndicator);
						}
						//right
						if (i + 1 < numberBoard.width && numberBoard.cells.get(j).get(i + 1) == 0) {
							numberBoard.cells.get(j).set(i + 1, nextIndicator);
						}
						//down
						if (j + 1 < numberBoard.height && numberBoard.cells.get(j + 1).get(i) == 0) {
							numberBoard.cells.get(j + 1).set(i, nextIndicator);
						}

						// diagonally. left up
						if (i - 1 >= 0 && j - 1 >= 0 && numberBoard.cells.get(j - 1).get(i - 1) == 0) {
							numberBoard.cells.get(j - 1).set(i - 1, nextIndicator);
						}
						// diagonally. right up
						if (i + 1 < numberBoard.width && j - 1 >= 0 && numberBoard.cells.get(j - 1).get(i + 1) == 0) {
							numberBoard.cells.get(j - 1).set(i + 1, nextIndicator);
						}
						// diagonally. left down
						if (i - 1 >= 0 && j + 1 < numberBoard.height && numberBoard.cells.get(j + 1).get(i - 1) == 0) {
							numberBoard.cells.get(j + 1).set(i - 1, nextIndicator);
						}
						// diagonally. right down
						if (i + 1 < numberBoard.width && j + 1 < numberBoard.height && numberBoard.cells.get(j + 1).get(i + 1) == 0) {
							numberBoard.cells.get(j + 1).set(i + 1, nextIndicator);
						}
					}
				}
			}
			indicator++;
		} while (numberBoard.cells.get(end.y).get(end.x) <= 0 && idx < totalSize);

		System.out.println(this.numberBoard.toStr());

		if (numberBoard.cells.get(end.y).get(end.x) > 0) {
			recurFindPathFromTheEnd(end.x, end.y);
			Collections.reverse(this.pathList);
		}
	}

	private void recurFindPathFromTheEnd(int curX, int curY) {
		if (this.start.x == curX && this.start.y == curY) {
			return;
		}
		this.pathList.add(new Point(curX, curY));
		int targetNum = numberBoard.cells.get(curY).get(curX) - 1;

		//left
		if (curX - 1 >= 0 && numberBoard.cells.get(curY).get(curX - 1) == targetNum) {
			recurFindPathFromTheEnd(curX - 1, curY);
			return;
		}
		//up
		if (curY - 1 >= 0 && numberBoard.cells.get(curY - 1).get(curX) == targetNum) {
			recurFindPathFromTheEnd(curX, curY - 1);
			return;
		}
		//right
		if (curX + 1 < numberBoard.width && numberBoard.cells.get(curY).get(curX + 1) == targetNum) {
			recurFindPathFromTheEnd(curX + 1, curY);
			return;
		}
		//down
		if (curY + 1 < numberBoard.height && numberBoard.cells.get(curY + 1).get(curX) == targetNum) {
			recurFindPathFromTheEnd(curX, curY + 1);
			return;
		}
		// diagonally. left up
		if (curX - 1 >= 0 && curY - 1 >= 0 && numberBoard.cells.get(curY - 1).get(curX - 1) == targetNum) {
			recurFindPathFromTheEnd(curX - 1, curY - 1);
			return;
		}
		// diagonally. right up
		if (curX + 1 < numberBoard.width && curY - 1 >= 0 && numberBoard.cells.get(curY - 1).get(curX + 1) == targetNum) {
			recurFindPathFromTheEnd(curX + 1, curY - 1);
			return;
		}
		// diagonally. left down
		if (curX - 1 >= 0 && curY + 1 < numberBoard.height && numberBoard.cells.get(curY + 1).get(curX - 1) == targetNum) {
			recurFindPathFromTheEnd(curX - 1, curY + 1);
			return;
		}
		// diagonally. right down
		if (curX + 1 < numberBoard.width && curY + 1 < numberBoard.height && numberBoard.cells.get(curY + 1).get(curX + 1) == targetNum) {
			recurFindPathFromTheEnd(curX + 1, curY + 1);
		}
	}

	public List<Point> pathList() {
		return this.pathList;
	}
}
