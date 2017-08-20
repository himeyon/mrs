package test_tutorial;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class HumanTest {

	public static class 初期化後のテスト {
		Human human;

		@Before
		public void setUp() throws Exception {
			this.human = new Human();
		}

		@Test
		public void 年齢のテスト() throws Exception {
			int age = this.human.getAge(new Date());
			/* 初期化直後は -1 */
			assertThat(age, is(-1));
		}
		@Test
		public void 姓名のテスト() throws Exception {
			String fullname = this.human.getFullName();
			/* 初期化直後は 半角スペース */
			assertThat(fullname, is(" "));
		}
	}

	public static class 値設定後のテスト {
		Human human;

		@Before
		public void setUp() throws Exception {
			this.human = new Human();
			// 誕生日設定
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date date = df.parse("1980/01/11");
			this.human.setBirthDay(date);
			
			// 姓名設定
			this.human.setFirstName("mizuki");
			this.human.setLastName("katagiri");
		}

		@Test
		public void 年齢のテスト() throws Exception {
			/* FIXME このテストは20180111以降はエラーになる. テストの振る舞いを固定にしたいなら、new Dateではなく日付を指定する. */
			int age = this.human.getAge(new Date());
			assertThat(age, is(37));
		}
		@Test
		public void 姓名のテスト() throws Exception {
			String fullname = this.human.getFullName();
			assertThat(fullname, is("mizuki katagiri"));
		}
	}
}
