package hwm.dao;

import hwm.arts.SimpleAxe;
import hwm.arts.SimpleEarRing;
import hwm.creatures.Peasant;
import hwm.domain.*;
import hwm.dto.BoardBean;
import hwm.dto.TeamBean;
import hwm.dto.WarBean;
import hwm.dto.WarPlayerBean;
import hwm.enums.Faction;
import hwm.enums.TeamType;
import hwm.enums.WarType;
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

	JacksonJsonSerializer jacksonJsonSerializer = new JacksonJsonSerializer();

	@BeforeEach
	public void setup() {
		botPlayerDao.deleteAll();
		playerEntityDao.deleteAll();
		artifactEntityDao.deleteAll();
		warEntityDao.deleteAll();
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

		BotPlayerEntity botPlayer = createBot();

		WarBean warBean = new WarBean(WarType.HUNT);
		warBean.boardBean = new BoardBean(10, 10);

		WarPlayerBean warPlayerBean1 = new WarPlayerBean(player1, warBean.boardBean);
		WarPlayerBean warPlayerBean2 = new WarPlayerBean(botPlayer, warBean.boardBean);

		warBean.redTeam.addPlayer(warPlayerBean1);
		warBean.blueTeam.addPlayer(warPlayerBean2);

		warBean.redTeam.beforeBattlePreparation();
		warBean.blueTeam.beforeBattlePreparation();

		System.out.println(jacksonJsonSerializer.toJson(warBean));

		checkPlayerBean(warPlayerBean1);
		checkBotBean(warPlayerBean2);


		WarEntity warEntity = new WarEntity();
		warEntity.setType(warBean.type);
		warBean.redTeam.players.forEach(it -> warEntity.addRedTeamPlayer(it.id));
		warBean.blueTeam.players.forEach(it -> warEntity.addBlueTeamPlayer(it.id));
		warEntityDao.save(warEntity);

		Page<WarEntity> page = warEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(2, page.getContent().get(0).warTeams().size());
		assertTrue(page.getContent().get(0).warTeams().stream().anyMatch(it-> it.getType().equals(TeamType.RED)));
		assertTrue(page.getContent().get(0).warTeams().stream().anyMatch(it-> it.getType().equals(TeamType.BLUE)));
	}

	private void checkPlayerBean(WarPlayerBean playerBean) {
//		assertEquals(player1.getName(), WarPlayerBean.name);
		assertTrue(playerBean.hasHero);
		assertEquals(Faction.Knight, playerBean.faction);
		assertEquals(TeamType.RED, playerBean.teamType);
		assertEquals(1, playerBean.creatures.size());
		assertEquals("Peasant", playerBean.creatures.get(0).name);
		assertEquals(30, playerBean.creatures.get(0).countFinal);

		assertEquals(10, playerBean.creatures.get(0).paramsFinal.attack);
		assertEquals(9, playerBean.creatures.get(0).paramsFinal.defence);
		assertEquals(1, playerBean.creatures.get(0).paramsFinal.minDamage);
		assertEquals(1, playerBean.creatures.get(0).paramsFinal.maxDamage);
		assertEquals(4, playerBean.creatures.get(0).paramsFinal.hp);
		assertEquals(4, playerBean.creatures.get(0).paramsFinal.speed);
		assertEquals("8.4", playerBean.creatures.get(0).paramsFinal.initiative.toString());
		assertEquals(0, playerBean.creatures.get(0).paramsFinal.shots);
		assertEquals(0, playerBean.creatures.get(0).paramsFinal.range);
		assertEquals(0, playerBean.creatures.get(0).paramsFinal.mana);

		assertEquals(5, playerBean.creatures.get(0).paramsFinal.luck);
		assertEquals(5, playerBean.creatures.get(0).paramsFinal.morale);
	}

	private void checkBotBean(WarPlayerBean botBean) {
		assertFalse(botBean.hasHero);
		assertEquals(TeamType.BLUE, botBean.teamType);

		assertEquals(1, botBean.creatures.size());
		assertEquals("Peasant", botBean.creatures.get(0).name);
		assertEquals(10, botBean.creatures.get(0).countFinal);

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
		ArtifactEntity axe1 = new SimpleAxe().artifact();
		artifactEntityDao.save(earring1);
		artifactEntityDao.save(earring2);
		artifactEntityDao.save(axe1);

		player1.addArtifact(earring1);
		player1.addArtifact(earring2);
		player1.addArtifact(axe1);
		playerEntityDao.save(player1);

		//put on all available arts
		assertTrue(player1.putOnArtifact(earring1.id().toString()));
		assertTrue(player1.putOnArtifact(earring2.id().toString()));
		assertTrue(player1.putOnArtifact(axe1.id().toString()));
		playerEntityDao.save(player1);
	}

	private BotPlayerEntity createBot() {
		Peasant peasant = new Peasant(10);

		BotPlayerEntity botPlayer = new BotPlayerEntity();
		botPlayer.setName(peasant.name + " (" + peasant.count + ")");

		CreatureEntity creatureEntity = new CreatureEntity(botPlayer, peasant);
		botPlayer.addCreature(creatureEntity);
		botPlayerDao.save(botPlayer);

		return botPlayer;
	}
}