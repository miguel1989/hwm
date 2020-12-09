package hwm.game;

import hwm.exceptions.WrongCoordinatesProvided;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

	@Test
	public void toStr() {
		int height = 3;
		int width = 4;
		Board board = new Board(width, height);
		System.out.println(board.toString());
		assertEquals(height, board.cells().size());
		assertEquals(width, board.cells().get(0).size());
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				assertNotNull(board.findCell(i, j));
			}
		}
	}

	@Test
	public void findCellByWrongCoordinates() {
		int height = 3;
		int width = 4;
		Board board = new Board(width, height);

		Assertions.assertThrows(WrongCoordinatesProvided.class, () -> {
			board.findCell(5, 2);
		});

		Assertions.assertThrows(WrongCoordinatesProvided.class, () -> {
			board.findCell(2, 5);
		});

		Assertions.assertThrows(WrongCoordinatesProvided.class, () -> {
			board.findCell(-1, 0);
		});

		Assertions.assertThrows(WrongCoordinatesProvided.class, () -> {
			board.findCell(0, -1);
		});
	}
}
