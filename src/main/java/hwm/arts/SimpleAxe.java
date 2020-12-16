package hwm.arts;

import hwm.domain.ArtifactEntity;
import hwm.game.enums.ArtifactType;

public class SimpleAxe {
	private final ArtifactEntity artifact = new ArtifactEntity();

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

	public ArtifactEntity artifact() {
		return artifact;
	}
}
