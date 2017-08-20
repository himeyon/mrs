package test_tutorial;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import test_tutorial.model.Book;

public class FixtureTest {

	@Test
	public void yamlLoad( ) throws Exception {
		Book book = (new Yaml()).loadAs(getClass().getResourceAsStream("books.yaml"), Book.class);
		
		System.out.println(book.toString());
	}
	/* FIXME どうもSnakeYamlはListオブジェクトを受け取るにはModel側に色々やらないと行けないようだ... CSVのほうが楽かも */
}
