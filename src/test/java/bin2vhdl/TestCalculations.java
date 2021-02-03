package bin2vhdl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCalculations {
	
	@Test
	public void testWithoutArgs () {
		Bin2vhdlConvert converter = new Bin2vhdlConvert();
		converter.calculateAddrBits(100);
		assertEquals(7, converter.getAddrBits());

	
		converter.calculateAddrBits(255);
		assertEquals(8, converter.getAddrBits());

		
		converter.calculateAddrBits(65536);
		assertEquals(16, converter.getAddrBits());

	}
	

	
}