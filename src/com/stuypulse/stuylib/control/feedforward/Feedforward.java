package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.streams.filters.Derivative;

public abstract class Feedforward {
    
    private final Derivative mDerivative;

    public Feedforward() {
        mDerivative = new Derivative();
    }

    public final FeedforwardController controller() {
        return new FeedforwardController(this);
    }

    public final double calculate(double velocity) {
        return calculate(velocity, mDerivative.get(velocity));
    }

    protected abstract double calculate(double velocity, double acceleration);

    public static class Motor extends Feedforward {
        private final Number kS, kV, kA;

        public Motor(Number kS, Number kV, Number kA) {
            this.kS = kS;
            this.kV = kV;
            this.kA = kA;
        }

        protected double calculate(double velocity, double acceleration) {
            return kS.doubleValue() * Math.signum(velocity) + kV.doubleValue() * velocity + kA.doubleValue() * acceleration;
        }
    }

    public static class Drivetrain extends Motor {
        public Drivetrain(Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
        }
    }

    public static class Flywheel extends Motor {
        public Flywheel(Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
        }
    }

    public static class Elevator extends Motor {
        private final Number kG;

        public Elevator(Number kG, Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
            this.kG = kG;
        }

        @Override
        protected double calculate(double velocity, double acceleration) {
            return kG.doubleValue() + super.calculate(velocity, acceleration);
        }
    }

    public static class Arm extends Motor {
        private final Number kG;
        public Arm(Number kG, Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
            this.kG = kG;
        }

        @Override
        protected double calculate(double velocity, double acceleration) {
            return Math.cos(kG.doubleValue()) + super.calculate(velocity, acceleration);
        }
    }

}