package hwm.dao;

import hwm.domain.ArtifactEntity;
import hwm.arts.SimpleAxe;
import hwm.enums.ArtifactType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArtifactEntityDaoTest {

	@Autowired
	private ArtifactEntityDao artifactEntityDao;

	@BeforeEach
	public void setup() {
		artifactEntityDao.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		artifactEntityDao.deleteAll();
	}

	@Test
	public void simpleAxe_init_without_player() {
		artifactEntityDao.save(new SimpleAxe().artifact());

		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
		Page<ArtifactEntity> page = artifactEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(1, page.getTotalPages());

		ArtifactEntity art = page.getContent().get(0);

		assertNotNull(art.id());
		assertNull(art.getPlayerEntity());
		assertEquals("Simple Axe", art.getName());
		assertEquals(1, art.getForLevel());
		assertEquals("3/3", art.durabilityStr());
		assertFalse(art.isOn());
		assertFalse(art.isBroken());

		assertEquals(4, art.getAttack());
		assertEquals(1, art.getDefence());
		assertEquals(1, art.getInitiative());
		assertEquals(1, art.getLuck());
		assertEquals(0, art.getMorale());
		assertEquals(0, art.getMagicPower());
		assertEquals(0, art.getKnowledge());

		assertEquals(ArtifactType.WEAPON_LEFT_HAND, art.getType());
	}

	@Test
	public void simpleAxe_without_player_putOn_takeOff() {
		ArtifactEntity art = new SimpleAxe().artifact();
		artifactEntityDao.save(art);

		assertNotNull(art.id());
		assertNull(art.getPlayerEntity());
		assertEquals("Simple Axe", art.getName());
		assertEquals("3/3", art.durabilityStr());
		assertFalse(art.isOn());
		assertFalse(art.isBroken());

		//decreaseDurability for art that is not on -> nothing should happen
		art.decreaseDurability();
		assertEquals("3/3", art.durabilityStr());

		//Put on the artifact and decrease
		art.putOn(1);
		assertTrue(art.isOn());
		assertFalse(art.isBroken());

		art.decreaseDurability();
		assertEquals("2/3", art.durabilityStr());
		assertTrue(art.isOn());
		assertFalse(art.isBroken());

		art.decreaseDurability();
		assertEquals("1/3", art.durabilityStr());
		assertTrue(art.isOn());
		assertFalse(art.isBroken());

		art.decreaseDurability();
		assertEquals("0/3", art.durabilityStr());
		assertFalse(art.isOn());
		assertTrue(art.isBroken());
	}
}
