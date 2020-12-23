package hwm.dto;

import com.datastax.driver.core.utils.UUIDs;
import hwm.creature.SimpleCreature;
import hwm.util.BigDecimalUtils;

import java.util.UUID;

public class WarCreatureBean {
	public final UUID id = UUIDs.timeBased();
	public String name;
	public int count;
	public int countFinal;
	public int x = -1;
	public int y = -1;

	public BaseCreatureParamsBean paramsInitial = new BaseCreatureParamsBean();
	public BaseCreatureParamsBean paramsFinal = new BaseCreatureParamsBean();

	public WarCreatureBean(SimpleCreature simpleCreature, BaseParamsBean paramsFromPlayer) {
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

		this.addPlayerParams(paramsFromPlayer);
	}

	void addPlayerParams(BaseParamsBean paramsFromPlayer) {
		this.paramsFinal.attack = this.paramsInitial.attack + paramsFromPlayer.attack;
		this.paramsFinal.defence = this.paramsInitial.defence + paramsFromPlayer.defence;
		this.paramsFinal.minDamage = this.paramsInitial.minDamage;//todo can be increased by perks
		this.paramsFinal.maxDamage = this.paramsInitial.maxDamage;//todo can be increased by perks
		this.paramsFinal.hp = this.paramsInitial.hp;//todo can be increased by perks
		this.paramsFinal.speed = this.paramsInitial.speed;//todo can be increased by perks
		this.paramsFinal.initiative = BigDecimalUtils.addInitiative(this.paramsInitial.initiative, BigDecimalUtils.fromInt(paramsFromPlayer.initiative));
		this.paramsFinal.shots = this.paramsInitial.shots;//todo
		this.paramsFinal.range = this.paramsInitial.range;//todo
		this.paramsFinal.mana = this.paramsInitial.mana;//todo

		this.paramsFinal.luck = this.paramsInitial.luck + paramsFromPlayer.luck;//todo
		this.paramsFinal.morale = this.paramsInitial.morale + paramsFromPlayer.morale;//todo

		//todo perks can add count
		this.countFinal = this.count;
	}
}
