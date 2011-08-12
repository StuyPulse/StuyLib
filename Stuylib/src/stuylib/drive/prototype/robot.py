#!/usr/bin/python3.1

try:
    import wpilib
except:
    import fake_wpilib as wpilib

import threading
import time
import math
import drive

class MyRobot(wpilib.SimpleRobot):
    def __init__(self):
        self.arm_motor = wpilib.Jaguar(3)
        self.arm_gyro = wpilib.Gyro(1)
        self.drive_train = drive.dt()
    
    def Autonomous(self):
        self.drive_train.heading_control.SetSetpoint(math.pi)
        self.drive_train.heading_control.Enable()
        #self.drive_train.
        while True:
            print("gyro rate:", self.drive_train.gyro_rate(), \
                  "gyro angle:", self.drive_train.drive_gyro_accumulator.PIDGet())
            time.sleep(0.25)

def run():
    robot = MyRobot()
    robot.StartCompetition()

if __name__ == "__main__":
    run()
