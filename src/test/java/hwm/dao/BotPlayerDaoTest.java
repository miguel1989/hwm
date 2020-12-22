package hwm.dao;

import hwm.creatures.Peasant;
import hwm.domain.BotPlayerEntity;
import hwm.domain.CreatureEntity;
import hwm.domain.PlayerEntity;
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
public class BotPlayerDaoTest {

	Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

	@Autowired
	private BotPlayerDao botPlayerDao;

	@BeforeEach
	public void setup() {
		botPlayerDao.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		botPlayerDao.deleteAll();
	}

	@Test
	public void simpleCreate() {
		Peasant peasant = new Peasant(10);

		BotPlayerEntity botPlayer = new BotPlayerEntity();
		botPlayer.setName(peasant.name + " (" + peasant.count + ")");

		CreatureEntity creatureEntity = new CreatureEntity(botPlayer, peasant);
		botPlayer.addCreature(creatureEntity);
		botPlayerDao.save(botPlayer);


		Page<BotPlayerEntity> page = botPlayerDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());

		assertEquals(1, page.getContent().get(0).creatures().size());
		assertEquals(peasant.name, page.getContent().get(0).creatures().iterator().next().getName());
	}
}
