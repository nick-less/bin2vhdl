package bin2vhdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class TestArguments {
	
	@Test
	public void testWithoutArgs () {
		String args [] = {"name"};
		
		Bin2vhdlConvert converter = new Bin2vhdlConvert();
		
		Bin2vhdl.parseArgs(converter, args); 
		assertEquals("name", converter.getFileName());
		
	}
	
	@Test
	public void testSetTemplate () {
		String args [] = {"name", "-t", "template.ftl"};
		
		Bin2vhdlConvert converter = new Bin2vhdlConvert();
		
		Bin2vhdl.parseArgs(converter, args); 
		assertEquals("name", converter.getFileName());
		assertEquals("template.ftl", converter.getTemplateName());
	}

	@Test
	public void testSetName () {
		String args [] = {"filename", "-n", "name", "-t", "template.ftl"};
		
		Bin2vhdlConvert converter = new Bin2vhdlConvert();
		
		Bin2vhdl.parseArgs(converter, args); 
		assertEquals("name", converter.getName());
		assertEquals("filename", converter.getFileName());
		assertEquals("template.ftl", converter.getTemplateName());
	}

	
}