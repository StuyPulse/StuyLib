package stuylib.drive.prototype;

import edu.wpi.first.wpilibj.*;

public class DriveTrain {

    VictorSpeed front_left_motor;
    VictorSpeed front_right_motor;
    VictorSpeed rear_left_motor;
    VictorSpeed rear_right_motor;

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

        g = new Gyro(O.getGyroChannel());
    }
}
