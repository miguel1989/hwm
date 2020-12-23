package hwm.dto;

import com.datastax.driver.core.utils.UUIDs;
import hwm.creature.SimpleCreature;
import hwm.util.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class WarCreatureBean {
	static final BigDecimal MAX_ATB = new BigDecimal(100);
	static final Random random = new Random();

	public final UUID id = UUIDs.timeBased();
	public String name;
	public int count;
	public int currentCount;
	public BigDecimal currentATB;
	public BigDecimal startATB;
	public int x = -1;
	public int y = -1;

	public BaseCreatureParamsBean paramsInitial = new BaseCreatureParamsBean();
	public BaseCreatureParamsBean paramsFinal = new BaseCreatureParamsBean();

	public WarCreatureBean(SimpleCreature simpleCreature, BaseParamsBean paramsFromPlayer) {
		this.name = simpleCreature.name;
		this.count = simpleCreature.count; //todo perks can add count
		this.currentCount = simpleCreature.count; //todo perks can add count
		int rndStartAtb = random.nextInt(11);
		this.startATB = BigDecimalUtils.fromInt(rndStartAtb);
		this.currentATB = BigDecimalUtils.fromInt(rndStartAtb);

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
	}

	public void atbTick() {
		this.currentATB = BigDecimalUtils.sum(this.currentATB, this.paramsFinal.initiative);
	}

	public boolean isReadyToMakeTurn() {
		return this.currentATB.compareTo(MAX_ATB) >= 0;
	}

	public void afterTurn() {
		//if (this.isTimeToMove())
		this.currentATB = BigDecimalUtils.subtract(this.currentATB, MAX_ATB);
	}
}
