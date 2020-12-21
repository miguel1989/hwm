package hwm.dto;

import com.datastax.driver.core.utils.UUIDs;
import hwm.creature.SimpleCreature;
import hwm.util.BigDecimalUtils;

import java.util.UUID;

public class WarCreatureBean extends BaseCreatureParamsBean {
	public final UUID id = UUIDs.timeBased();
	public String name;
	public int count;

	public BaseCreatureParamsBean paramsFromPlayer;
	public BaseCreatureParamsBean paramsFromSkill;
	public BaseCreatureParamsBean finalParams;

	public WarCreatureBean(SimpleCreature simpleCreature) {
		this.name = simpleCreature.name;
		this.count = simpleCreature.count;

		this.attack = simpleCreature.attack;
		this.defence = simpleCreature.defence;
		this.minDamage = simpleCreature.minDamage;
		this.maxDamage = simpleCreature.maxDamage;
		this.hp = simpleCreature.hp;
		this.speed = simpleCreature.speed;
		this.initiative = BigDecimalUtils.fromInt(simpleCreature.initiative);
		this.shots = simpleCreature.shots;
		this.range = simpleCreature.range;
		this.mana = simpleCreature.mana;
	}

	public void addPlayerParams(BaseParamsBean baseParamsBean) {
		this.paramsFromPlayer.attack = baseParamsBean.attack;
		this.paramsFromPlayer.defence = baseParamsBean.defence;
		this.paramsFromPlayer.initiative = BigDecimalUtils.fromInt(baseParamsBean.initiative);
		//todo maybe later add also max/min dmg from player

		this.paramsFromPlayer.luck = baseParamsBean.luck;
		this.paramsFromPlayer.morale = baseParamsBean.morale;
	}

	public void addSkillParams(BaseParamsBean baseParamsBean) {
		this.paramsFromSkill.attack = baseParamsBean.attack;
		this.paramsFromSkill.defence = baseParamsBean.defence;
		this.paramsFromSkill.initiative = BigDecimalUtils.fromInt(baseParamsBean.initiative);
	}

	public void calFinalParams() {
		this.finalParams.attack = this.attack + this.paramsFromPlayer.attack + this.paramsFromSkill.attack;
		this.finalParams.defence = this.defence + this.paramsFromPlayer.defence + this.paramsFromSkill.defence;
		this.finalParams.minDamage = this.minDamage; //todo
		this.finalParams.maxDamage = this.maxDamage; //todo
		this.finalParams.hp = this.hp; //todo
		this.finalParams.speed = this.speed; //todo
		this.finalParams.initiative =
				BigDecimalUtils.addInitiative(this.initiative, BigDecimalUtils.sum(this.paramsFromPlayer.initiative, this.paramsFromSkill.initiative));
		this.finalParams.shots = this.shots; //todo
		this.finalParams.range = this.range; //todo
		this.finalParams.mana = this.mana; //todo
	}

	public int finalCount() {
		return this.count; //todo perks can add count
	}

}
