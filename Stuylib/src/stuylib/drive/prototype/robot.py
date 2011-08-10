#!/usr/bin/python3.1

try:
    import wpilib
except:
    import fake_wpilib as wpilib

import threading
import time

class MyRobot(wpilib.SimpleRobot):
    def __init__(self):
        self.v = wpilib.Jaguar(1)
        self.w = wpilib.Jaguar(2)
        self.arm_motor = wpilib.Jaguar(3)
        self.arm_gyro = wpilib.Gyro(1)
        self.left_enc = wpilib.Encoder(6, 5)
        self.right_enc = wpilib.Encoder(8, 7)
        #self.drive_gyro_rate = wpilib.Gyro(9)
        #self.drive_gyro_accumulator = Accumulator(self.drive_gyro_rate.GetAngle)
        #self.drive_gyro_accumulator.setDaemon(True)
        #self.drive_gyro_accumulator.start()

    
        self.drive_gyro_accumulator = Accumulator(self.gyro_rate)
        self.drive_gyro_accumulator.setDaemon(True)
        self.drive_gyro_accumulator.start()

    def gyro_rate(self):
        return 10 * (self.left_enc.GetRate() - self.right_enc.GetRate())
	
    def Autonomous(self):
        self.v.Set(0.6)
        self.w.Set(-0.6)
        while True:
            print("gyro rate:", self.gyro_rate(), "gyro angle:", self.drive_gyro_accumulator.PIDGet())
            time.sleep(0.25)

class Accumulator(threading.Thread):
    def __init__(self, sensor_func):
        self.sensor_func = sensor_func
        self.val = 0
        self.last_time = time.clock()
        threading.Thread.__init__(self)

    def PIDGet(self):
        return self.val

    def run(self):
        while True:
            current_time = time.clock()
            dt = current_time - self.last_time
            sensor_val = self.sensor_func()
            self.val += sensor_val * dt
            self.last_time = current_time
            time.sleep(0.5)
            

def run():
    robot = MyRobot()
    robot.StartCompetition()

if __name__ == "__main__":
    run()
