package hwm.atb;


import hwm.creatures.Bowman;
import hwm.creatures.Horse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ATBSimulationTest {

	@Test
	public void twoCreaturesStartFromZero() {
		Horse horse = new Horse(BigDecimal.ZERO);//16 ini
		Bowman bowman = new Bowman(BigDecimal.ZERO);//12 ini

		ATBSimulation atbSimulation = new ATBSimulation();
		atbSimulation.addCreature(horse);
		atbSimulation.addCreature(bowman);

		atbSimulation.run(3);

		//System.out.println(atbSimulation.moveList());
//		atbSimulation.moveList().forEach(it -> {
//			System.out.println(it.toString());
//		});
		long horseMoves = atbSimulation.moveList().stream().filter(it -> it.name().equals("Horse")).count();
		long bowmanMoves = atbSimulation.moveList().stream().filter(it -> it.name().equals("Bowman")).count();

		Assertions.assertEquals(4, horseMoves);
		Assertions.assertEquals(3, bowmanMoves);


		atbSimulation = new ATBSimulation();
		atbSimulation.addCreature(horse);
		atbSimulation.addCreature(bowman);
		atbSimulation.run(4);

		horseMoves = atbSimulation.moveList().stream().filter(it -> it.name().equals("Horse")).count();
		bowmanMoves = atbSimulation.moveList().stream().filter(it -> it.name().equals("Bowman")).count();

		Assertions.assertEquals(6, horseMoves);
		Assertions.assertEquals(4, bowmanMoves);
	}

}
