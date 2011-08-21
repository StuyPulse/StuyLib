try:
    import wpilib
except:
    from fake_wpilib import fake_wpilib as wpilib

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
        
        self.heading_control = wpilib.PIDController(0.5, 0.04, 0, \
                                                    self.drive_gyro_accumulator, \
                                                    self.heading_drive)
        self.heading_control.SetOutputRange(-1, 1)
        self.heading_control.SetInputRange(-2*math.pi, 2*math.pi)
        self.heading_control.SetContinuous(False)
        
        self.coord = CoordinateEstimator(self)
        
    def gyro_rate(self):
        return (self.right_enc.GetRate() - self.left_enc.GetRate())*3/20
    
    def face(self, theta):
        self.heading_control.SetSetpoint(theta)
        self.heading_control.Enable()
        time.sleep(1)
        while abs(theta - self.drive_gyro_accumulator.PIDGet()) > 0.01:
            print("angle error:", abs(theta - self.drive_gyro_accumulator.PIDGet()))
        self.heading_control.Disable()
        self.heading_drive.PIDWrite(0)
        print("angle error:", abs(theta - self.drive_gyro_accumulator.PIDGet()))
        print("------ Done turning ----------")
    
    def forward(self, dist):
        print("go forward:", dist)
        begin_dist = self.distance_traveled()
        last_print_time = time.clock()
        while self.distance_traveled() - begin_dist < dist:
            print("dist:", self.distance_traveled(), \
                  "x:", self.coord.x(), "y:", self.coord.y())
            
            self.left_motor.Set(1)
            self.right_motor.Set(1)
        self.left_motor.Set(-1)
        self.right_motor.Set(-1)
        print("-------- Done going forward ------")
        print("dist:", self.distance_traveled(), \
              "x:", self.coord.x(), "y:", self.coord.y())
        
    
    def translate(self, x, y):
        self.face(math.atan2(y, x))
        self.forward(math.sqrt(x*x + y*y))
    
    def distance_traveled(self):
        return (self.left_enc.GetDistance() + self.right_enc.GetDistance()) / 2
    
class CoordinateEstimator:
    def __init__(self, drive):
        def dx():
            return (drive.left_enc.GetRate() + drive.right_enc.GetRate())/2 * math.cos(drive.drive_gyro_accumulator.PIDGet())
        def dy():
            return (drive.left_enc.GetRate() + drive.right_enc.GetRate())/2 * math.sin(drive.drive_gyro_accumulator.PIDGet())
        
        self.x_accum = Accumulator(dx)
        self.x_accum.setDaemon(True)
        self.x_accum.start()
        
        self.y_accum = Accumulator(dy)
        self.y_accum.setDaemon(True)
        self.y_accum.start()
        
    def x(self):
        return self.x_accum.PIDGet()
        
    def y(self):
        return self.y_accum.PIDGet()
    
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