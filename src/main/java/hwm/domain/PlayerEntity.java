package hwm.domain;

import com.google.common.collect.ImmutableList;
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
	@Embedded
	BaseParams baseParams = new BaseParams();

	@Getter
	@Setter
	@Column(name = "faction")
	Faction faction;

	//todo guilds

	//todo umelka

	//todo artifacts
	@OneToMany(mappedBy = "playerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	final Collection<ArtifactEntity> artifacts = new LinkedList<>();

	//todo perks

	public PlayerEntity(String name) {
		this.name = name;
	}

	public Collection<ArtifactEntity> artifacts() {
		return ImmutableList.copyOf(artifacts);
	}

//	public int allAttack() {
//		int artifactAttack = this.artifacts.stream().map(art -> art.attack).reduce(0, Integer::sum);
//		return this.attack + artifactAttack;
//	}
}
