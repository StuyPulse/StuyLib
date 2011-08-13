try:
    import wpilib
except:
    import fake_wpilib as wpilib

import threading
import time
import math

class heading_drive:
    def __init__(self, drive):
        self.drive = drive
    def PIDWrite(self, out):
        self.drive.left_motor.Set(-out)
        self.drive.right_motor.Set(out)
        
class dt:
    def __init__(self):
        self.left_motor = wpilib.Jaguar(1)
        self.right_motor = wpilib.Jaguar(2)
        self.left_enc = wpilib.Encoder(6, 5)
        self.right_enc = wpilib.Encoder(8, 7)
        self.drive_gyro_accumulator = Accumulator(self.gyro_rate)
        self.drive_gyro_accumulator.setDaemon(True)
        self.drive_gyro_accumulator.start()
        self.heading_drive = heading_drive(self)
        
        self.heading_control = wpilib.PIDController(0.5, 0, 0, \
                                                    self.drive_gyro_accumulator, \
                                                    self.heading_drive)
        self.heading_control.SetOutputRange(-1, 1)
        self.heading_control.SetInputRange(-2*math.pi, 2*math.pi)
        self.heading_control.SetContinuous(False)
        
    def gyro_rate(self):
        return (self.right_enc.GetRate() - self.left_enc.GetRate())/5
    
    def face(self, theta):
        self.heading_control.SetSetpoint(theta)
        self.heading_control.Enable()
    
    def translate(self, x, y):
        turn(math.atan2(y, x))
        forward(math.sqrt(x*x + y*y))
        
class Accumulator(threading.Thread):
    def __init__(self, sensor_func):
        self.sensor_func = sensor_func
        self.last_sensor_val = 0
        self.last_time = time.clock()
        self.integral = 0
        threading.Thread.__init__(self)

    def PIDGet(self):
        return self.integral

    def run(self):
        while True:
            current_time = time.clock()
            sensor_val = self.sensor_func()
            dt = current_time - self.last_time
            trapezoid = (sensor_val + self.last_sensor_val) * dt / 2
            self.integral += trapezoid
            self.last_time = current_time
            self.last_sensor_val = sensor_val