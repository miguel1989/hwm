package hwm.dto;


import hwm.domain.PlayerEntity;
import hwm.tables.SkillTable;

import java.math.BigDecimal;

public class FightPlayerBean {

	public String id;
	public String name;
	public int level;
	public BaseParamsBean baseParams;
	public BaseParamsBean creatureParams;


	public FightPlayerBean(PlayerEntity playerEntity) {
		this.id = playerEntity.id().toString();
		this.name = playerEntity.getName();
		this.level = playerEntity.getLevel();
		this.baseParams = new BaseParamsBean(playerEntity.finalParams());

		//creature params from skill
		BigDecimal currentSkill = playerEntity.currentSkill();
		creatureParams = SkillTable.calcParams(currentSkill);
		//creatures
	}
}
