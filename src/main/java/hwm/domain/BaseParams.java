package hwm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BaseParams {
	@Getter
	@Setter
	@Column(name = "attack")
	int attack;

	@Getter
	@Setter
	@Column(name = "defence")
	int defence;

	@Getter
	@Setter
	@Column(name = "magic_power")
	int magicPower;

	@Getter
	@Setter
	@Column(name = "knowledge")
	int knowledge;

	@Getter
	@Setter
	@Column(name = "initiative")
	int initiative;

	@Getter
	@Setter
	@Column(name = "luck")
	int luck;

	@Getter
	@Setter
	@Column(name = "morale")
	int morale;

	@Override
	public BaseParams clone() {
		BaseParams bp = new BaseParams();
		bp.setAttack(this.attack);
		bp.setDefence(this.defence);
		bp.setMagicPower(this.magicPower);
		bp.setKnowledge(this.knowledge);
		bp.setInitiative(this.initiative);
		bp.setLuck(this.luck);
		bp.setMorale(this.morale);
		return bp;
	}

	public void addParams(BaseParams bp) {
		this.attack += bp.getAttack();
		this.defence += bp.getDefence();
		this.magicPower += bp.getMagicPower();
		this.knowledge += bp.getKnowledge();
		this.initiative += bp.getInitiative();
		this.luck += bp.getLuck();
		this.morale += bp.getMorale();
	}
}
