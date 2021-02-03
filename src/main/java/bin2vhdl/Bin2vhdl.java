package bin2vhdl;

import java.io.IOException;

import gnu.getopt.Getopt;

/**
 *
 * @author jc
 */
public class Bin2vhdl {

	
	public static void parseArgs (Bin2vhdlConvert converter, String[] args) {
		Getopt g = new Getopt("bin2vhdl", args, "t:w:n:o:");
		int c;
		while ((c = g.getopt()) != -1) {
			
			   
			switch (c) {
			case 't':
				converter.setTemplateName(g.getOptarg());
				break;
			case 'o':
				converter.setOutputName(g.getOptarg());
				break;
			case 'n':
				converter.setName(g.getOptarg());
				break;
			case 'w':
				converter.setWidth(Integer.valueOf( g.getOptarg()));
				break;
			case '?':
				break; 
			}
		}
		converter.setFileName(args [g.getOptind()]);
	}
	
	
	public static void main(String[] args) {

		//
		if (args.length == 0) {
			System.err.println("Please specify a file.");
			System.exit(1);
		}

		try {
			Bin2vhdlConvert converter = new Bin2vhdlConvert();
			parseArgs(converter, args);
			converter.readDataFromFile(converter.getFileName());
			if (converter.getOutputName() != null) {
				converter.writeOutputFile();
				System.out.println("wrote to "+converter.getOutputName());
			} else {
				System.out.println(converter.getVhdl());
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
