// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.subsystems.shooter.shooterSubsystem;
import frc.robot.subsystems.Uptake.UptakeSubsystem;
import frc.robot.subsystems.Intake.IntakeSubsystem;
import frc.robot.subsystems.Piviot.piviotSubsystem;
import frc.robot.subsystems.proximity.proximitysubsystem;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Swerve.SwerveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.OperatorConstants;
import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
   private final IntakeSubsystem m_intake = new IntakeSubsystem();
   private final UptakeSubsystem m_uptake = new UptakeSubsystem();
   private final shooterSubsystem m_shooter = new shooterSubsystem();
   private final piviotSubsystem m_piviot = new piviotSubsystem();
   private final proximitysubsystem m_proximity = new proximitysubsystem();
  
  private final SwerveSubsystem m_drivebase = SwerveSubsystem.getInstance();

  private final SendableChooser<Command> autoChooser;
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configurePathPlanner();
    autoChooser = AutoBuilder.buildAutoChooser("Simple Auto");
    Shuffleboard.getTab("Pre-Match").add("Auto Chooser", autoChooser);
    configureBindings(); // Configure the trigger bindings
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
  private void configureBindings() {

     Constants.operatorController.b().or(Constants.driverController.rightTrigger(.1)).or(m_proximity.piecein.whileTrue(m_intake.startIntaking().andThen(m_uptake.startUptaking())));
     Constants.operatorController.b().or(Constants.driverController.rightTrigger(.1)).or(m_proximity.piecein.whileFalse(m_intake.stopIntaking().andThen((m_uptake.stopUptaking()))));
     Constants.operatorController.x().whileTrue(m_uptake.startUptaking());
     Constants.operatorController.x().whileFalse(m_uptake.stopUptaking());
     Constants.operatorController.y().whileTrue(m_shooter.startSpeakerCommand());
     Constants.operatorController.a().whileTrue(m_shooter.startAmpCommand());
     Constants.operatorController.y().or(Constants.operatorController.a()).whileFalse(m_shooter.stopShooterCommand());
  }


// Nothing under here uses our subsystem right now!




  
  public void configurePathPlanner() {
    // TODO: These are example NamedCommands, import the real NamedCommands from the `swerve` branch
    // NamedCommands.registerCommand("Ground Intake",
            // superstructure.toState(SuperState.GROUND_INTAKE).withTimeout(3));
    // NamedCommands.registerCommand("Safe", superstructure.toState(SuperState.SAFE).withTimeout(3));
    // NamedCommands.registerCommand("TestShoot1", m_shooter.shootIt(-5000));
    // NamedCommands.registerCommand("RunFeeder", m_feeder.runFeeder(0.3));
    m_drivebase.setupPathPlanner();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // A Path will be run in autonomous
    //(null)
//    return drivebase.spinCounterClockwise();//drivebase.getAutonomousCommand("TESTER", true);

    // Runs an Auto
//    return new PathPlannerAuto("Simple Auto");

//    Gets Selected Auto from Shuffleboard
    return autoChooser.getSelected();
//    return null;
  }

  public void setDriveMode()
  {
    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation
    Command driveFieldOrientedDirectAngle = m_drivebase.driveCommand(
            () -> MathUtil.applyDeadband(Constants.driverController.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
            () -> MathUtil.applyDeadband(Constants.driverController.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
            () -> -Constants.driverController.getRightX(),
            () -> -Constants.driverController.getRightY());

    Command driveFieldOrientedDirectAngleSim = m_drivebase.simDriveCommand(
            () -> MathUtil.applyDeadband(Constants.driverController.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
            () -> MathUtil.applyDeadband(Constants.driverController.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
            () -> Constants.driverController.getRawAxis(2));
    Command driveinfinityturn = m_drivebase.driveCommand(() -> MathUtil.applyDeadband(Constants.driverController.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
            () -> MathUtil.applyDeadband(Constants.driverController.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
            () ->MathUtil.applyDeadband( Constants.driverController.getRightX(),.3));

    m_drivebase.setDefaultCommand(
            !RobotBase.isSimulation() ? driveinfinityturn : driveinfinityturn);

  }

  public void setMotorBrake(boolean brake)
  {
    m_drivebase.setMotorBrake(brake);
  }
}
