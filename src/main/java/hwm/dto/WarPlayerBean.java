package hwm.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import hwm.creatures.Peasant;
import hwm.domain.ArmyEntity;
import hwm.domain.BotPlayerEntity;
import hwm.domain.CreatureEntity;
import hwm.domain.PlayerEntity;
import hwm.enums.Faction;
import hwm.enums.TeamType;
import hwm.tables.SkillTable;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class WarPlayerBean {
	@JsonIgnore
	public WarBean war;

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
		creatures.add(new WarCreatureBean(playerEntity));
		creatures.add(new WarCreatureBean(peasant, finalParamsFromPlayer));
	}

	public WarPlayerBean(BotPlayerEntity botPlayerEntity) {
		this.id = botPlayerEntity.id().toString();
		this.name = botPlayerEntity.getName();
		this.hasHero = false;

		this.creatures = botPlayerEntity.creatures()
				.stream()
				.map(CreatureEntity::toSimpleCreature)
				.map(it -> new WarCreatureBean(it, new BaseParamsBean()))
				.collect(Collectors.toList());
	}

	public void defaultPositionForCreatures(TeamType teamType, int playerIndexInTheTeam, int availableHeightForPlayer) {
		int startY = playerIndexInTheTeam * availableHeightForPlayer;
		int endY = ((playerIndexInTheTeam + 1) + availableHeightForPlayer) - 1;
		int count = 0;
		//todo take into account warType, GV -> place user in center
		for (WarCreatureBean warCreatureBean : this.creatures) {
			if (warCreatureBean.isHero) {
				warCreatureBean.x = TeamType.RED.equals(teamType) ? -1 : war.boardBean.width + 1;
				warCreatureBean.y = startY;
				continue;
			}

			int tmpX = TeamType.RED.equals(teamType) ? 0 : war.boardBean.width - 1;
			int tmpY = startY + count;//todo take into account creature SIZE
			warCreatureBean.x = tmpX;
			warCreatureBean.y = tmpY;
			count++;
		}

	}

}
