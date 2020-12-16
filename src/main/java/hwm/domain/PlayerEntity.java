package hwm.domain;

import hwm.game.enums.Faction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "players")
@NoArgsConstructor
public class PlayerEntity extends BaseEntity {

	@Getter
	@Setter
	@Column(name = "name", unique = true)
	String name;

	@Getter
	@Setter
	@Column(name = "level")
	int level;

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

	@Getter
	@Setter
	@Column(name = "faction")
	Faction faction;

	//todo guilds

	//todo umelka

	//todo artifacts
	@OneToMany(mappedBy = "playerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	final Collection<Artifact> artifacts = new LinkedList<>();

	//todo perks

	public PlayerEntity(String name) {
		this.name = name;
	}

	public int allAttack() {
		int artifactAttack = this.artifacts.stream().map(art -> art.attack).reduce(0, Integer::sum);
		return this.attack + artifactAttack;
	}
}
