package hwm.arts;

import hwm.domain.ArtifactEntity;
import hwm.enums.ArtifactType;

public class SimplePendant {

	private final ArtifactEntity artifact = new ArtifactEntity();

	public SimplePendant() {
		this.create();
	}

	void create() {
		artifact.setName("Simple Pendant");
		artifact.initDurability(2);

		artifact.getBaseParams().setAttack(0);
		artifact.getBaseParams().setDefence(0);
		artifact.getBaseParams().setInitiative(49);
		artifact.getBaseParams().setLuck(0);
		artifact.getBaseParams().setMorale(0);
		artifact.getBaseParams().setMagicPower(0);
		artifact.getBaseParams().setKnowledge(0);

		artifact.setForLevel(1);
		artifact.setType(ArtifactType.PENDANT);
	}

	public ArtifactEntity artifact() {
		return artifact;
	}
}
