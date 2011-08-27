package stuylib.drive;

public interface DriveConfig {

    public int getFrontLeftMotorChannel();

    public int getRearLeftMotorChannel();

    public int getFrontRightMotorChannel();

    public int getRearRightMotorChannel();

    public int[] getRearLeftEncoderChannel();

    public int[] getRearRightEncoderChannel();

    public int[] getFrontLeftEncoderChannel();

    public int[] getFrontRightEncoderChannel();

    public int[] getPIDHeadingConstants();

    public int[] getPIDSpeedConstants();

    public boolean getUseSpeedControl();

    public double getEncoderRadPerPulse();


    public int getGyroChannel();


}
