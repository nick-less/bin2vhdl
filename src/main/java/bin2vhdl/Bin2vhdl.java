package bin2vhdl;

/**
 *
 * @author jc
 */
public class Bin2vhdl {

    public static void main(String[] args)  {
        if (args.length == 0) {
        	System.err.println("Please specify a file.");
        	System.exit(1);
        }
        Bin2vhdlConvert converter = new Bin2vhdlConvert(args[0]);
        System.out.println(converter.getVhdl());
    }
}
