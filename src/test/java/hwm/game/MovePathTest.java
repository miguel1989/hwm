package hwm.game;

import hwm.dto.BoardBean;
import hwm.dto.WarCreatureBean;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovePathTest {

	@Test
	public void moveAroundObstacle() {
		BoardBean boardBean = new BoardBean(5, 5);

		WarCreatureBean creature1 = createCreature(0, 0);
		WarCreatureBean creature2 = createCreature(1, 0);
		WarCreatureBean creature3 = createCreature(2, 0);
		boardBean.addCreature(creature1);
		boardBean.addCreature(creature2);
		boardBean.addCreature(creature3);


		NumberBoard numberBoard = boardBean.toNumberBoard();
		MovePath movePath = new MovePath(numberBoard, new Point(0, 0), new Point(4, 0));
		List<Point> pathList = movePath.pathList();
		System.out.println(pathList);
		assertEquals(4, pathList.size());
		assertEquals("Point{x=1, y=1}", pathList.get(0).toString());
		assertEquals("Point{x=2, y=1}", pathList.get(1).toString());
		assertEquals("Point{x=3, y=0}", pathList.get(2).toString());
		assertEquals("Point{x=4, y=0}", pathList.get(3).toString());
	}

	@Test
	public void moveLikeFuria() {
		BoardBean boardBean = new BoardBean(8, 8);

		WarCreatureBean creature1 = createCreature(0, 0);
		WarCreatureBean creature2 = createCreature(1, 0);
		boardBean.addCreature(creature1);
		boardBean.addCreature(creature2);

		NumberBoard numberBoard = boardBean.toNumberBoard();
		MovePath movePath = new MovePath(numberBoard, new Point(0, 0), new Point(7, 3));
		List<Point> pathList = movePath.pathList();
		System.out.println(pathList);
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
