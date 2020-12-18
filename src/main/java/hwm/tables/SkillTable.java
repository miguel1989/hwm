package hwm.tables;

import hwm.dto.BaseParamsBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SkillTable {

	public static final List<SkillTableRow> list = new ArrayList<>(14);

	static {
		list.add(new SkillTableRow(1, 20, 0, 1, 0));
		list.add(new SkillTableRow(2, 50, 1, 0, 0));
		list.add(new SkillTableRow(3, 90, 0, 0, 1));
		list.add(new SkillTableRow(4, 160, 1, 0, 1));
		list.add(new SkillTableRow(5, 280, 0, 1, 1));
		list.add(new SkillTableRow(6, 500, 0, 0, 2));
		list.add(new SkillTableRow(7, 900, 0, 2, 1));
		list.add(new SkillTableRow(8, 1600, 2, 0, 1));
		list.add(new SkillTableRow(9, 2900, 2, 2, 1));
		list.add(new SkillTableRow(10, 5300, 2, 2, 2));
		list.add(new SkillTableRow(11, 9600, 2, 3, 2));
		list.add(new SkillTableRow(12, 17300, 3, 2, 2));
		list.add(new SkillTableRow(13, 35000, 1, 1, 0));
		list.add(new SkillTableRow(14, 70000, 1, 0, 1));
	}

	public static BaseParamsBean calcParams(BigDecimal currSkill) {
		BaseParamsBean baseParamsBean = new BaseParamsBean();
		for (SkillTableRow row: list) {
			if (currSkill.compareTo(row.skill) < 0) {
				return baseParamsBean;
			}
			baseParamsBean.add(row.baseParams);
		}
		return baseParamsBean;
	}
}
