package hwm.creature;

import com.datastax.driver.core.utils.UUIDs;

import java.math.BigDecimal;

public abstract class Creature extends ATBCreature {

	protected final int count;

//    protected int baseAttack;
//    protected int baseDefence;
//    protected int baseMinDamage;
//    protected int baseMaxDamage;
//    protected int baseLife;

	protected final int baseSpeed;

//    protected int baseShots;
//    protected int baseRange;
//
//    protected int baseMana;

	public Creature(String name, int count, int speed, BigDecimal initiative) {
		super(UUIDs.timeBased(), name, initiative);
		this.count = count;
		this.baseSpeed = speed;
	}

	public Creature(String name, int count, int speed, BigDecimal initiative, BigDecimal startATB) {
		super(UUIDs.timeBased(), name, initiative, startATB);
		this.count = count;
		this.baseSpeed = speed;
	}

	public boolean isAlive() {
		return this.count > 0;
	}

	public int baseSpeed() {
		return baseSpeed;
	}

	public ATBCreature toAtbSimulationCreature() {
		return new ATBCreature(this.id, this.name, this.baseInitiative, this.currentATB);
	}

	@Override
	public String toString() {
		return "Creature{" +
				"id=" + id +
				", name='" + name + '\'' +
				", baseSpeed=" + baseSpeed +
				", baseInitiative=" + baseInitiative +
				", currentATB=" + currentATB +
				'}';
	}
}
