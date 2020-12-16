package hwm.arts;

import hwm.domain.Artifact;
import hwm.game.enums.ArtifactType;

public class SimpleAxe {
	private final Artifact artifact = new Artifact();

	public SimpleAxe() {
		create();
	}

	void create() {
		artifact.setName("Simple Axe");
		artifact.setAttack(4);
		artifact.setDefence(1);
		artifact.setForLevel(1);
		artifact.setInitiative(1);
		artifact.setType(ArtifactType.WEAPON_LEFT_HAND);
		artifact.setLuck(1);
	}

	public Artifact artifact() {
		return artifact;
	}
}
