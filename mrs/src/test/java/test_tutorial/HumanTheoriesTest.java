package test_tutorial;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class HumanTheoriesTest {

	/*
	 * 複数のテスト入力値と期待値を定義する. 
	 */
	@DataPoints
	public static Object[][] VALUES = {
		{ init("1980/01/11", "mizuki", "katagiri"), 37, "mizuki katagiri" },
		{ init("1982/06/23", "hideki", "katagiri"), 35, "hideki katagiri" },
		{ init("1984/10/10", "hiroki", "katagiri"), 33, "hiroki katagiri" }
	};
	
	private static Human init(String birthday, String firstName, String lastName) {
		Human human = new Human();
		try {
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			human.setBirthDay(df.parse(birthday));			
		} catch (Exception e) {
			human.setBirthDay(null);
		}
		human.setFirstName(firstName);
		human.setLastName(lastName);
		return human;
	}

	/*
	 * DataPointsで定義された複数の入力値と期待値のテストを実行する.
	 */
	@Theory
	public void test(Object[] values) throws Exception {
		Human human = (Human) values[0];
		final int resultAge = (Integer) values[1];
		final String resultFullName = (String) values[2];

		// 年齢のテスト
		assertThat(human.getAge(new Date()), is(resultAge));
		// 姓名のテスト
		assertThat(human.getFullName(), is(resultFullName));
	}
	
	/* FIXME デメリットもある. テストの結果(特にエラーとなった場合)から、どんなケースで何のエラーとなったかがわかりにくい */
}
