package hwm.dao;

import hwm.arts.SimpleAxe;
import hwm.arts.SimpleEarRing;
import hwm.arts.SimplePendant;
import hwm.domain.*;
import hwm.dto.TurnBean;
import hwm.dto.WarBean;
import hwm.dto.WarPlayerBean;
import hwm.enums.Faction;
import hwm.enums.TeamType;
import hwm.enums.WarStatus;
import hwm.service.WarHuntService;
import hwm.util.JacksonJsonSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class WarDaoTest {

	Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

	@Autowired
	BotPlayerDao botPlayerDao;
	@Autowired
	PlayerEntityDao playerEntityDao;
	@Autowired
	ArtifactEntityDao artifactEntityDao;
	@Autowired
	WarEntityDao warEntityDao;
	@Autowired
	WarHistoryEntityDao warHistoryEntityDao;
	@Autowired
	WarActionLogEntityDao warActionLogEntityDao;

	JacksonJsonSerializer jacksonJsonSerializer = new JacksonJsonSerializer();
	WarHuntService warHuntService;

	@BeforeEach
	public void setup() {
		botPlayerDao.deleteAll();
		playerEntityDao.deleteAll();
		artifactEntityDao.deleteAll();
		warEntityDao.deleteAll();

		warHuntService = new WarHuntService(
				playerEntityDao,
				botPlayerDao,
				warEntityDao,
				warHistoryEntityDao,
				warActionLogEntityDao,
				jacksonJsonSerializer
		);
	}

	@AfterEach
	public void tearDown() {
		botPlayerDao.deleteAll();
		playerEntityDao.deleteAll();
		artifactEntityDao.deleteAll();
		warEntityDao.deleteAll();
	}

	@Test
	public void war() {
		PlayerEntity player1 = createSimplePlayer();
		putOnArtifacts(player1);

		String warId = warHuntService.create(player1.id().toString());
		WarEntity warEntity = warEntityDao.findById(UUID.fromString(warId)).get();
		WarHistoryEntity lastHistoryEntry = warHistoryEntityDao.findTopByWarIdOrderByCreatedAtDesc(warEntity.id());
		WarBean warBean = jacksonJsonSerializer.restoreWar(lastHistoryEntry.getJson());


		checkPlayerBean(warBean.redTeam.players.get(0));
		checkBotBean(warBean.blueTeam.players.get(0));

		//todo later on this should be done with cron and/or after player completes his preparation
		warHuntService.start(warId);

		Page<WarEntity> page = warEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(2, page.getContent().get(0).teams().size());
		assertEquals(WarStatus.STARTED, page.getContent().get(0).getStatus());
		assertTrue(page.getContent().get(0).teams().stream().anyMatch(it -> it.getType().equals(TeamType.RED)));
		assertTrue(page.getContent().get(0).teams().stream().anyMatch(it -> it.getType().equals(TeamType.BLUE)));

		/////////////////////////// attempt to def by hero.peasant
		lastHistoryEntry = warHistoryEntityDao.findTopByWarIdOrderByCreatedAtDesc(warEntity.id());
		warBean = jacksonJsonSerializer.restoreWar(lastHistoryEntry.getJson());

		boolean turnResult = warHuntService.playerTurn(warId, TurnBean.defence(player1.id().toString(), warBean.redTeam.players.get(0).creatures.get(1).id.toString()));
		assertTrue(turnResult);

		/////////////////////////// attempt to wait by hero
		turnResult = warHuntService.playerTurn(warId, TurnBean.await(player1.id().toString(), warBean.redTeam.players.get(0).creatures.get(0).id.toString()));
		assertTrue(turnResult);

		Collection<WarActionLogEntity> warActions = warActionLogEntityDao.findAllByWarIdOrderByCreatedAt(warEntity.id());
		assertEquals(2, warActions.size());

		/////////////////////////// attempt to move the neutral peasant -> creature can not move there
		turnResult = warHuntService.playerTurn(warId,
				TurnBean.move(
						warBean.blueTeam.players.get(0).id,
						warBean.blueTeam.players.get(0).creatures.get(0).id.toString(),
						0,
						0
				));
		assertFalse(turnResult);

		turnResult = warHuntService.playerTurn(warId,
				TurnBean.move(
						warBean.blueTeam.players.get(0).id,
						warBean.blueTeam.players.get(0).creatures.get(0).id.toString(),
						7,
						0
				));
		assertTrue(turnResult);
	}

	private void checkPlayerBean(WarPlayerBean playerBean) {
//		assertEquals(player1.getName(), WarPlayerBean.name);
		assertTrue(playerBean.hasHero);
		assertEquals(Faction.Knight, playerBean.faction);
		assertEquals(2, playerBean.creatures.size());

		assertEquals("Batman[1]", playerBean.creatures.get(0).name);
		assertTrue(playerBean.creatures.get(0).isHero);
		assertEquals("10", playerBean.creatures.get(0).paramsFinal.initiative.toString());

		assertEquals("Peasant", playerBean.creatures.get(1).name);
		assertFalse(playerBean.creatures.get(1).isHero);
		assertEquals(30, playerBean.creatures.get(1).count);
		assertEquals(30, playerBean.creatures.get(1).currentCount);

		assertEquals(10, playerBean.creatures.get(1).paramsFinal.attack);
		assertEquals(9, playerBean.creatures.get(1).paramsFinal.defence);
		assertEquals(1, playerBean.creatures.get(1).paramsFinal.minDamage);
		assertEquals(1, playerBean.creatures.get(1).paramsFinal.maxDamage);
		assertEquals(4, playerBean.creatures.get(1).paramsFinal.hp);
		assertEquals(4, playerBean.creatures.get(1).paramsFinal.speed);
		assertEquals("12.3", playerBean.creatures.get(1).paramsFinal.initiative.toString());
		assertEquals(0, playerBean.creatures.get(1).paramsFinal.shots);
		assertEquals(0, playerBean.creatures.get(1).paramsFinal.range);
		assertEquals(0, playerBean.creatures.get(1).paramsFinal.mana);

		assertEquals(5, playerBean.creatures.get(1).paramsFinal.luck);
		assertEquals(5, playerBean.creatures.get(1).paramsFinal.morale);
	}

	private void checkBotBean(WarPlayerBean botBean) {
		assertFalse(botBean.hasHero);

		assertEquals(1, botBean.creatures.size());
		assertEquals("Peasant", botBean.creatures.get(0).name);
		assertEquals(10, botBean.creatures.get(0).count);
		assertEquals(10, botBean.creatures.get(0).currentCount);

		assertEquals(1, botBean.creatures.get(0).paramsFinal.attack);
		assertEquals(1, botBean.creatures.get(0).paramsFinal.defence);
		assertEquals(1, botBean.creatures.get(0).paramsFinal.minDamage);
		assertEquals(1, botBean.creatures.get(0).paramsFinal.maxDamage);
		assertEquals(4, botBean.creatures.get(0).paramsFinal.hp);
		assertEquals(4, botBean.creatures.get(0).paramsFinal.speed);
		assertEquals("8.0", botBean.creatures.get(0).paramsFinal.initiative.toString());
		assertEquals(0, botBean.creatures.get(0).paramsFinal.shots);
		assertEquals(0, botBean.creatures.get(0).paramsFinal.range);
		assertEquals(0, botBean.creatures.get(0).paramsFinal.mana);

		assertEquals(0, botBean.creatures.get(0).paramsFinal.luck);
		assertEquals(0, botBean.creatures.get(0).paramsFinal.morale); //todo should be 1
	}

	private PlayerEntity createSimplePlayer() {
		PlayerEntity playerEntity = new PlayerEntity("Batman");
		playerEntity.setFaction(Faction.Knight);
		playerEntity.getBaseParams().setAttack(1);
		playerEntity.getBaseParams().setDefence(2);
		playerEntity.setLevel(1);

		ArmyEntity elfArmy = new ArmyEntity(playerEntity, Faction.Elf);
		elfArmy.setLevel1Count(10);

		ArmyEntity knightArmy = new ArmyEntity(playerEntity, Faction.Knight);
		knightArmy.setLevel1Count(30);

		playerEntity.addArmy(elfArmy);
		playerEntity.addArmy(knightArmy);

		playerEntityDao.save(playerEntity);

		return playerEntity;
	}

	private void putOnArtifacts(PlayerEntity player1) {
		ArtifactEntity earring1 = new SimpleEarRing().artifact();
		ArtifactEntity earring2 = new SimpleEarRing().artifact();
		ArtifactEntity axe = new SimpleAxe().artifact();
		ArtifactEntity pendant = new SimplePendant().artifact();
		artifactEntityDao.save(earring1);
		artifactEntityDao.save(earring2);
		artifactEntityDao.save(axe);
		artifactEntityDao.save(pendant);

		player1.addArtifact(earring1);
		player1.addArtifact(earring2);
		player1.addArtifact(axe);
		player1.addArtifact(pendant);
		playerEntityDao.save(player1);

		//put on all available arts
		assertTrue(player1.putOnArtifact(earring1.id().toString()));
		assertTrue(player1.putOnArtifact(earring2.id().toString()));
		assertTrue(player1.putOnArtifact(axe.id().toString()));
		assertTrue(player1.putOnArtifact(pendant.id().toString()));
		playerEntityDao.save(player1);
	}
}
