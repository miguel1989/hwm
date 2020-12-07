package hwm.creature;

import com.datastax.driver.core.utils.UUIDs;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;

public abstract class Creature {
	private static final MathContext MATH_CTX = new MathContext(2, RoundingMode.CEILING);
	private static final Random random = new Random();

	private static final BigDecimal MAX_ATB = new BigDecimal(100);

	protected UUID id = UUIDs.timeBased();

	protected final String name;
	protected final int count;

//    protected int baseAttack;
//    protected int baseDefence;
//    protected int baseMinDamage;
//    protected int baseMaxDamage;
//    protected int baseLife;

	protected final int baseSpeed;
	protected final BigDecimal baseInitiative;
	protected BigDecimal currentATB;

//    protected int baseShots;
//    protected int baseRange;
//
//    protected int baseMana;

	public Creature(String name, int count, int speed, BigDecimal initiative) {
		this(name, count, speed, initiative, new BigDecimal(random.nextInt(11)));
	}

	public Creature(String name, int count, int speed, BigDecimal initiative, BigDecimal startATB) {
		this.name = name;
		this.count = count;
		this.baseSpeed = speed;
		this.baseInitiative = initiative;
		this.currentATB = startATB;
	}

	public void atbTick() {
		this.currentATB = this.currentATB.add(this.baseInitiative, MATH_CTX); //todo make it as sum of baseIni + ini
	}

	public void atbAfterMove() {
		if (this.currentATB.compareTo(MAX_ATB) >= 0) {
			this.currentATB = BigDecimal.ZERO;
		}
	}

	public boolean isAlive() {
		return this.count > 0;
	}

	public UUID id() {
		return id;
	}

	public String name() {
		return name;
	}

	public int baseSpeed() {
		return baseSpeed;
	}

	public BigDecimal baseInitiative() {
		return baseInitiative;
	}

	public BigDecimal currentATB() {
		return currentATB;
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
