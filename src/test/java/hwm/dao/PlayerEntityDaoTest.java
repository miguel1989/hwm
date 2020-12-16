package hwm.dao;

import hwm.domain.PlayerEntity;
import hwm.game.enums.Faction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PlayerEntityDaoTest {

	@Autowired
	private PlayerEntityDao playerEntityDao;

	@BeforeEach
	public void setup() {
		playerEntityDao.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		playerEntityDao.deleteAll();
	}

	@Test
	public void registerElf() {
		PlayerEntity playerEntity = new PlayerEntity("myLittleElf");
		playerEntity.setFaction(Faction.Elf);
		playerEntity.setInitiative(new BigDecimal(3));
		playerEntity.setMorale(1);
		playerEntity.setLevel(1);

		playerEntityDao.save(playerEntity);

		assertTrue(playerEntityDao.findAll().iterator().hasNext());
	}
}
