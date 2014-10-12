package org.kontiky.weight2fit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO add time support (params with spaces screening)
 * TODO create command-line usage string
 * TODO create parser exception instead of ParseException
 */
public class CommandLineProcessor {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Date timestamp;
    private double weight;
    private String outFileName;

    public void parse(String str) throws ParseException {
        parse(splitArgs(str));
    }

    public void parse(String... args) throws ParseException {
        for (int i = 0; i < args.length;) {
            String param = args[i++];
            switch (param) {
                case "-timestamp":
                    timestamp = DATE_FORMAT.parse(args[i++]);
                    break;
                case "-weight":
                    weight = Double.valueOf(args[i++]);
                    break;
                case "-out":
                    outFileName = args[i++];
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: " + param);
            }
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getWeight() {
        return weight;
    }

    public String getOutFileName() {
        return outFileName;
    }

    private String[] splitArgs(String str) {
        return str.split("\\s");
    }
}
