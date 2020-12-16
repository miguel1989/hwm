package hwm.dao;

import hwm.domain.PlayerEntity;
import hwm.game.enums.Faction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PlayerEntityDaoTest {

	@Autowired
	private PlayerEntityDao playerEntityDao;
	@Autowired
	private ArtifactDao artifactDao;

	@BeforeEach
	public void setup() {
		playerEntityDao.deleteAll();
		artifactDao.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		playerEntityDao.deleteAll();
		artifactDao.deleteAll();
	}

	@Test
	public void simpleCreate() {
		PlayerEntity playerEntity = new PlayerEntity("myLittleElf");
		playerEntity.setFaction(Faction.Elf);
		playerEntity.getBaseParams().setAttack(1);
		playerEntity.getBaseParams().setDefence(2);
		playerEntity.setLevel(1);

		playerEntityDao.save(playerEntity);

		assertTrue(playerEntityDao.findAll().iterator().hasNext());
	}
}
