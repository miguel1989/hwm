package hwm.domain;

import hwm.enums.ArtifactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "artifacts")
@NoArgsConstructor
public class ArtifactEntity extends BaseEntity {
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	PlayerEntity playerEntity;

	@Getter
	@Setter
	@Column(name = "owned_by")
	UUID ownedBy;

	@Getter
	@Setter
	@Column(name = "name")
	String name;

	@Getter
	@Setter
	@Column(name = "durability_total")
	int durabilityTotal;

	@Getter
	@Setter
	@Column(name = "durability_current")
	int durabilityCurrent;

	@Getter
	@Column(name = "is_on")
	boolean isOn = false;

	@Getter
	@Setter
	@Column(name = "for_level")
	int forLevel = 1;

	@Getter
	@Setter
	@Embedded
	BaseParams baseParams = new BaseParams();

	@Getter
	@Setter
	@Column(name = "type")
	ArtifactType type;

	//todo price

	//todo effects

	public int getAttack() {
		return this.baseParams.attack;
	}

	public int getDefence() {
		return this.baseParams.defence;
	}

	public int getInitiative() {
		return this.baseParams.initiative;
	}

	public int getLuck() {
		return this.baseParams.luck;
	}

	public int getMorale() {
		return this.baseParams.morale;
	}

	public int getMagicPower() {
		return this.baseParams.magicPower;
	}

	public int getKnowledge() {
		return this.baseParams.knowledge;
	}

	public void initDurability(int num) {
		this.durabilityTotal = num;
		this.durabilityCurrent = num;
	}

	public void putOn(int playerLevel) {
		if (playerLevel > this.forLevel) {
			return;
		}
		if (isBroken()) {
			return;
		}
		this.isOn = true;
	}

	public void takeOff() {
		this.isOn = false;
	}

	public void decreaseDurability() {
		if (!this.isOn) {
			return;
		}
		if (isBroken()) {
			return;
		}
		this.durabilityCurrent--;
		if (isBroken()) {
			this.takeOff();
		}
	}

	public boolean isBroken() {
		return this.durabilityCurrent == 0;
	}

	public void detachFromPlayer() {
		if (this.playerEntity == null) {
			return;
		}
		this.ownedBy = this.playerEntity.id;
		this.playerEntity = null;
	}

	public String durabilityStr() {
		return this.getDurabilityCurrent() + "/" + this.getDurabilityTotal();
	}
}
