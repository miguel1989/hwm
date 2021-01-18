package hwm.game;

import hwm.dto.BoardBean;
import hwm.dto.WarCreatureBean;
import org.junit.jupiter.api.Test;

public class AvailableMovesTest {

	@Test
	public void moveFromCornerNoObstacles() {
		WarCreatureBean warCreatureBean = new WarCreatureBean();
		warCreatureBean.x = 0;
		warCreatureBean.y = 0;
		warCreatureBean.currentCount = 1;
		warCreatureBean.paramsFinal.hp = 1;
		warCreatureBean.paramsFinal.speed = 5;

		BoardBean boardBean = new BoardBean(12, 12);
		boardBean.addCreature(warCreatureBean);

		SimpleBoard simpleBoard = boardBean.toSimpleBoard();
		System.out.println(simpleBoard.toStr());
		AvailableMoves availableMoves = new AvailableMoves(warCreatureBean, simpleBoard);
		availableMoves.calc();

		System.out.println("----------------------------------------");
		System.out.println(simpleBoard.toStr());
	}
}
