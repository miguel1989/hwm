package hwm.creatures;

import hwm.creature.SimpleCreature;

public class Peasant extends SimpleCreature {
	public Peasant(int count) {
		this.name = "Peasant";
		this.count = count;

		this.attack = 1;
		this.defence = 1;
		this.minDamage = 1;
		this.maxDamage = 1;
		this.hp = 4;
		this.speed = 4;
		this.initiative = 8;
		this.shots = 0;
		this.mana = 0;
		this.range = 0;

		//todo skills, maybe they can come from UI
	}
}
