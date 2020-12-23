package hwm.dto;


import hwm.creatures.Peasant;
import hwm.domain.ArmyEntity;
import hwm.domain.BotPlayerEntity;
import hwm.domain.CreatureEntity;
import hwm.domain.PlayerEntity;
import hwm.enums.Faction;
import hwm.enums.TeamType;
import hwm.tables.SkillTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarPlayerBean {

	public String id;
	public String name;
	public int level;
	public Faction faction;
	public BaseParamsBean heroParams = new BaseParamsBean();
	public List<WarCreatureBean> creatures = new ArrayList<>();
	public boolean hasHero = false;

	public WarPlayerBean(PlayerEntity playerEntity) {
		this.id = playerEntity.id().toString();
		this.name = playerEntity.getName();
		this.level = playerEntity.getLevel();
		this.faction = playerEntity.getFaction();
		this.heroParams = new BaseParamsBean(playerEntity.finalParams());
		this.hasHero = true;

		BaseParamsBean finalParamsFromPlayer = new BaseParamsBean();
		finalParamsFromPlayer.add(heroParams);
		//todo params from skillwheel

		//creature params from skill
		BigDecimal currentSkill = playerEntity.currentSkill();
		BaseParamsBean skillParams = SkillTable.calcParams(currentSkill);
		finalParamsFromPlayer.add(skillParams);

		//creatures
		ArmyEntity army = playerEntity.currentArmy();
		//todo complex logic to find out is it a peasant or a peasantUp
		Peasant peasant = new Peasant(army.getLevel1Count());
		creatures.add(new WarCreatureBean(peasant, finalParamsFromPlayer));
	}

	public WarPlayerBean(BotPlayerEntity botPlayerEntity) {
		this.id = botPlayerEntity.id().toString();
		this.name = botPlayerEntity.getName();
		this.hasHero = false;

		//todo think how to add +1 morale only to alive creatures
		this.creatures = botPlayerEntity.creatures()
				.stream()
				.map(CreatureEntity::toSimpleCreature)
				.map(it -> new WarCreatureBean(it, new BaseParamsBean()))
				.collect(Collectors.toList());
	}

	public void defaultPositionForCreatures(TeamType teamType, BoardBean boardBean) {
		int i = 0;
		//todo take into account warType, GV -> place user in center
		for (WarCreatureBean warCreatureBean: this.creatures) {
			int tmpX = TeamType.RED.equals(teamType) ? 0 : boardBean.width;
			int tmpY = i;//todo take into account creature SIZE
			warCreatureBean.x = tmpX;
			warCreatureBean.y = tmpY;
		}

	}

}
