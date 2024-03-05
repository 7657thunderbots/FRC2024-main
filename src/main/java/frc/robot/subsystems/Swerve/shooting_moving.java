package frc.robot.subsystems.Swerve;

import java.util.Optional;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooting_moving extends SubsystemBase {
    public final SwerveSubsystem m_drivebase = SwerveSubsystem.getInstance();
    double x_distsncetosp;
    double y_distsncetosp;
    Pose2d Speaker;
    public shooting_moving(){ 
    }

     public Command start_aimbot_Command(){
            return run(() -> {
             Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
     Speaker = new Pose2d(Units.Meters.of(16.5), Units.Meters.of(5.77), Rotation2d.fromDegrees(330.0));
    }
    if (ally.get() == Alliance.Blue) {
     Speaker = new Pose2d(Units.Meters.of(0), Units.Meters.of(5.77), Rotation2d.fromDegrees(130.0));
    }
}
else {
//no color
}
y_distsncetosp = SwerveSubsystem.getInstance().getypov();
x_distsncetosp = SwerveSubsystem.getInstance().getxpov();
double SpeakererX = Speaker.getX();
double Speakery = Speaker.getY();
double ysub = y_distsncetosp-Speakery;
double yabs =Math.abs(ysub);
double ysquared = yabs * yabs;
double xsub = x_distsncetosp-SpeakererX;
double xabs =Math.abs(xsub);
double xsquared = xabs * xabs;
double distance_squared = xsquared+ ysquared;
double distance = Math.sqrt(distance_squared);


             });
        }
        @Override
        public void periodic()
        {
            Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
     Speaker = new Pose2d(Units.Meters.of(16.5), Units.Meters.of(5.77), Rotation2d.fromDegrees(330.0));
    }
    if (ally.get() == Alliance.Blue) {
     Speaker = new Pose2d(Units.Meters.of(0), Units.Meters.of(5.77), Rotation2d.fromDegrees(130.0));
    }
}
            y_distsncetosp = SwerveSubsystem.getInstance().getypov();
x_distsncetosp = SwerveSubsystem.getInstance().getxpov();
double SpeakererX = Speaker.getX();
double Speakery = Speaker.getY();
double ysub = y_distsncetosp-Speakery;
double yabs =Math.abs(ysub);
double ysquared = yabs * yabs;
double xsub = x_distsncetosp-SpeakererX;
double xabs =Math.abs(xsub);
double xsquared = xabs * xabs;
double distance_squared = xsquared+ ysquared;
double distance = Math.sqrt(distance_squared);
        SmartDashboard.putNumber("distance from speaker", distance);
        }
}
