package hwm.creature;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;

public class ATBCreature {
	public static final MathContext MATH_CTX = new MathContext(2, RoundingMode.CEILING);
	static final Random random = new Random();
	static final BigDecimal MAX_ATB = new BigDecimal(100);

	protected final UUID id;
	protected final String name;
	protected final BigDecimal baseInitiative;
	protected BigDecimal currentATB;

	public ATBCreature(UUID id, String name, BigDecimal initiative) {
		this(id, name, initiative, new BigDecimal(random.nextInt(11)));
	}

	public ATBCreature(UUID id, String name, BigDecimal initiative, BigDecimal startATB) {
		this.id = id;
		this.name = name;
		this.baseInitiative = initiative;
		this.currentATB = startATB;
	}

	public void atbTick() {
		this.currentATB = this.currentATB.add(this.baseInitiative, MATH_CTX); //todo make it as sum of baseIni + ini
	}

	public boolean isTimeToMove() {
		return this.currentATB.compareTo(MAX_ATB) >= 0;
	}

	public void atbAfterMove() {
		if (this.isTimeToMove()) {
			this.currentATB = this.currentATB.subtract(MAX_ATB, MATH_CTX);
		}
	}

	public UUID id() {
		return id;
	}

	public String name() {
		return name;
	}

	public BigDecimal baseInitiative() {
		return baseInitiative;
	}

	public BigDecimal currentATB() {
		return currentATB;
	}

	@Override
	public String toString() {
		return "ATBCreature{" +
				"id=" + id +
				", name='" + name + '\'' +
				", baseInitiative=" + baseInitiative +
				", currentATB=" + currentATB +
				'}';
	}
}
