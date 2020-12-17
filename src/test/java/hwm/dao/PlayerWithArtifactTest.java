package hwm.dao;

import hwm.arts.SimpleAxe;
import hwm.arts.SimpleEarRing;
import hwm.domain.ArtifactEntity;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class PlayerWithArtifactTest {

	@Autowired
	private PlayerEntityDao playerEntityDao;
	@Autowired
	private ArtifactEntityDao artifactEntityDao;

	Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

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
	public void addAndRemoveAxesForPlayer() {
		ArtifactEntity axe1 = new SimpleAxe().artifact();
		ArtifactEntity axe2 = new SimpleAxe().artifact();
		artifactEntityDao.save(axe1);
		artifactEntityDao.save(axe2);

		PlayerEntity player1 = createSimplePlayer();
		player1.addArtifact(axe1);
		player1.addArtifact(axe2);
		playerEntityDao.save(player1);


		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(2, player1.artifacts().size());


		player1.removeArtifact(axe1.id().toString());
		playerEntityDao.save(player1);


		page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(1, player1.artifacts().size());

		Page<ArtifactEntity> artifactPage = artifactEntityDao.findAll(pageable);
		assertEquals(2, artifactPage.getContent().size());

		ArtifactEntity detachedAxe = artifactPage.getContent().stream().filter(it -> it.id().equals(axe1.id())).findFirst().get();
		assertNull(detachedAxe.getPlayerEntity());
		assertEquals(player1.id(), detachedAxe.getOwnedBy());

		ArtifactEntity attachedAxe = artifactPage.getContent().stream().filter(it -> it.id().equals(axe2.id())).findFirst().get();
		assertNotNull(attachedAxe.getPlayerEntity());
		assertNull(attachedAxe.getOwnedBy());
	}

	@Test
	public void putOnAndTakeOffAxes() {
		ArtifactEntity axe1 = new SimpleAxe().artifact();
		ArtifactEntity axe2 = new SimpleAxe().artifact();
		artifactEntityDao.save(axe1);
		artifactEntityDao.save(axe2);

		PlayerEntity player1 = createSimplePlayer();
		player1.addArtifact(axe1);
		player1.addArtifact(axe2);
		playerEntityDao.save(player1);

		boolean putOnResult = player1.putOnArtifact(axe1.id().toString());
		assertTrue(putOnResult);
		putOnResult = player1.putOnArtifact(axe1.id().toString());
		assertFalse(putOnResult, "can not putOn the same axe");
		putOnResult = player1.putOnArtifact(axe2.id().toString());
		assertFalse(putOnResult, "can not putOn any other weapon there");
		playerEntityDao.save(player1);


		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(2, player1.artifacts().size());
		assertEquals(1, player1.artifacts().stream().filter(ArtifactEntity::isOn).count());


		boolean takeOffResult = player1.takeOffArtifact(axe2.id().toString());
		assertFalse(takeOffResult, "can not takeOff axe that is not on");
		takeOffResult = player1.takeOffArtifact(axe1.id().toString());
		assertTrue(takeOffResult);
		takeOffResult = player1.takeOffArtifact(axe1.id().toString());
		assertFalse(takeOffResult, "can not takeOff axe that is not on");
		playerEntityDao.save(player1);


		page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(2, player1.artifacts().size());
		assertEquals(0, player1.artifacts().stream().filter(ArtifactEntity::isOn).count());
	}

	@Test
	public void putOnAndTakeOffEarrings() {
		ArtifactEntity earring1 = new SimpleEarRing().artifact();
		ArtifactEntity earring2 = new SimpleEarRing().artifact();
		ArtifactEntity earring3 = new SimpleEarRing().artifact();
		artifactEntityDao.save(earring1);
		artifactEntityDao.save(earring2);
		artifactEntityDao.save(earring3);

		PlayerEntity player1 = createSimplePlayer();
		player1.addArtifact(earring1);
		player1.addArtifact(earring2);
		player1.addArtifact(earring3);
		playerEntityDao.save(player1);

		boolean putOnResult = player1.putOnArtifact(earring1.id().toString());
		assertTrue(putOnResult);
		putOnResult = player1.putOnArtifact(earring1.id().toString());
		assertFalse(putOnResult, "can not put on the same item twice");

		putOnResult = player1.putOnArtifact(earring2.id().toString());
		assertTrue(putOnResult);
		putOnResult = player1.putOnArtifact(earring2.id().toString());
		assertFalse(putOnResult, "can not put on the same item twice");

		putOnResult = player1.putOnArtifact(earring3.id().toString());
		assertFalse(putOnResult, "no available slots");
		playerEntityDao.save(player1);


		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(3, player1.artifacts().size());
		assertEquals(2, player1.artifacts().stream().filter(ArtifactEntity::isOn).count());


		boolean takeOffResult = player1.takeOffArtifact(earring3.id().toString());
		assertFalse(takeOffResult, "can not takeOff item that is not on");
		takeOffResult = player1.takeOffArtifact(earring2.id().toString());
		assertTrue(takeOffResult);
		takeOffResult = player1.takeOffArtifact(earring2.id().toString());
		assertFalse(takeOffResult, "can not takeOff item that is not on");
		playerEntityDao.save(player1);

		page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(3, player1.artifacts().size());
		assertEquals(1, player1.artifacts().stream().filter(ArtifactEntity::isOn).count());
	}

	@Test
	public void durabilityAfterFight() {
		ArtifactEntity earring1 = new SimpleEarRing().artifact();
		ArtifactEntity earring2 = new SimpleEarRing().artifact();
		ArtifactEntity earring3 = new SimpleEarRing().artifact();
		artifactEntityDao.save(earring1);
		artifactEntityDao.save(earring2);
		artifactEntityDao.save(earring3);

		ArtifactEntity axe1 = new SimpleAxe().artifact();
		ArtifactEntity axe2 = new SimpleAxe().artifact();
		artifactEntityDao.save(axe1);
		artifactEntityDao.save(axe2);

		PlayerEntity player1 = createSimplePlayer();
		player1.addArtifact(earring1);
		player1.addArtifact(earring2);
		player1.addArtifact(earring3);
		player1.addArtifact(axe1);
		player1.addArtifact(axe2);
		playerEntityDao.save(player1);

		assertTrue(player1.putOnArtifact(earring1.id().toString()));
		assertTrue(player1.putOnArtifact(earring2.id().toString()));
		assertTrue(player1.putOnArtifact(axe1.id().toString()));
		playerEntityDao.save(player1);


		player1.decreaseDurabilityAfterFight();
		playerEntityDao.save(player1);


		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(5, player1.artifacts().size());
		assertEquals(3, player1.artifacts().stream().filter(ArtifactEntity::isOn).count());
		//check durability
		assertEquals("1/2", player1.artifacts().stream().filter(it -> it.id().equals(earring1.id())).findFirst().get().durabilityStr());
		assertEquals("1/2", player1.artifacts().stream().filter(it -> it.id().equals(earring2.id())).findFirst().get().durabilityStr());
		assertEquals("2/2", player1.artifacts().stream().filter(it -> it.id().equals(earring3.id())).findFirst().get().durabilityStr());
		assertEquals("2/3", player1.artifacts().stream().filter(it -> it.id().equals(axe1.id())).findFirst().get().durabilityStr());
		assertEquals("3/3", player1.artifacts().stream().filter(it -> it.id().equals(axe2.id())).findFirst().get().durabilityStr());


		player1.decreaseDurabilityAfterFight();
		playerEntityDao.save(player1);

		page = playerEntityDao.findAll(pageable);
		assertEquals(1, page.getContent().size());
		player1 = page.getContent().get(0);
		assertEquals(5, player1.artifacts().size());
		assertEquals(1, player1.artifacts().stream().filter(ArtifactEntity::isOn).count());
		//check durability
		assertEquals("0/2", player1.artifacts().stream().filter(it -> it.id().equals(earring1.id())).findFirst().get().durabilityStr());
		assertEquals("0/2", player1.artifacts().stream().filter(it -> it.id().equals(earring2.id())).findFirst().get().durabilityStr());
		assertEquals("2/2", player1.artifacts().stream().filter(it -> it.id().equals(earring3.id())).findFirst().get().durabilityStr());
		assertEquals("1/3", player1.artifacts().stream().filter(it -> it.id().equals(axe1.id())).findFirst().get().durabilityStr());
		assertEquals("3/3", player1.artifacts().stream().filter(it -> it.id().equals(axe2.id())).findFirst().get().durabilityStr());
	}

	private PlayerEntity createSimplePlayer() {
		PlayerEntity playerEntity = new PlayerEntity("Batman");
		playerEntity.setFaction(Faction.Elf);
		playerEntity.getBaseParams().setAttack(1);
		playerEntity.getBaseParams().setDefence(2);
		playerEntity.setLevel(1);

		playerEntityDao.save(playerEntity);

		return playerEntity;
	}
}
