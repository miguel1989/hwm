package hwm.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

//make sure you match the Faction
@Entity
@Table(name = "skills")
@NoArgsConstructor
public class SkillsEntity extends BaseEntity {

	@Getter
	@Setter
	@Column(name = "knight")
	BigDecimal knight = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Getter
	@Setter
	@Column(name = "necro")
	BigDecimal necro = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Getter
	@Setter
	@Column(name = "elf")
	BigDecimal elf = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Getter
	@Setter
	@Column(name = "dark_elf")
	BigDecimal darkElf = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Getter
	@Setter
	@Column(name = "mage")
	BigDecimal mage = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
}
