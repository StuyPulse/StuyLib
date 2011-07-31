/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.util;

import edu.wpi.first.wpilibj.Encoder;
import stuylib.io.DashboardUpdater;

/**
 * WPIlibj Encoder wrapper.  Pushes data to the dashboard continuously.
 * @author Alejandro Carrillo
 */
public class XEncoder extends Encoder implements SensorHelper {

    private DashboardUpdater dashboard;

    public XEncoder(Config cfg) {

        super(/*Data extracted from cfg*/0, 0);
    }

    @Override
    public void reportToDashboard() {
        //dashboard.asdf(hjkl)
    }

}


