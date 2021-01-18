package hwm.game;

import hwm.dto.BoardBean;
import hwm.dto.WarCreatureBean;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableMovesEmptyBoard {

	@Test
	public void moveFromTopLeftCorner() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(0, 0);

		boardBean.addCreature(warCreatureBean);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
//		System.out.println(simpleBoard.toStr());
		StopWatch sw = new StopWatch();
		sw.start("availableMoves.calc");
		AvailableMoves availableMoves = new AvailableMoves(warCreatureBean, simpleBoard);
		availableMoves.calc();
		sw.stop();

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);
		System.out.println(sw.prettyPrint());

		assertEquals(10, movesList.size());
		assertFalse(movesList.contains(new Point(0, 0)), "self position, should not contain");
		assertTrue(movesList.contains(new Point(1, 0)));
		assertTrue(movesList.contains(new Point(2, 0)));
		assertTrue(movesList.contains(new Point(3, 0)));
		assertTrue(movesList.contains(new Point(0, 1)));
		assertTrue(movesList.contains(new Point(1, 1)));
		assertTrue(movesList.contains(new Point(2, 1)));
		assertTrue(movesList.contains(new Point(0, 2)));
		assertTrue(movesList.contains(new Point(1, 2)));
		assertTrue(movesList.contains(new Point(2, 2)));
		assertTrue(movesList.contains(new Point(0, 3)));
	}

	@Test
	public void moveFromTopRightCorner() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(5, 0);

		boardBean.addCreature(warCreatureBean);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		AvailableMoves availableMoves = new AvailableMoves(warCreatureBean, simpleBoard);
		availableMoves.calc();

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);

		assertEquals(10, movesList.size());
		assertFalse(movesList.contains(new Point(5, 0)), "self position, should not contain");
		assertTrue(movesList.contains(new Point(4, 0)));
		assertTrue(movesList.contains(new Point(3, 0)));
		assertTrue(movesList.contains(new Point(2, 0)));
		assertTrue(movesList.contains(new Point(5, 1)));
		assertTrue(movesList.contains(new Point(4, 1)));
		assertTrue(movesList.contains(new Point(3, 1)));
		assertTrue(movesList.contains(new Point(5, 2)));
		assertTrue(movesList.contains(new Point(4, 2)));
		assertTrue(movesList.contains(new Point(3, 2)));
		assertTrue(movesList.contains(new Point(5, 3)));
	}

	@Test
	public void moveFromBottomRightCorner() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(5, 5);

		boardBean.addCreature(warCreatureBean);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		AvailableMoves availableMoves = new AvailableMoves(warCreatureBean, simpleBoard);
		availableMoves.calc();

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);

		assertEquals(10, movesList.size());
		assertFalse(movesList.contains(new Point(5, 5)), "self position, should not contain");
		assertTrue(movesList.contains(new Point(4, 5)));
		assertTrue(movesList.contains(new Point(3, 5)));
		assertTrue(movesList.contains(new Point(2, 5)));
		assertTrue(movesList.contains(new Point(5, 4)));
		assertTrue(movesList.contains(new Point(4, 4)));
		assertTrue(movesList.contains(new Point(3, 4)));
		assertTrue(movesList.contains(new Point(5, 3)));
		assertTrue(movesList.contains(new Point(4, 3)));
		assertTrue(movesList.contains(new Point(3, 3)));
		assertTrue(movesList.contains(new Point(5, 2)));
	}

	@Test
	public void moveFromBottomLeftCorner() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean warCreatureBean = createCreature(0, 5);

		boardBean.addCreature(warCreatureBean);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		AvailableMoves availableMoves = new AvailableMoves(warCreatureBean, simpleBoard);
		availableMoves.calc();

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);

		assertEquals(10, movesList.size());
		assertFalse(movesList.contains(new Point(0, 5)), "self position, should not contain");
		assertTrue(movesList.contains(new Point(1, 5)));
		assertTrue(movesList.contains(new Point(2, 5)));
		assertTrue(movesList.contains(new Point(3, 5)));
		assertTrue(movesList.contains(new Point(0, 4)));
		assertTrue(movesList.contains(new Point(1, 4)));
		assertTrue(movesList.contains(new Point(2, 4)));
		assertTrue(movesList.contains(new Point(0, 3)));
		assertTrue(movesList.contains(new Point(1, 3)));
		assertTrue(movesList.contains(new Point(2, 3)));
		assertTrue(movesList.contains(new Point(0, 2)));
	}

	@Test
	public void moveFromCenter() {
		BoardBean boardBean = new BoardBean(7, 7);

		WarCreatureBean warCreatureBean = createCreature(3, 3);
		boardBean.addCreature(warCreatureBean);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		AvailableMoves availableMoves = new AvailableMoves(warCreatureBean, simpleBoard);
		availableMoves.calc();

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);

		assertEquals(28, movesList.size());
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
