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
        
        self.heading_control = wpilib.PIDController(0.5, 0.04, 0, \
                                                    self.drive_gyro_accumulator, \
                                                    self.heading_drive)
        self.heading_control.SetOutputRange(-1, 1)
        self.heading_control.SetInputRange(-2*math.pi, 2*math.pi)
        self.heading_control.SetContinuous(False)
        
    def gyro_rate(self):
        return (self.right_enc.GetRate() - self.left_enc.GetRate())*3/20
    
    def face(self, theta):
        self.heading_control.SetSetpoint(theta)
        self.heading_control.Enable()
        time.sleep(1)
        while abs(self.heading_control.m_prevError) > 0.1:
            pass
        self.heading_control.Disable()
        self.heading_drive.PIDWrite(0)
    
    def forward(self, dist):
        print("go forward:", dist)
        begin_dist = self.distance_traveled()
        while self.distance_traveled() - begin_dist < dist:
            #print("begin_dist:", begin_dist, \
            #      "distance_traveled:", self.distance_traveled(), \
            #      "to go:", self.distance_traveled() - begin_dist - dist)
            self.left_motor.Set(1)
            self.right_motor.Set(1)
        self.left_motor.Set(0)
        self.right_motor.Set(0)
    
    def translate(self, x, y):
        self.face(math.atan2(y, x))
        self.forward(math.sqrt(x*x + y*y))
    
    def distance_traveled(self):
        return (self.left_enc.GetDistance() + self.right_enc.GetDistance()) / 2
    
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