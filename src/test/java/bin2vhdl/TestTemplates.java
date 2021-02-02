package bin2vhdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class TestTemplates {
	
	@Test
	public void testPackageTemplate () {
		String templateName ="package.tpl";
		try {
			Bin2vhdlConvert converter = new Bin2vhdlConvert();
			converter.setWidth(8);
			converter.setFileName("test");
			converter.setName("test");
			converter.readDataFromInputStream((getClass().getClassLoader().getResourceAsStream(templateName)));

			assertEquals(4829, converter.getVhdl().length());
		} catch (IOException e) {
			e.printStackTrace(); 
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testDefaultTemplate () {
		try {
			Bin2vhdlConvert converter = new Bin2vhdlConvert();

			converter.setFileName("Test1");
			converter.setWidth(16);
			converter.readDataFromInputStream((getClass().getClassLoader().getResourceAsStream("test1")));
			assertEquals(317, converter.getVhdl().length());

		} catch (IOException e) {
			e.printStackTrace(); 
			fail(e.getMessage());
		}
	}

	@Test
	public void testDefaultTemplate32 () {
		try {
			Bin2vhdlConvert converter = new Bin2vhdlConvert();

			converter.setFileName("Test2");
			converter.setWidth(32);
			converter.readDataFromInputStream((getClass().getClassLoader().getResourceAsStream("test1")));
			assertEquals(313, converter.getVhdl().length());

		} catch (IOException e) {
			e.printStackTrace(); 
			fail(e.getMessage());
		}
	}

	
}