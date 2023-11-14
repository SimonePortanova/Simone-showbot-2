package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TankDriveCommand extends CommandBase {
    DrivetrainSubsystem drivetrain;
    DoubleSupplier rightStick , leftStick;

    public TankDriveCommand (DrivetrainSubsystem drivetrain , DoubleSupplier rightStick , DoubleSupplier leftStick) {
        this.drivetrain = drivetrain;
        this.rightStick = rightStick;
        this.leftStick = leftStick;

        addRequirements(drivetrain);
    }

    double deadZone (double in) {
        if(Math.abs(in) <= Constants.DEADZONE_AMOUNT)
           return 0;
        return in;
    }

    public void exe () {
        drivetrain.setMotorPowers(deadZone(leftStick.getAsDouble()), deadZone(rightStick.getAsDouble()), "Command received from Joystick" );
    }
    
}
