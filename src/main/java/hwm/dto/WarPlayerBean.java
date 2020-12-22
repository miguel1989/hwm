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
	public TeamBean teamBean;
	public BoardBean boardBean;

	public WarPlayerBean(PlayerEntity playerEntity, TeamBean teamBean, BoardBean boardBean) {
		this.id = playerEntity.id().toString();
		this.name = playerEntity.getName();
		this.level = playerEntity.getLevel();
		this.faction = playerEntity.getFaction();
		this.heroParams = new BaseParamsBean(playerEntity.finalParams());
		this.hasHero = true;
		this.teamBean = teamBean;
		this.boardBean = boardBean;

		//todo params from skillwheel

		//creature params from skill
		BigDecimal currentSkill = playerEntity.currentSkill();
		BaseParamsBean skillParams = SkillTable.calcParams(currentSkill);

		//creatures
		ArmyEntity army = playerEntity.currentArmy();
		//todo complex logic to find out is it a peasant or a peasantUp
		Peasant peasant = new Peasant(army.getLevel1Count());
		WarCreatureBean peasantBean = new WarCreatureBean(peasant);
		peasantBean.addPlayerParams(heroParams)
				.addSkillParams(skillParams)
				.calFinalParams();

		creatures.add(peasantBean);

		defaultPositionForCreatures();
	}

	public WarPlayerBean(BotPlayerEntity botPlayerEntity, TeamBean teamBean, BoardBean boardBean) {
		this.id = botPlayerEntity.id().toString();
		this.name = botPlayerEntity.getName();
		this.hasHero = false;
		this.teamBean = teamBean;
		this.boardBean = boardBean;

		this.creatures = botPlayerEntity.creatures()
				.stream()
				.map(CreatureEntity::toSimpleCreature)
				.map(it -> {
					WarCreatureBean warCreatureBean = new WarCreatureBean(it);
					warCreatureBean.calFinalParams(); //todo think how to add +1 morale only to alive creatures
					return warCreatureBean;
				})
				.collect(Collectors.toList());

		defaultPositionForCreatures();
	}

	public void defaultPositionForCreatures() {
		int i = 0;
		for (WarCreatureBean warCreatureBean: this.creatures) {
			int tmpX = TeamType.RED.equals(teamBean.team) ? 0 : this.boardBean.width;
			int tmpY = i;//todo take into account creature SIZE
			warCreatureBean.x = tmpX;
			warCreatureBean.y = tmpY;
		}

	}

}
