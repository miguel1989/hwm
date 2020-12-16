package hwm.domain;

import hwm.game.enums.Faction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "players")
public class PlayerEntity extends BaseEntity {

	@Column(name = "name", unique = true)
	protected String name;

	@Column(name = "level")
	protected int level;

	@Column(name = "attack")
	protected int attack;
	@Column(name = "defence")
	protected int defence;
	@Column(name = "initiative")
	protected BigDecimal initiative = BigDecimal.ZERO;
//	protected int luck;
	@Column(name = "morale")
	protected int morale;

	protected Faction faction;

	//guilds

	//umelka

	//default for hibernate
	public PlayerEntity() {

	}

	public PlayerEntity(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int level() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int attack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int defence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public BigDecimal initiative() {
		return initiative;
	}

	public void setInitiative(BigDecimal initiative) {
		this.initiative = initiative;
	}

	public int morale() {
		return morale;
	}

	public void setMorale(int morale) {
		this.morale = morale;
	}

	public Faction faction() {
		return faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}
}
