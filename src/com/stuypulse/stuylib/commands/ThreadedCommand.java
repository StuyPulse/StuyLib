package com.stuypulse.stuylib.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A ThreadedCommand is a command that helps run commands at higher rates than
 * 50hz. It is interfaced with like a normal command, but handles the threading
 * in the background.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class ThreadedCommand implements Command {

    /**
     * Command Runner is a thread that runs a command until its finished or
     * interrupted.
     *
     * This does not handle initialize or end, that is for threaded command
     */
    private static class CommandRunner extends Thread {

        private double mRate;
        private SynchronizedCommand mBaseCommand;

        /**
         * Makes a CommandRunner with custom rate
         *
         * @param command command to run on thread
         * @param rate    time in between executes
         */
        public CommandRunner(SynchronizedCommand command, double rate) {
            mRate = rate;
            mBaseCommand = command;
        }

        /**
         * Makes a CommandRunner with a default rate of 50hz
         *
         * @param command command to run on thread
         */
        public CommandRunner(SynchronizedCommand command) {
            this(command, 0.02);
        }

        @Override
        public void run() {
            do {
                mBaseCommand.execute();

                try {
                    Thread.sleep((long) (mRate * 1000.0));
                } catch(InterruptedException e) {
                    return;
                }
            } while(mBaseCommand.isFinished() && this.isInterrupted());
        }
    }

    private SynchronizedCommand mBaseCommand;
    private CommandRunner mCommandRunner;

    /**
     * Make a Threaded Command with a custom rate
     *
     * @param command command to be run on the thread
     * @param rate    the time in between executes
     */
    public ThreadedCommand(Command command, double rate) {
        mBaseCommand = new SynchronizedCommand(command);
        mCommandRunner = new CommandRunner(mBaseCommand, rate);
    }

    /**
     * Make a Threaded Command with a default rate of 50hz
     *
     * @param command command to be run on the thread
     */
    public ThreadedCommand(Command command) {
        mBaseCommand = new SynchronizedCommand(command);
        mCommandRunner = new CommandRunner(mBaseCommand);
    }

    @Override
    public synchronized void initialize() {
        mBaseCommand.initialize();
        mCommandRunner.start();
    }

    @Override
    public synchronized void execute() {
        //
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
        return "Synchronized" + mBaseCommand.getName();
    }

}
