package hwm.tables;

import hwm.dto.BaseParamsBean;
import hwm.util.BigDecimalUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillTableTest {

	@Test
	public void level0() {
		BaseParamsBean baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("15.3"));
		assertParams(baseParamsBean, 0, 0, 0);

		baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("19.9"));
		assertParams(baseParamsBean, 0, 0, 0);
	}

	@Test
	public void level1() {
		BaseParamsBean baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("20.0"));
		assertParams(baseParamsBean, 0, 1, 0);

		baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("20.1"));
		assertParams(baseParamsBean, 0, 1, 0);

		baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("49.9"));
		assertParams(baseParamsBean, 0, 1, 0);
	}

	@Test
	public void level2() {
		BaseParamsBean baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("50.0"));
		assertParams(baseParamsBean, 1, 1, 0);

		baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("89.9"));
		assertParams(baseParamsBean, 1, 1, 0);
	}

	@Test
	public void level9() {
		BaseParamsBean baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("2900.00"));
		assertParams(baseParamsBean, 6, 6, 8);

		baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("5299.99"));
		assertParams(baseParamsBean, 6, 6, 8);
	}

	@Test
	public void level14() {
		BaseParamsBean baseParamsBean = SkillTable.calcParams(BigDecimalUtils.fromStr("70000.00"));
		assertParams(baseParamsBean, 15, 14, 15);
	}

	public void assertParams(BaseParamsBean baseParamsBean, int expectedAttack, int expectedDefence, int expectedIni) {
		assertEquals(expectedAttack, baseParamsBean.attack);
		assertEquals(expectedDefence, baseParamsBean.defence);
		assertEquals(expectedIni, baseParamsBean.initiative);
	}
}
