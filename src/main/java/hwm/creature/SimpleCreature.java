package hwm.creature;

import com.datastax.driver.core.utils.UUIDs;

import java.util.UUID;

public class SimpleCreature {
	public final UUID id = UUIDs.timeBased();

	public String name;
	public int count;

	public int attack;
	public int defence;
	public int minDamage;
	public int maxDamage;
	public int hp;
	public int speed;
	public int initiative;
	public int shots;
	public int range;
	public int mana;
}
