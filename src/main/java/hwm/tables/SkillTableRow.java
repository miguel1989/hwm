package hwm.tables;

import hwm.dto.BaseParamsBean;
import hwm.util.BigDecimalUtils;

import java.math.BigDecimal;

public class SkillTableRow {
	public final int level;
	public final BigDecimal skill;
	public final BaseParamsBean baseParams;

	public SkillTableRow(int level, int skill, int attack, int defence, int initiative) {
		this.level = level;
		this.skill = BigDecimalUtils.fromInt(skill);
		this.baseParams = new BaseParamsBean(attack, defence, initiative);
	}
}
