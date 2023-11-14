// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.Map;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.TankDriveCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  public static double shooterFastSpeed = 0.8, shooterSlowSpeed = 0.5, driveSpeed = 0.5;
  public static GenericEntry fastSpeedEntry , slowSpeedEntry , driveSpeedEntry;

  public static Joystick primaryJoystick, secondaryJoystick;
  public static JoystickButton primaryTriggerRight , primaryTriggerLeft;

  public static JoystickButton safetyButton , YButton , AButton , BButton;
  // The robot's subsystems and commands are defined here...

  public static ShuffleboardTab tab = Shuffleboard.getTab("Main");
  // Replace with CommandPS4Controller or CommandJoystick if needed
  public static DrivetrainSubsystem driveTrain;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

      driveTrain = new DrivetrainSubsystem();

      configureBindings();

      tab.addBoolean("Safe Mode", ()->!Robot.isTestMode);
      tab.addBoolean("Primary Trigger", ()->primaryTriggerRight.getAsBoolean());
      tab.addBoolean("Secondary Button", ()->safetyButton.getAsBoolean());

      fastSpeedEntry = tab.add("Fast", shooterFastSpeed)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("MIN", 0, "MAX", 1))
    .getEntry();
    slowSpeedEntry = tab.add("Slow", shooterSlowSpeed)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("MIN", 0, "MAX", 1))
    .getEntry();
    driveSpeedEntry = tab.add("Drive", driveSpeed)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("MIN", 0, "MAX", 1))
    .getEntry();

    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings () {
    primaryJoystick = new Joystick (0);

    primaryTriggerLeft = new JoystickButton(primaryJoystick, LogitechControllerButtons.triggerLeft);
    primaryTriggerRight = new JoystickButton(primaryJoystick, LogitechControllerButtons.triggerRight);

    YButton = new JoystickButton(primaryJoystick, LogitechControllerButtons.y);
    AButton = new JoystickButton(primaryJoystick, LogitechControllerButtons.a);
    BButton = new JoystickButton(primaryJoystick, LogitechControllerButtons.b);

    safetyButton = new JoystickButton(primaryJoystick, LogitechControllerButtons.x);

    driveTrain.setDefaultCommand(new TankDriveCommand(driveTrain , () -> primaryJoystick.getY () , () -> primaryJoystick.getThrottle()));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
