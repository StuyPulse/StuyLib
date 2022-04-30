/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math.RPMCalculator;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

/**
 * This class solves for variables and inital velocity. It uses the displacement equation
 *          d = V(i)t + 1/2 * a * t*t
 * in which d = displacement, V(i) is inital velocity, a is acceleraiton, and t is time. 
 * This equation is used to solve for both horizontal and vertical component, and uses that to solve for inital velocity (of a ball)
 */
public class RPMChange{

    private final double modifiedDisplacement;
    private final double horizontalAcceleration;
    private final Vector2D vectorComponents;
    private static final double gravity = -9.8; // m /s^2  
    private final double velocityToRPM;

    /**
     * This class finds the Velocity that the shooter should launch at. The RPM will be adjusted according to the shooter angle
     * @param horizontalAcceleration the horizontal acceleration of the ball. Will most likely be zero (neglecting air resistance) 
     * @param displacement the distance from the goal
     * @param launchAngle the angle the ball is launched at
     * @param distanceOffset most of the time, we don't want the peak of the parabola to be directly over the goal, so we offset the peak
     * @param velocityToRPM using physics only gives us the velocity the ball should be launched at. In order to be useful to us, we want the RPM. I'm hoping it's linear. 
     */

    public RPMChange(double horizontalAcceleration, double displacement, double launchAngle, double distanceOffset, double velocityToRPM) {
        this.horizontalAcceleration = horizontalAcceleration; // most likely 0, but here for completeness
        this.velocityToRPM = velocityToRPM;
        double midpoint = displacement / 2;
        double modifiedMidpoint = midpoint * Math.sin(launchAngle) - distanceOffset; // we want the peak of the trajectory to be right before the goal
        modifiedDisplacement = modifiedMidpoint * 2;

        // we double the displacement so that the highest point is over the goal. (this is assuming for a 45 degree angle)
        // If not a 45 degree angle, we take sin(angle) * root 2
        // we multiply by root 2 so that the sin(45) * root2 = 1. (45 degrees is the farthest distance possible. Everything else is relative to this)



        // finds the x and y component for a vector
        double xComponent = Math.cos(launchAngle);
        double yComponent = Math.sin(launchAngle);

        this.vectorComponents = new Vector2D(xComponent, yComponent);


        
    }

    // I don't know why this would ever be useful
    public double isolateInitVel(double time, boolean verticalComponent){ // isolating V(i) 
        
        double initalVelocity; 
        if (verticalComponent == true){
            initalVelocity = vectorComponents.y;
        } else {
            initalVelocity = vectorComponents.x;
        }
       
        
        initalVelocity -= .5 * horizontalAcceleration * time * time;
        initalVelocity /= time; 
        
        return initalVelocity;
    }



    /**
     * 
     * @param netHeight
     * @return
     */
    public double findRPM(double netHeight){ // The net height is height of the target - the height the ball leaves contact with the shooter
        // solving for time from horizontal component. The answer will be divided by V(i)
        Angle angle = Angle.fromVector(vectorComponents); 
        double time = 2 * modifiedDisplacement / angle.cos(); // not shown here but it's also divided by V(i)


        //plugging in time
        double lhs = netHeight + time * vectorComponents.x;
        double rhs = 0.5 * gravity * Math.pow(time, 2); 
        double initalVel = Math.sqrt(rhs / lhs);

        return initalVel  * velocityToRPM; 

        
        
    
// the method below is a has superior naming than the one above. It does the same thing though. 
// public double findingU(double netHeightOfGoal){ // not asking you for a date, but would be nice to have one. U is initial Velocity 
    //     // solving for time from horizontal component. The answer will be divided by V(i)
    //     Angle angle = Angle.fromVector(vectorComponents); 
    //     double time = displacement / angle.cos(); // not shown here but it's also divided by V(i)

    //     //plugging in time
    //     double lhs = netHeightOfGoal + time * vectorComponents.x;
    //     double rhs = 0.5 * gravity * Math.pow(time, 2); 
    //     double toU = Math.sqrt(rhs / lhs);

    //     return toU; // :)
    // }

    }

}
