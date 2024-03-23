// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.lang.Thread;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.subsystems.Vision.VisionSubsystem;
//import frc.robot.subsystems.LED.LEDSubsystem;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.proximity.proximitysubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import java.io.File;
import java.io.IOException;
import swervelib.parser.SwerveParser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private XboxController operatorController;

  private static Robot instance;
  private Command m_autonomousCommand;
  private XboxController driverController;
  private RobotContainer m_robotContainer;

  // private LEDSubsystem m_ledSubsystem;

  private Timer disabledTimer;
  AnalogInput climb1 = new AnalogInput(3);
  AnalogInput climb2 = new AnalogInput(2);
  public Robot() {
    instance = this;
  }

  public static Robot getInstance() {
    return instance;
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    operatorController = new XboxController(1);
    driverController = new XboxController(0);
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // Create a timer to disable motor brake a few seconds after disable. This will
    // let the robot stop
    // immediately when disabled, but then also let it be pushed more
    disabledTimer = new Timer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    m_robotContainer.m_drivebase.updateEstimatedPose(m_robotContainer.m_vision);
    SmartDashboard.putNumber("A", m_robotContainer.a);
    if (driverController.getBackButton()) {
      m_robotContainer.a = 1;
    } else if (driverController.getStartButton()) {
      m_robotContainer.a = -1;
    }
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    // m_ledSubsystem.disabledLED();
    m_robotContainer.setMotorBrake(true);
    disabledTimer.reset();
    disabledTimer.start();
  }

  @Override
  public void disabledPeriodic() {
    if (disabledTimer.hasElapsed(Constants.DrivebaseConstants.WHEEL_LOCK_TIME)) {
      m_robotContainer.setMotorBrake(false);
      disabledTimer.stop();
    }
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_robotContainer.m_shooter.sauto = true;
    m_robotContainer.m_intake.auto = true;
    m_robotContainer.setMotorBrake(true);
    m_robotContainer.m_piviot.piviotsetpoint=40.5;
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    m_robotContainer.m_shooter.sauto = false;
    m_robotContainer.m_intake.auto = false;

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    m_robotContainer.setDriveMode();
    m_robotContainer.setMotorBrake(true);
    m_robotContainer.m_intake.uptakeshoot = false;
    m_robotContainer.m_intake.intake = false;
    m_robotContainer.m_shooter.spinup = false;

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  double startTime = 0;
    if (1 == 1){
      startTime = System.currentTimeMillis();
   
      // if (System.currentTimeMillis()- startTime < 1) {
      //   operatorController.setRumble(RumbleType.kLeftRumble, 1);
      //   operatorController.setRumble(RumbleType.kRightRumble, 1);
      // }else {
      //   operatorController.setRumble(RumbleType.kLeftRumble, 0);
      //   operatorController.setRumble(RumbleType.kRightRumble, 0);
      // }
    }




    
    if (operatorController.getRightTriggerAxis()>.1){
      if(climb2.getValue()>100){
        m_robotContainer.m_climber.climber1.setVoltage(0);
      }
      else{
      m_robotContainer.m_climber.climber1.setVoltage(13);
      }
    }
    else if (operatorController.getRightBumper()==true){
       m_robotContainer.m_climber.climber1.setVoltage(-13);
    }
    else{
       m_robotContainer.m_climber.climber1.setVoltage(0);
    }

    if (operatorController.getLeftTriggerAxis()>.1){
      if(climb1.getValue()>100){
        m_robotContainer.m_climber.climber2.setVoltage(0);
      }
      else{
      m_robotContainer.m_climber.climber2.setVoltage(-13);
      }
    }
    else if (operatorController.getLeftBumper()==true){
       m_robotContainer.m_climber.climber2.setVoltage(13);
    }
    else{
      
       m_robotContainer.m_climber.climber2.setVoltage(0);
    }

  
  if (driverController.getLeftTriggerAxis()>.1 && m_robotContainer.m_shooteMoving.stop==false){
    m_robotContainer.m_piviot.piviotsetpoint=m_robotContainer.m_shooteMoving.gotoangle;
    //m_robotContainer.m_shooter.shooterspeed=m_robotContainer.m_shooteMoving.shootervoltagerequired;
  }
  if (operatorController.getLeftTriggerAxis()>.1 && m_robotContainer.m_shooteMoving.stop==true){
    m_robotContainer.m_piviot.piviotsetpoint=m_robotContainer.m_piviot.piviotencoder.getPosition();

  }
  
  

}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    m_robotContainer.setMotorBrake(true);
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    // Update drivetrain simulation
    m_robotContainer.driveSimulationPeriodic();

    //Update camera simulation
   // m_robotContainer.updateVisionSimulationPeriod();
  }
}