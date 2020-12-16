package hwm.dao;

import hwm.arts.SimpleAxe;
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
	public void addAndRemoveArtifactForPlayer() {
		ArtifactEntity axe1 = new SimpleAxe().artifact();
		ArtifactEntity axe2 = new SimpleAxe().artifact();
		artifactEntityDao.save(axe1);
		artifactEntityDao.save(axe2);

		PlayerEntity player1 = createSimplePlayer();
		player1.addArtifact(axe1);
		player1.addArtifact(axe2);
		playerEntityDao.save(player1);


		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
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
