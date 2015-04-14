package org.weight2fit.infra;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CommandLineProcessorTest {

    @Test(expected = IllegalArgumentException.class)
    public void illegalParam() throws ParseException {
        CommandLineProcessor processor = new CommandLineProcessor();
        processor.parse("-weight 82.5  -timestamp 2014-10-03  -errorParam  -out res.fit");
    }

    @Test
    public void baseParametersParsing() throws ParseException {
        CommandLineProcessor processor = new CommandLineProcessor();
        processor.parse("-weight 82.5 -timestamp 2014-10-03 -out res.fit");

        assertEquals(new Date(2014-1900, 10-1, 03), processor.getTimestamp());
        // see double comparison at http://stackoverflow.com/a/5686848
        assertEquals(0, Double.compare(82.5, processor.getWeight()));
        assertEquals("res.fit", processor.getOutFileName());
    }
}