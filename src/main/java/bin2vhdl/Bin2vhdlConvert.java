package bin2vhdl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.cache.TemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.SimpleScalar;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNotFoundException;

/**
 *
 * @author jc
 */
class Bin2vhdlConvert {

	private static final int DEFAULT_WIDTH = 8;
	private static final String VALUES_KEY = "values";
	private static final String FILENAME_KEY = "filename";
	private static final String NAME_KEY = "name";
	private static final String TEMPLATE_NAME_KEY = "templateName";
	private static final String WIDTH_KEY = "word_width";
	private static final String WIDTH_BYTES_KEY = "width_bytes";
	private static final String DEFAULT_TEMPLATE_NAME = "package.tpl";

	Map<String, Object> variables = new HashMap<>();
	private OutputStream outputStream = new ByteArrayOutputStream();

	public class WriteHex implements TemplateMethodModelEx {

		@Override
		public TemplateModel exec(List args) throws TemplateModelException {
			if (args.size() != 2) {
				throw new TemplateModelException("Wrong argument count");
			}
			int value = Integer.valueOf(args.get(0).toString());
			int width = Integer.valueOf(args.get(1).toString());
			if (width == 8) {
				return new SimpleScalar(String.format("%02X", value & 0xff));
			}
			if (width == 16) {
				return new SimpleScalar(String.format("%04X", value & 0xffff));
			}
			if (width == 32) {
				return new SimpleScalar(String.format("%08X", value));
			}
			return new SimpleScalar(String.format("%02X", value & 0xff) + " " + width + " " + args.get(1));

		}
	}

	public void readDataFromFile(String filename) throws FileNotFoundException, IOException {
		setFileName(filename);
		readDataFromInputStream(new FileInputStream(new File(filename)));
	}

	public void readDataFromInputStream(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int length;
		while ((length = in.read(buf)) > 0) {
			bout.write(buf, 0, length);
		}
		if (getWidth() == 8) {
			variables.put(VALUES_KEY, bout.toByteArray());
		}
		if (getWidth() == 16) {
			byte bdata[] = bout.toByteArray();
			int data[] = new int[bdata.length / 2 + (bdata.length % 2!=0?1:0)];
			int x = 0;
			for (int i = 0; i < data.length; i++) {
				data[i] = ((int) bdata[x++]) << 8;
				if (x < bdata.length) {
					data[i] |= bdata[x++];
				}
			}
			variables.put(VALUES_KEY, data);
		}

		if (getWidth() == 32) {
			byte bdata[] = bout.toByteArray();
			int data[] = new int[bdata.length / 4 + (bdata.length % 4!=0?1:0)];
			int x = 0;
			for (int i = 0; i < data.length; i++) {
				if (x < bdata.length) {
					data[i] = ((int) bdata[x++]) << 24;
				}
				if (x < bdata.length) {
					data[i] |= ((int) bdata[x++]) << 16;
				}
				if (x < bdata.length) {
					data[i] |= ((int) bdata[x++]) << 8;
				}
				if (x < bdata.length) {
					data[i] |= bdata[x++];
				}
			}
			variables.put(VALUES_KEY, data);
		}
		try {
			createOutput();
		} catch (TemplateException e) {
			throw new IOException(e);
		}
	}

	private int getWidth() {
		// TODO Auto-generated method stub
		return (int) variables.get(WIDTH_KEY);
	}

	public Bin2vhdlConvert() {
		setTemplateName(DEFAULT_TEMPLATE_NAME);
		setWidth(DEFAULT_WIDTH);
		variables.put("hex", new WriteHex());
	}

	public String getVhdl() {
		return outputStream.toString();
	}

	private void createOutput() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException,
			IOException, TemplateException {

		String templateName = (String) variables.get(TEMPLATE_NAME_KEY);
		if (templateName == null) {
			throw new IllegalArgumentException("no template name set");
		}

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setNumberFormat("0.######");
		cfg.setTemplateLoader(new TemplateLoader() {
			
			public void closeTemplateSource(Object inputstream) throws IOException {
				// nothing to do
			}

			public Object findTemplateSource(String name) throws IOException {
				return Bin2vhdlConvert.class.getClassLoader().getResourceAsStream(name);
			}

			public long getLastModified(Object inputstream) {
				// nothing to do
				return 0;
			}

			public Reader getReader(Object is, String encoding) throws IOException {
				return new InputStreamReader((InputStream) is);
			}
		});
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setFallbackOnNullLoopVariable(false);

		Template temp = cfg.getTemplate(templateName);

		Writer out = new OutputStreamWriter(outputStream);
		temp.process(variables, out);
	}

	public void setTemplateName(String templateName) {
		variables.put(TEMPLATE_NAME_KEY, templateName);
	}

	public void setName(String name) {
		variables.put(NAME_KEY, name);

	}

	public void setFileName(String filename) {
		variables.put(FILENAME_KEY, filename);
		variables.putIfAbsent(NAME_KEY, filename);
	}

	public void setWidth(int width) {
		variables.put(WIDTH_KEY, width);
		variables.put(WIDTH_BYTES_KEY, width / 8);
	}

}
