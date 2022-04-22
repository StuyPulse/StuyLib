package com.stuypulse.stuylib.math.RPMCalculator;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.math.Vector;

// d = V(i)t + 1/2 * a * t*t
public class DisplacementEquation{

    private final double displacement;
    private final double horizontalAcceleration;
    private final Vector2D vectorComponents;
     private static final double gravity = -9.8; // m /s^2   
    public DisplacementEquation(double horizontalAcceleration, double displacement, Vector2D vectorComponents) {
        this.horizontalAcceleration = horizontalAcceleration;
        this.displacement = displacement;
        this.vectorComponents = vectorComponents;
    }

    public double isolateVariable(double time, boolean verticalComponent){ // isolating V(i) 
        
        double U;
        if (verticalComponent == true){
            U = vectorComponents.y;
        } else {
            U = vectorComponents.x;
        }
       
        
        U -= .5 * horizontalAcceleration * time * time;
        U /= time; 
        
        return U;
    }


    public double findingU(double netHeightOfGoal){ // not asking you for a date, but would be nice to have one. U is initial Velocity 
        // solving for time
        Angle angle = Angle.fromVector(vectorComponents); // I need the angle in radians
        double time = displacement / angle.cos(); // it's divided by V(i)

        //plugging in time
        double lhs = netHeightOfGoal + time * vectorComponents.x;
        double rhs = 0.5 * gravity * Math.pow(time, 2); 
        double toU = Math.sqrt(rhs / lhs);

        return toU; // :)
    }

    

    // public static void main(String[] args){ // test 
    //    DisplacementEquation dis = new DisplacementEquation(10, 10);

    //    double initVel = dis.isolateVariable(10);
       
    //    System.out.println(initVel); 

    // }

}
