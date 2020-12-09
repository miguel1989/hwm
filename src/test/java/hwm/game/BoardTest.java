package hwm.game;

import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	public void toStr() {
		Board board = new Board(4, 3);
		System.out.println(board.toString());
	}
}
