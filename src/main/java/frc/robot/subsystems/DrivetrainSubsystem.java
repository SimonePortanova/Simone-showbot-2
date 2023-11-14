package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ITeamTalon;
import frc.robot.Ports;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.TeamTalonFX;


public class DrivetrainSubsystem extends SubsystemBase {
    
    ITeamTalon rightDriveFalconMain;
    ITeamTalon leftDriveFalconMain;
    ITeamTalon rightDriveFalconSub;
    ITeamTalon leftDriveFalconSub;

    public double speedMod;

    public DrivetrainSubsystem () {

        rightDriveFalconMain = new TeamTalonFX(getName(), Ports.RightDriveFalconMainCAN);
        leftDriveFalconMain = new TeamTalonFX(getName(), Ports.LeftDriveFalconMainCAN);
        rightDriveFalconSub = new TeamTalonFX(getName(), Ports.RightDriveFalconSubCAN);
        leftDriveFalconSub = new TeamTalonFX (getName() , Ports.LeftDriveFalconSubCAN);

        TalonFXConfiguration configuration = new TalonFXConfiguration();

        rightDriveFalconMain.configBaseAllSettings(configuration);
        leftDriveFalconMain.configBaseAllSettings(configuration);
        rightDriveFalconMain.follow(rightDriveFalconMain);
        leftDriveFalconSub.follow(leftDriveFalconMain);

        rightDriveFalconMain.setInverted(true);
        leftDriveFalconMain.setInverted(false);
        rightDriveFalconSub.setInverted(true);
        leftDriveFalconSub.setInverted(false);


    }

    public double getCappedPower (double power){
        return Math.max ( Math.min( 1 , power ) , - 1 );
    }

    public double getRampedPower (double power1 , double power2){
        if( power1 > power2 ) {
            return Math.max( power2 , power1 - Constants.DRIVETRAIN_MAX_POWER_CHANGE );
        } else {
            if( power1 < power2 ) {
                return Math.max( power2 , power1 - Constants.DRIVETRAIN_MAX_POWER_CHANGE );
            }
        }
        return power2;
    }

    public double setMotorPowers (double leftMotor , double rightMotor , String reason) {
        speedMod = RobotContainer.driveSpeedEntry.getDouble(RobotContainer.driveSpeed);

        if(Robot.isTestMode) {
            double rightPowerNow = rightDriveFalconMain.get ();
            double leftPowerNow = leftDriveFalconMain.get ();

            leftMotor *= speedMod;
            rightMotor *= speedMod;

            leftMotor = getRampedPower(leftMotor , leftPowerNow);
            rightMotor = getRampedPower(rightMotor, rightPowerNow);

            leftMotor = getCappedPower(leftMotor);
            rightMotor = getCappedPower(rightMotor);

            leftDriveFalconMain.set(leftMotor , reason);
            rightDriveFalconMain.set(rightMotor , reason);
        } else 
            if (!Robot.isTestMode) {

                leftDriveFalconMain.set(0 , "no test mode");
                rightDriveFalconMain.set(0 , "no test mode");

            }
            return 0;
    }
}
