package hwm.dao;

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
	public void simpleCreate() {
		createSimplePlayer();

		Page<PlayerEntity> page = playerEntityDao.findAll(pageable);
		assertEquals(1L, page.getTotalElements());
		assertEquals(1, page.getContent().size());
		assertEquals(1, page.getTotalPages());

		assertEquals(1, page.getContent().get(0).getLevel());
		assertEquals(0, page.getContent().get(0).getExperience());
		assertEquals("0.00", page.getContent().get(0).getSkills().getKnight().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getNecro().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getElf().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getDarkElf().toString());
		assertEquals("0.00", page.getContent().get(0).getSkills().getMage().toString());
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

		playerEntityDao.save(playerEntity);

		return playerEntity;
	}
}
