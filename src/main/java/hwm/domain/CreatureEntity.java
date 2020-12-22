package hwm.domain;

import hwm.creature.SimpleCreature;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bot_creatures")
@NoArgsConstructor
public class CreatureEntity extends BaseEntity {

	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bot_id")
	BotPlayerEntity botPlayerEntity;

	@Getter
	@Column(name = "name")
	String name;

	@Getter
	@Column(name = "count")
	int count;

	@Getter
	@Column(name = "attack")
	int attack;

	@Getter
	@Column(name = "defence")
	int defence;

	@Getter
	@Column(name = "minDamage")
	int minDamage;

	@Getter
	@Column(name = "maxDamage")
	int maxDamage;

	@Getter
	@Column(name = "hp")
	int hp;

	@Getter
	@Column(name = "speed")
	int speed;

	@Getter
	@Column(name = "initiative")
	int initiative;

	@Getter
	@Column(name = "shots")
	int shots;

	@Getter
	@Column(name = "range")
	int range;

	@Getter
	@Column(name = "mana")
	int mana;

	//todo skills

	public CreatureEntity(BotPlayerEntity botPlayerEntity, SimpleCreature simpleCreature) {
		this.botPlayerEntity = botPlayerEntity;

		this.name = simpleCreature.name;
		this.count = simpleCreature.count;

		this.attack = simpleCreature.attack;
		this.defence = simpleCreature.defence;
		this.minDamage = simpleCreature.minDamage;
		this.maxDamage = simpleCreature.maxDamage;
		this.hp = simpleCreature.hp;
		this.speed = simpleCreature.speed;
		this.initiative = simpleCreature.initiative;
		this.shots = simpleCreature.shots;
		this.range = simpleCreature.range;
		this.mana = simpleCreature.mana;
	}

	public SimpleCreature toSimpleCreature() {
		SimpleCreature simpleCreature = new SimpleCreature();
		simpleCreature.name = this.name;
		simpleCreature.count = this.count;

		simpleCreature.attack = this.attack;
		simpleCreature.defence = this.defence;
		simpleCreature.minDamage = this.minDamage;
		simpleCreature.maxDamage = this.maxDamage;
		simpleCreature.hp = this.hp;
		simpleCreature.speed = this.speed;
		simpleCreature.initiative = this.initiative;
		simpleCreature.shots = this.shots;
		simpleCreature.range = this.range;
		simpleCreature.mana = this.mana;
		return simpleCreature;
	}
}
