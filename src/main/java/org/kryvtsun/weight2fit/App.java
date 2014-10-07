package org.kryvtsun.weight2fit;

import com.garmin.fit.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        Mesg msg = Factory.createMesg(MesgNum.WEIGHT_SCALE);

//        FileEncoder encoder = new FileEncoder(new File("test.fit"));
//        WeightScaleMesg mesg = new WeightScaleMesg();
//        mesg.setWeight(82f);
//        encoder.write(mesg);
//        encoder.close();
    }
}
