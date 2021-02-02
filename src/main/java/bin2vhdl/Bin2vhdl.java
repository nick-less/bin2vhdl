package bin2vhdl;

import java.io.IOException;

import gnu.getopt.Getopt;

/**
 *
 * @author jc
 */
public class Bin2vhdl {
	private static String filename;

	public static void main(String[] args) {

		//
		if (args.length == 0) {
			System.err.println("Please specify a file.");
			System.exit(1);
		}
		Getopt g = new Getopt("bin2vhdl", args, "t:w:n:");
		Bin2vhdlConvert converter = new Bin2vhdlConvert();

		int c;
		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 't':
				converter.setTemplateName(g.getOptarg());
				break;
			case 'n':
				converter.setName(g.getOptarg());
				break;
			case 'w':
				converter.setWidth(Integer.valueOf( g.getOptarg()));
				break;
			case '?':
				break; // getopt() already printed an error
			//
	          default:
	        	  //filename = g.et
			}
		}

		filename = args[0];
		System.out.println(filename);
		try {
			converter.readDataFromFile(args[0]);
			System.out.println(converter.getVhdl());
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
