package hwm.dao;

import hwm.domain.Artifact;
import hwm.arts.SimpleAxe;
import hwm.game.enums.ArtifactType;
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
public class ArtifactDaoTest {

	@Autowired
	private ArtifactDao artifactDao;

	@BeforeEach
	public void setup() {
		artifactDao.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		artifactDao.deleteAll();
	}

	@Test
	public void simpleAxe_init_without_player() {
		artifactDao.save(new SimpleAxe().artifact());

		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
		Page<Artifact> page = artifactDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(1, page.getTotalPages());

		Artifact art = page.getContent().get(0);

		assertNotNull(art.id());
		assertNull(art.getPlayerEntity());
		assertEquals("Simple Axe", art.getName());
		assertEquals(1, art.getForLevel());
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
}
