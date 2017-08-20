package test_tutorial;

import static org.junit.Assert.assertThat;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;

public class MatcherTest {
	
	@Test
	public void 都道府県のリストの件数の検証() {
		Matcher test = new Matcher();
		assertThat(test.getJapanPrefectures(), IsCollectionWithSize.hasSize(47));
	}
}
