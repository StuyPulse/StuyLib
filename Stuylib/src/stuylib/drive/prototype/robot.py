#!/usr/bin/python3.1

try:
    import wpilib
except:
    from fake_wpilib import fake_wpilib as wpilib

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
        self.drive_train.translate(13, 3)
        while True:
            print("dist:", self.drive_train.distance_traveled(), \
                  "x:", self.drive_train.coord.x(), "y:", self.drive_train.coord.y())
            time.sleep(0.1)

def run():
    robot = MyRobot()
    robot.StartCompetition()

if __name__ == "__main__":
    run()
