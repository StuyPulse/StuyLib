package com.stuypulse.stuylib.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A ThreadedCommand is a command that helps run commands at higher periods than 50hz. It is
 * interfaced with like a normal command, but handles the threading in the background.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class ThreadedCommand implements Command {

    /**
     * Command Runner is a thread that runs a command until its finished or interrupted.
     *
     * This does not handle initialize or end, that is for threaded command
     */
    private static class CommandRunner extends Thread {

        private long mPeriod;
        private SynchronizedCommand mBaseCommand;

        /**
         * Makes a CommandRunner with custom period
         *
         * @param command command to run on thread
         * @param period  time in between executes
         */
        public CommandRunner(SynchronizedCommand command, double period) {
            mPeriod = (long) (period * 1000.0);
            mBaseCommand = command;
        }

        @Override
        public void run() {
            try {
                do {
                    mBaseCommand.execute();

                    try {
                        Thread.sleep(mPeriod);
                    } catch(InterruptedException e) {
                        return;
                    }
                } while(!mBaseCommand.isFinished() && !Thread.interrupted());
            } finally {
                mBaseCommand = null;
            }
        }
    }

    private double mPeriod;
    private SynchronizedCommand mBaseCommand;
    private CommandRunner mCommandRunner;

    /**
     * Make a Threaded Command with a custom period
     *
     * @param command command to be run on the thread
     * @param period  the time in between executes
     */
    public ThreadedCommand(Command command, double period) {
        if(period <= 0) {
            throw new IllegalArgumentException("period must be greater than 0");
        }

        mBaseCommand = new SynchronizedCommand(command);
        mPeriod = period;
    }

    /**
     * Make a Threaded Command with a default period of 50hz
     *
     * @param command command to be run on the thread
     */
    public ThreadedCommand(Command command) {
        this(command, 0.02);
    }

    /**
     * Start thread and interrupt the old one if it is running
     */
    private void startCommandRunner() {
        if(mCommandRunner != null && mCommandRunner.isAlive()) {
            mCommandRunner.interrupt();
        }

        mCommandRunner = new CommandRunner(mBaseCommand, mPeriod);
        mCommandRunner.setName("CommandRunner-" + mBaseCommand.getName());
        mCommandRunner.start();
    }

    @Override
    public synchronized void initialize() {
        mBaseCommand.initialize();
    }

    @Override
    public synchronized void execute() {
        if(mCommandRunner == null) {
            startCommandRunner();
        } else {
            if(!mCommandRunner.isAlive()) {
                startCommandRunner();
            }
        }
    }

    @Override
    public synchronized void end(boolean interrupted) {
        mCommandRunner.interrupt();
        mBaseCommand.end(interrupted);
    }

    @Override
    public synchronized boolean isFinished() {
        return mCommandRunner.isAlive() && mBaseCommand.isFinished();
    }

    @Override
    public synchronized Set<Subsystem> getRequirements() {
        return mBaseCommand.getRequirements();
    }

    @Override
    public synchronized boolean hasRequirement(Subsystem requirement) {
        return mBaseCommand.hasRequirement(requirement);
    }

    @Override
    public synchronized boolean runsWhenDisabled() {
        return mBaseCommand.runsWhenDisabled();
    }

    @Override
    public synchronized String getName() {
        return "Threaded" + mBaseCommand.getName();
    }
}
