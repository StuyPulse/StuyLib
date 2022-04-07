package com.stuypulse.stuylib.math.interpolation;


import com.stuypulse.stuylib.math.Vector2D;

public class CubicInterpolator implements Interpolator{
    Vector2D point1;
    Vector2D point2;
    Vector2D point3;
    Vector2D point4;
    double a;
    double b;
    double c;
    double d; 

    public CubicInterpolator(Vector2D point1, Vector2D point2, Vector2D point3, Vector2D point4){
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }

   public static double[] getEquation(Vector2D point1, Vector2D point2, Vector2D point3, Vector2D point4){
    // math 
    


    // TODO: store everything below into a new class: GassianElimination
    // write 3 equations out of the three points.
    // store the equations in 3 arrays, and we can manipulate those arrays to get new arrays


    // all equations are in the form {coeffient of a, coefficent of b, coeffient of c, coeffiecent of d, equals to (usually y)}

    double equation1[] = InterpolatorMath.getOriginalEquation(point1, 3);
    double equation2[] = InterpolatorMath.getOriginalEquation(point2, 3);
    double equation3[] = InterpolatorMath.getOriginalEquation(point3, 3);
    double equation4[] = InterpolatorMath.getOriginalEquation(point4, 3);


    double tripleVariableEquation1[] = InterpolatorMath.worklessCancel(equation1, equation2);
    double tripleVariableEquation2[] = InterpolatorMath.worklessCancel(equation1, equation3);
    double tripleVariableEquation3[] = InterpolatorMath.worklessCancel(equation1, equation4);

    // we should be left with a set of triple variable equations. We then repeat. 

    double doubleVariableEquation1[] = InterpolatorMath.worklessCancel(tripleVariableEquation1, tripleVariableEquation2);
    double doubleVariableEquation2[] = InterpolatorMath.worklessCancel(tripleVariableEquation1, tripleVariableEquation3);

    // now we have two doubleVariableEquations. We can now cancel to one variable

    double singleVariableEquation[] = InterpolatorMath.worklessCancel(doubleVariableEquation1, doubleVariableEquation2);
    
    // we now have a singleVariable equation equal to a y value. we now have all the nessecary information to do cancelling
    // we use one of each equation, a original equation, tripleVariable, a double, and the singleVariable equation and we can plug and solve


    double d = singleVariableEquation[4] / singleVariableEquation[3]; // a * x = num
    double c = (doubleVariableEquation1[4] - (d * doubleVariableEquation1[3])) / doubleVariableEquation1[2]; // ax + by = num, (num - by)/a
    double b = (tripleVariableEquation1[4] - ((d * tripleVariableEquation1[3]) + (c * tripleVariableEquation1[2]))) / tripleVariableEquation1[1];
    double a = equation1[4] - ((d * equation1[3]) + (c * equation1[2]) + (b * equation1[1])) / equation1[0];

    double derivedEquation[] = {a, b, c, d};

    return derivedEquation;
   }

   public static void main(String args[]) {
    double[] test = getEquation(new Vector2D(-2, 9), new Vector2D(-1, 2), new Vector2D(0, 3), new Vector2D(1, 12));
    for (int i = 0; i < test.length; i ++){
        System.out.println(test[i]);

    }
   }
   
   
   
   
   


    @Override
    public double interpolate(double x) {
        // TODO Auto-generated method stub
        return 0.0;
    }
    
}
