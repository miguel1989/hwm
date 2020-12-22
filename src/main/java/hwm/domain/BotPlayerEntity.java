package hwm.domain;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bot_players")
@NoArgsConstructor
public class BotPlayerEntity extends BaseEntity {

	@Getter
	@Setter
	@Column(name = "name")
	String name;

	@OneToMany(mappedBy = "botPlayerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	final Set<CreatureEntity> creatures = new HashSet<>();

	public void addCreature(CreatureEntity creatureEntity) {
		this.creatures.add(creatureEntity);
	}

	public Collection<CreatureEntity> creatures() {
		return ImmutableSet.copyOf(creatures);
	}
}
