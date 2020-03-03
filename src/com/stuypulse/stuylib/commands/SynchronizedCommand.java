package com.stuypulse.stuylib.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A synchronized command takes in a command and synchronizes all of the methods. This can be used
 * when a command is being passed around threads to prevent data races.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SynchronizedCommand implements Command {

    // Command to synchronize
    private Command mBaseCommand;

    /**
     * Initialize a Synchronized Command with another command.
     *
     * @param command base command that will be synchronized
     */
    public SynchronizedCommand(Command command) {
        mBaseCommand = command;
    }

    @Override
    public synchronized void initialize() {
        mBaseCommand.initialize();
    }

    @Override
    public synchronized void execute() {
        mBaseCommand.execute();
    }

    @Override
    public synchronized void end(boolean interrupted) {
        mBaseCommand.end(interrupted);
    }

    @Override
    public synchronized boolean isFinished() {
        return mBaseCommand.isFinished();
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
