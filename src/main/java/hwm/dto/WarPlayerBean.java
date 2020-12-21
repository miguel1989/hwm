package hwm.dto;


import hwm.creatures.Peasant;
import hwm.domain.PlayerEntity;
import hwm.tables.SkillTable;

import java.math.BigDecimal;

public class WarPlayerBean {

	public String id;
	public String name;
	public int level;
	public BaseParamsBean baseParams;
	public BaseParamsBean creatureParams;

	public WarPlayerBean(PlayerEntity playerEntity) {
		this.id = playerEntity.id().toString();
		this.name = playerEntity.getName();
		this.level = playerEntity.getLevel();
		this.baseParams = new BaseParamsBean(playerEntity.finalParams());

		//todo params from skillwheel

		//creature params from skill
		BigDecimal currentSkill = playerEntity.currentSkill();
		creatureParams = SkillTable.calcParams(currentSkill);

		//creatures
		Peasant peasant = new Peasant(50);
		WarCreatureBean peasantBean = new WarCreatureBean(peasant);
		peasantBean.addPlayerParams(creatureParams)
				.addSkillParams(creatureParams)
				.calFinalParams();

	}
}
