package hwm.game;

import hwm.dto.BoardBean;
import hwm.dto.WarCreatureBean;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovePathTestNoObstacles {

	@Test
	public void moveStraightFromCorner() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(0, 0);
		boardBean.addCreature(warCreatureBean);

		NumberBoard numberBoard = boardBean.toNumberBoard();
		MovePath movePath = new MovePath(numberBoard, new Point(0, 0), new Point(3, 0));
		List<Point> pathList = movePath.pathList();
		System.out.println(pathList);
		assertEquals(3, pathList.size());
		assertEquals("Point{x=1, y=0}", pathList.get(0).toString());
		assertEquals("Point{x=2, y=0}", pathList.get(1).toString());
		assertEquals("Point{x=3, y=0}", pathList.get(2).toString());
	}

	@Test
	public void moveDiagonallyFromTopLeft() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(0, 0);
		boardBean.addCreature(warCreatureBean);
		NumberBoard numberBoard = boardBean.toNumberBoard();
		MovePath movePath = new MovePath(numberBoard, new Point(0, 0), new Point(5, 5));
		List<Point> pathList = movePath.pathList();
		System.out.println(pathList);
		assertEquals(5, pathList.size());
		assertEquals("Point{x=1, y=1}", pathList.get(0).toString());
		assertEquals("Point{x=2, y=2}", pathList.get(1).toString());
		assertEquals("Point{x=3, y=3}", pathList.get(2).toString());
		assertEquals("Point{x=4, y=4}", pathList.get(3).toString());
		assertEquals("Point{x=5, y=5}", pathList.get(4).toString());
	}

	@Test
	public void moveDiagonallyFromBottomLeft() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(0, 5);
		boardBean.addCreature(warCreatureBean);
		NumberBoard numberBoard = boardBean.toNumberBoard();
		MovePath movePath = new MovePath(numberBoard, new Point(0, 5), new Point(5, 0));
		List<Point> pathList = movePath.pathList();
		System.out.println(pathList);
		assertEquals(5, pathList.size());
		assertEquals("Point{x=1, y=4}", pathList.get(0).toString());
		assertEquals("Point{x=2, y=3}", pathList.get(1).toString());
		assertEquals("Point{x=3, y=2}", pathList.get(2).toString());
		assertEquals("Point{x=4, y=1}", pathList.get(3).toString());
		assertEquals("Point{x=5, y=0}", pathList.get(4).toString());
	}

	private WarCreatureBean createCreature(int x, int y) {
		WarCreatureBean warCreatureBean = new WarCreatureBean();
		warCreatureBean.x = x;
		warCreatureBean.y = y;
		warCreatureBean.currentCount = 1;
		warCreatureBean.paramsFinal.speed = 3;
		return warCreatureBean;
	}
}
