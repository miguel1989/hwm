package hwm.dto;

import com.datastax.driver.core.utils.UUIDs;
import hwm.creature.SimpleCreature;
import hwm.util.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class WarCreatureBean {
	public final UUID id = UUIDs.timeBased();
	public String name;
	public int count;
	public int countFinal;
	public int x = -1;
	public int y = -1;

	public BaseCreatureParamsBean paramsInitial = new BaseCreatureParamsBean();
	public BaseCreatureParamsBean paramsFromPlayer = new BaseCreatureParamsBean();
	public BaseCreatureParamsBean paramsFromSkill = new BaseCreatureParamsBean();
	public BaseCreatureParamsBean paramsFinal = new BaseCreatureParamsBean();

	public WarCreatureBean(SimpleCreature simpleCreature) {
		this.name = simpleCreature.name;
		this.count = simpleCreature.count;

		this.paramsInitial.attack = simpleCreature.attack;
		this.paramsInitial.defence = simpleCreature.defence;
		this.paramsInitial.minDamage = simpleCreature.minDamage;
		this.paramsInitial.maxDamage = simpleCreature.maxDamage;
		this.paramsInitial.hp = simpleCreature.hp;
		this.paramsInitial.speed = simpleCreature.speed;
		this.paramsInitial.initiative = BigDecimalUtils.fromInt(simpleCreature.initiative);
		this.paramsInitial.shots = simpleCreature.shots;
		this.paramsInitial.range = simpleCreature.range;
		this.paramsInitial.mana = simpleCreature.mana;
	}

	public WarCreatureBean addPlayerParams(BaseParamsBean baseParamsBean) {
		this.paramsFromPlayer.attack = baseParamsBean.attack;
		this.paramsFromPlayer.defence = baseParamsBean.defence;
		this.paramsFromPlayer.initiative = BigDecimalUtils.fromInt(baseParamsBean.initiative);
		//todo maybe later add also max/min dmg from player

		this.paramsFromPlayer.luck = baseParamsBean.luck;
		this.paramsFromPlayer.morale = baseParamsBean.morale;
		return this;
	}

	public WarCreatureBean addSkillParams(BaseParamsBean baseParamsBean) {
		this.paramsFromSkill.attack = baseParamsBean.attack;
		this.paramsFromSkill.defence = baseParamsBean.defence;
		this.paramsFromSkill.initiative = BigDecimalUtils.fromInt(baseParamsBean.initiative);
		return this;
	}

	public void calFinalParams() {
		this.paramsFinal.attack = this.paramsInitial.attack + this.paramsFromPlayer.attack + this.paramsFromSkill.attack;
		this.paramsFinal.defence = this.paramsInitial.defence + this.paramsFromPlayer.defence + this.paramsFromSkill.defence;
		this.paramsFinal.minDamage = this.paramsInitial.minDamage; //todo
		this.paramsFinal.maxDamage = this.paramsInitial.maxDamage; //todo
		this.paramsFinal.hp = this.paramsInitial.hp; //todo
		this.paramsFinal.speed = this.paramsInitial.speed; //todo
		BigDecimal tmpIni = BigDecimalUtils.sum(this.paramsFromPlayer.initiative, this.paramsFromSkill.initiative);
		this.paramsFinal.initiative = BigDecimalUtils.addInitiative(this.paramsInitial.initiative, tmpIni);
		this.paramsFinal.shots = this.paramsInitial.shots; //todo
		this.paramsFinal.range = this.paramsInitial.range; //todo
		this.paramsFinal.mana = this.paramsInitial.mana; //todo

		this.paramsFinal.luck = this.paramsInitial.luck + this.paramsFromPlayer.luck;
		this.paramsFinal.morale = this.paramsInitial.morale + this.paramsFromPlayer.morale;
		//todo perks can add count
		this.countFinal = this.count;
	}
}
