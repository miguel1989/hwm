package hwm.dao;

import hwm.domain.ArmyEntity;
import hwm.domain.PlayerEntity;
import hwm.dto.FightResult;
import hwm.enums.Faction;
import hwm.util.BigDecimalUtils;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PlayerEntityDaoTest {

	Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

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
	public void updateArmy() {
		createSimplePlayer();

		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(1, page.getTotalPages());

		final PlayerEntity player = page.getContent().get(0);
		assertEquals(1, player.getLevel());
		assertEquals(0, player.getExperience());
		assertEquals("0.00", player.getSkills().getKnight().toString());
		assertEquals("0.00", player.getSkills().getNecro().toString());
		assertEquals("0.00", player.getSkills().getElf().toString());
		assertEquals("0.00", player.getSkills().getDarkElf().toString());
		assertEquals("0.00", player.getSkills().getMage().toString());

		assertEquals(0, player.artifacts().size());

		Collection<ArmyEntity> armies = player.armies();
		assertEquals(2, armies.size());
		ArmyEntity currArmy = armies.stream().filter(it -> it.getFaction().equals(player.getFaction())).findFirst().get();
		assertEquals(10, currArmy.getLevel1Count());
		currArmy.setLevel1Count(25);
		playerEntityDao.save(player);

		page = playerEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		PlayerEntity player1 = page.getContent().get(0);
		armies = player1.armies();
		assertEquals(2, armies.size());
		currArmy = armies.stream().filter(it -> it.getFaction().equals(player1.getFaction())).findFirst().get();
		assertEquals(25, currArmy.getLevel1Count());
	}

	@Test
	public void addExpAndSkill() {
		PlayerEntity playerEntity = createSimplePlayer();

		FightResult fightResult = new FightResult(BigDecimalUtils.fromStr("1.92"), 1234);

		playerEntity.addExperience(fightResult.experience);
		playerEntity.addSkill(fightResult.skill);
		playerEntityDao.save(playerEntity);

		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(1, page.getTotalPages());

		assertEquals(1, page.getContent().get(0).getLevel());
		assertEquals(1234, page.getContent().get(0).getExperience());
		assertEquals("0.00", page.getContent().get(0).getSkills().getKnight().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getNecro().toString());
		assertEquals("1.92", page.getContent().get(0).getSkills().getElf().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getDarkElf().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getMage().toString());
	}

	private PlayerEntity createSimplePlayer() {
		PlayerEntity playerEntity = new PlayerEntity("Batman");
		playerEntity.setFaction(Faction.Elf);
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
}
