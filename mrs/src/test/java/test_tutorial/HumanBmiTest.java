package test_tutorial;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class HumanBmiTest {

	public static class 初期化後のテスト {
		Human human;

		@Before
		public void setUp() throws Exception {
			this.human = new Human();
		}
		
		@Rule
		public ExpectedException expectedException = ExpectedException.none();

		@Test
		public void BMIのテスト_例外が送出される() throws Exception {
			
			// 例外送出と例外メッセージを定義.
			expectedException.expect(NumberFormatException.class);
			expectedException.expectMessage("Infinite or NaN");
			
			// 例外送出が想定される処理
			this.human.getBmi();
		}
	}

	public static class 値設定後のテスト {
		Human human;

		@Before
		public void setUp() throws Exception {
			this.human = new Human();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date date = df.parse("1995/04/20");
			this.human.setBirthDay(date);
			this.human.setFirstName("Damian");
			this.human.setLastName("McKenzie");
			// 身長・体重を設定
			this.human.setHeight(175.0);
			this.human.setWeight(81.0);
		}

		@Test
		public void BMIのテスト() throws Exception {
			BigDecimal bmi = this.human.getBmi();
			assertEquals(bmi, new BigDecimal("26.45"));
		}
	}
}
