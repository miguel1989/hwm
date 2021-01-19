package hwm.game;

import hwm.dto.WarCreatureBean;
import hwm.enums.SimpleBoardCell;

public class AvailableMoves {
	private static final float SPEED_POINTS_DIAGONAL = 1.5f;
	private static final int SPEED_POINTS_NORMAL = 1;

	private final SimpleBoard simpleBoard;
	private final AvailableMovesList movesList = new AvailableMovesList();

	private final Integer curX;
	private final Integer curY;
	private final Integer speed;

	public AvailableMoves(WarCreatureBean warCreatureBean, SimpleBoard simpleBoard) {
		this.curX = warCreatureBean.x;
		this.curY = warCreatureBean.y;
		this.speed = warCreatureBean.paramsFinal.speed;
		this.simpleBoard = simpleBoard;
		simpleBoard.cells.get(curY).set(curX, SimpleBoardCell.SELF_CREATURE);

		this.calc();
	}

	private void calc() {
		//todo take into account creatures skills like: fly,isBig,teleport
		recurCalc(curX, curY, speed);
	}

	private void recurCalc(int x, int y, float speedPoints) {
		if (speedPoints < 0) {
			return;
		}

		if (SimpleBoardCell.EMPTY.equals(this.simpleBoard.cells.get(y).get(x))) {
			this.simpleBoard.cells.get(y).set(x, SimpleBoardCell.AVAILABLE_TO_MOVE);
			this.movesList.add(new Point(x, y));
		}

		boolean canMoveLeft = (x - 1) >= 0 && isCellAvailable(this.simpleBoard.cells.get(y).get(x - 1));
		boolean canMoveUp = (y - 1) >= 0 && isCellAvailable(this.simpleBoard.cells.get(y - 1).get(x));
		boolean canMoveRight = (x + 1) < this.simpleBoard.width && isCellAvailable(this.simpleBoard.cells.get(y).get(x + 1));
		boolean canMoveDown = (y + 1) < this.simpleBoard.height && isCellAvailable(this.simpleBoard.cells.get(y + 1).get(x));
		// firstly move horizontally or vertically
		if (canMoveLeft) {
			recurCalc(x - 1, y, speedPoints - SPEED_POINTS_NORMAL);
		}
		if (canMoveUp) {
			recurCalc(x, y - 1, speedPoints - SPEED_POINTS_NORMAL);
		}
		if (canMoveRight) {
			recurCalc(x + 1, y, speedPoints - SPEED_POINTS_NORMAL);
		}
		if (canMoveDown) {
			recurCalc(x, y + 1, speedPoints - SPEED_POINTS_NORMAL);
		}
		//then move diagonally
		if (canMoveLeft && canMoveUp) {
			recurCalc(x - 1, y - 1, speedPoints - SPEED_POINTS_DIAGONAL);
		}
		if (canMoveRight && canMoveUp) {
			recurCalc(x + 1, y - 1, speedPoints - SPEED_POINTS_DIAGONAL);
		}
		if (canMoveLeft && canMoveDown) {
			recurCalc(x - 1, y + 1, speedPoints - SPEED_POINTS_DIAGONAL);
		}
		if (canMoveRight && canMoveDown) {
			recurCalc(x + 1, y + 1, speedPoints - SPEED_POINTS_DIAGONAL);
		}
	}

	private boolean isCellAvailable(SimpleBoardCell cell) {
		return SimpleBoardCell.AVAILABLE_TO_MOVE.equals(cell) || SimpleBoardCell.EMPTY.equals(cell);
	}

	public AvailableMovesList movesList() {
		return this.movesList;
	}
}
