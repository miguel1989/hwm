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
		artifact.initDurability(3);

		artifact.getBaseParams().setAttack(4);
		artifact.getBaseParams().setDefence(1);
		artifact.getBaseParams().setInitiative(1);
		artifact.getBaseParams().setLuck(1);

		artifact.setForLevel(1);
		artifact.setType(ArtifactType.WEAPON_LEFT_HAND);
	}

	public Artifact artifact() {
		return artifact;
	}
}
