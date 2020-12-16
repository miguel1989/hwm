package hwm.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BaseParams {
	@Getter
	@Setter
	@Column(name = "attack")
	public int attack;

	@Getter
	@Setter
	@Column(name = "defence")
	public int defence;

	@Getter
	@Setter
	@Column(name = "magic_power")
	public int magicPower;

	@Getter
	@Setter
	@Column(name = "knowledge")
	public int knowledge;

	@Getter
	@Setter
	@Column(name = "initiative")
	public int initiative;

	@Getter
	@Setter
	@Column(name = "luck")
	public int luck;

	@Getter
	@Setter
	@Column(name = "morale")
	public int morale;
}
