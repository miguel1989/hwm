package hwm.arts;

import hwm.domain.ArtifactEntity;
import hwm.game.enums.ArtifactType;

public class SimpleEarRing {
	private final ArtifactEntity artifact = new ArtifactEntity();

	public SimpleEarRing() {
		this.create();
	}

	void create() {
		artifact.setName("Simple Earring");
		artifact.initDurability(2);

		artifact.getBaseParams().setAttack(2);
		artifact.getBaseParams().setDefence(2);
		artifact.getBaseParams().setInitiative(2);
		artifact.getBaseParams().setLuck(2);
		artifact.getBaseParams().setMorale(2);
		artifact.getBaseParams().setMagicPower(2);
		artifact.getBaseParams().setKnowledge(2);

		artifact.setForLevel(1);
		artifact.setType(ArtifactType.EARRING);
	}

	public ArtifactEntity artifact() {
		return artifact;
	}
}
