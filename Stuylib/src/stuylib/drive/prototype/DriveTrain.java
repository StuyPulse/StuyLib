package stuylib.drive.prototype;

import edu.wpi.first.wpilibj.*;

public class DriveTrain {

    VictorSpeed front_left_motor;
    VictorSpeed front_right_motor;
    VictorSpeed rear_left_motor;
    VictorSpeed rear_right_motor;

    RobotDrive rd;

    Gyro g;

    DriveTrain(DriveConfig O) {
        front_left_motor = new VictorSpeed(O.getFrontLeftMotorChannel(),
                O.getFrontLeftEncoderChannel()[0], O.getFrontLeftEncoderChannel()[1], false, O);
        front_right_motor = new VictorSpeed(O.getFrontRightMotorChannel(),
                O.getFrontRightEncoderChannel()[0], O.getFrontRightEncoderChannel()[1], true, O);
        rear_left_motor = new VictorSpeed(O.getRearLeftMotorChannel(),
                O.getRearLeftEncoderChannel()[0], O.getRearLeftEncoderChannel()[1], false, O);
        rear_right_motor = new VictorSpeed(O.getRearRightMotorChannel(),
                O.getRearRightEncoderChannel()[0], O.getRearRightEncoderChannel()[1], true, O);

        rd = new RobotDrive(front_left_motor,
                            rear_left_motor,
                            front_right_motor,
                            rear_right_motor);

        g = new Gyro(O.getGyroChannel());
    }
}

class HeadingDrive implements PIDOutput {

    DriveTrain dt;

    public HeadingDrive(DriveTrain dt) {
        this.dt = dt;
    }

    public void pidWrite(double out) {
        dt.rd.tankDrive(-out, out);
    }
}
