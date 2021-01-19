package hwm.game;

import hwm.dto.BoardBean;
import hwm.dto.WarCreatureBean;
import hwm.util.JacksonJsonSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvailableMovesWithCreatures {

	@Test
	public void moveFromTopLeftCorner_blocked() {
		BoardBean boardBean = new BoardBean(6, 6);

		WarCreatureBean creature1 = createCreature(0, 0, 3);
		WarCreatureBean creature2 = createCreature(1, 0, 3);
		WarCreatureBean creature3 = createCreature(1, 1, 3);
		WarCreatureBean creature4 = createCreature(0, 1, 3);

		boardBean.addCreature(creature1);
		boardBean.addCreature(creature2);
		boardBean.addCreature(creature3);
		boardBean.addCreature(creature4);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		AvailableMoves availableMoves = new AvailableMoves(creature1, simpleBoard);

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);
		assertEquals(0, movesList.size(), "creature1 can not move at all, it is blocked");


		simpleBoard = boardBean.toSimpleBoard();
		availableMoves = new AvailableMoves(creature2, simpleBoard);
		movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);
		assertEquals(6, movesList.size());


		simpleBoard = boardBean.toSimpleBoard();
		availableMoves = new AvailableMoves(creature3, simpleBoard);
		movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);
		assertEquals(14, movesList.size());
	}

	@Test
	public void bigBoard() {
		BoardBean boardBean = new BoardBean(20, 20);

		WarCreatureBean creature1 = createCreature(19, 19, 8);
		WarCreatureBean creature2 = createCreature(18, 19, 3);
		WarCreatureBean creature3 = createCreature(18, 18, 3);

		boardBean.addCreature(creature1);
		boardBean.addCreature(creature2);
		boardBean.addCreature(creature3);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		StopWatch sw = new StopWatch();
		sw.start("availableMoves.calc");
		AvailableMoves availableMoves = new AvailableMoves(creature1, simpleBoard);
		sw.stop();

		AvailableMovesList movesList = availableMoves.movesList();
		System.out.println(simpleBoard.toStr());
		System.out.println(movesList);
		System.out.println(sw.prettyPrint());
		assertEquals(42, movesList.size(), "creature1 can not move at all, it is blocked");
	}

	private WarCreatureBean createCreature(int x, int y, int speed) {
		WarCreatureBean warCreatureBean = new WarCreatureBean();
		warCreatureBean.x = x;
		warCreatureBean.y = y;
		warCreatureBean.currentCount = 1;
		warCreatureBean.paramsFinal.speed = speed;
		return warCreatureBean;
	}
}
