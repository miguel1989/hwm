package hwm.dao;

import hwm.domain.PlayerEntity;
import hwm.game.enums.Faction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PlayerEntityDaoTest {

	@Autowired
	private PlayerEntityDao playerEntityDao;
	@Autowired
	private ArtifactEntityDao artifactEntityDao;

	@BeforeEach
	public void setup() {
		playerEntityDao.deleteAll();
		artifactEntityDao.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		playerEntityDao.deleteAll();
		artifactEntityDao.deleteAll();
	}

	@Test
	public void simpleCreate() {
		PlayerEntity playerEntity = new PlayerEntity("myLittleElf");
		playerEntity.setFaction(Faction.Elf);
		playerEntity.getBaseParams().setAttack(1);
		playerEntity.getBaseParams().setDefence(2);
		playerEntity.setLevel(1);

		playerEntityDao.save(playerEntity);

		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(1, page.getTotalPages());
	}
}
