package hwm.dto;

import hwm.domain.BaseParams;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseParamsBean {
	public int attack;
	public int defence;
	public int magicPower;
	public int knowledge;
	public int initiative;
	public int luck;
	public int morale;

	public BaseParamsBean(BaseParams bp) {
		this.attack = bp.getAttack();
		this.defence = bp.getDefence();
		this.magicPower = bp.getMagicPower();
		this.knowledge = bp.getKnowledge();
		this.initiative = bp.getInitiative();
		this.luck = bp.getLuck();
		this.morale = bp.getMorale();
	}

	public BaseParamsBean(int attack, int defence, int initiative) {
		this.attack = attack;
		this.defence = defence;
		this.initiative = initiative;
	}

	public void add(BaseParamsBean bp) {
		this.attack += bp.attack;
		this.defence += bp.defence;
		this.magicPower += bp.magicPower;
		this.knowledge += bp.knowledge;
		this.initiative += bp.initiative;
		this.luck += bp.luck;
		this.morale += bp.morale;
	}
}
