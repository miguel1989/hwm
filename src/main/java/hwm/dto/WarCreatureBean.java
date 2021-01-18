package hwm.dto;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hwm.creature.SimpleCreature;
import hwm.domain.BaseParams;
import hwm.domain.PlayerEntity;
import hwm.util.BigDecimalUtils;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor //default constructor for jackson
public class WarCreatureBean {
	@JsonIgnore
	public WarPlayerBean player;

	static final BigDecimal MAX_ATB = new BigDecimal(100);
	static final BigDecimal WAIT_ATB = new BigDecimal(50);
	static final Random random = new Random();

	public final UUID id = UUIDs.timeBased();
	public String name;
	public int count;
	public int currentCount;
	public BigDecimal currentATB;
	public BigDecimal startATB;
	public int x = -1;
	public int y = -1;
	public boolean isHero = false;

	public BaseCreatureParamsBean paramsInitial = new BaseCreatureParamsBean();
	public BaseCreatureParamsBean paramsFinal = new BaseCreatureParamsBean();

	public WarCreatureBean(WarPlayerBean player, SimpleCreature simpleCreature, BaseParamsBean paramsFromPlayer) {
		this.player = player;
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

	public WarCreatureBean(WarPlayerBean player, PlayerEntity playerEntity) {
		this.player = player;
		this.name = playerEntity.getName() + "[" + playerEntity.getLevel() + "]";
		this.count = 1;
		this.currentCount = 1;
		this.isHero = true;
		int rndStartAtb = random.nextInt(11);
		this.startATB = BigDecimalUtils.fromInt(rndStartAtb);
		this.currentATB = BigDecimalUtils.fromInt(rndStartAtb);

		this.paramsInitial = null;//to exclude it from json

		BaseParams playerFinalParams = playerEntity.finalParams();
		this.paramsFinal.attack = playerFinalParams.getAttack();
		this.paramsFinal.defence = playerFinalParams.getDefence();
		this.paramsFinal.minDamage = playerFinalParams.getAttack();//todo create formula
		this.paramsFinal.maxDamage = playerFinalParams.getAttack() + 1 + playerEntity.getLevel();//todo create formula
		this.paramsFinal.hp = 1;
		this.paramsFinal.speed = 1;
		this.paramsFinal.initiative = BigDecimal.TEN;//todo arts/perks can add ini
		this.paramsFinal.shots = 0;//todo
		this.paramsFinal.range = 0;//todo
		this.paramsFinal.mana = 0;//todo
		this.paramsFinal.luck = 0;//not needed for hero
		this.paramsFinal.morale = 0;//not needed for hero
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

	public void await() {
		this.currentATB = BigDecimalUtils.subtract(this.currentATB, WAIT_ATB);
	}

	public void def() {
		//todo add defence + 30%
		this.currentATB = BigDecimalUtils.subtract(this.currentATB, MAX_ATB);
	}

	public void afterTurn() {
		//if (this.isTimeToMove())
		this.currentATB = BigDecimalUtils.subtract(this.currentATB, MAX_ATB);
	}
}
