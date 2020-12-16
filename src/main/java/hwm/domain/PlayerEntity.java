package hwm.domain;

import com.google.common.collect.ImmutableList;
import hwm.game.enums.ArtifactType;
import hwm.game.enums.Faction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

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

	public void addArtifact(ArtifactEntity artifact) {
		artifact.setPlayerEntity(this);
		this.artifacts.add(artifact);
	}

	public void removeArtifact(String artId) {
		Optional<ArtifactEntity> optionalArt = findArtifactById(artId);
		optionalArt.ifPresent(artifactEntity -> {
			artifactEntity.detachFromPlayer();
			artifacts.remove(artifactEntity);
		});
	}

	public boolean putOnArtifact(String artId) {
		Optional<ArtifactEntity> optionalArt = findArtifactById(artId);

		if (optionalArt.isPresent()) {
			if (isArtifactSlotFree(optionalArt.get().getType())) {
				optionalArt.get().putOn();
				return optionalArt.get().isOn();
			}
		}

		return false;
	}

	public boolean takeOffArtifact(String artId) {
		Optional<ArtifactEntity> optionalArt = findArtifactById(artId);

		if (optionalArt.isPresent()) {
			optionalArt.get().takeOff();
			return true;
		}
		return false;
	}

	public void decreaseDurabilityAfterFight() {
		this.artifacts.stream().filter(ArtifactEntity::isOn).forEach(ArtifactEntity::decreaseDurability);
	}

	Optional<ArtifactEntity> findArtifactById(String artId) {
		UUID uuid = UUID.fromString(artId);
		return this.artifacts.stream().filter(it -> it.id().equals(uuid)).findFirst();
	}

	boolean isArtifactSlotFree(ArtifactType artifactType) {
		if (ArtifactType.EARRING.equals(artifactType)) {
			long count = this.artifacts.stream().filter(it -> it.isOn() && it.getType().equals(artifactType)).count();
			return count < 2; //we can have only 2 earrings
		}
		return this.artifacts.stream().anyMatch(it -> it.isOn() && it.getType().equals(artifactType));
	}

//	public int allAttack() {
//		int artifactAttack = this.artifacts.stream().map(art -> art.attack).reduce(0, Integer::sum);
//		return this.attack + artifactAttack;
//	}
}
