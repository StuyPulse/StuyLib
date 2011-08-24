package stuylib.drive.prototype;

import edu.wpi.first.wpilibj.*;

public class Accumulator extends Thread {

    private boolean done = false;
    double distance,lasttime,lastsenval;
    PIDSource source;

    public Accumulator(PIDSource a){
        source = a;
        lasttime = Timer.getFPGATimestamp();
    }

    public void run(){
        while(!done){
            double time = Timer.getFPGATimestamp();
            distance += (source.pidGet() + lastsenval) * (time - lasttime)/2;
            lasttime = time;
        }
    }

    public double getDis(){
        return distance;
    }
}
