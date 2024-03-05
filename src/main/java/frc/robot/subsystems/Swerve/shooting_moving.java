package frc.robot.subsystems.Swerve;

import java.util.Optional;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooting_moving extends SubsystemBase {
    public final SwerveSubsystem m_drivebase = SwerveSubsystem.getInstance();
    double x_distsncetosp;
    double y_distsncetosp;
    public shooting_moving(){ 
    }

     public Command start_aimbot_Command(){
            return run(() -> {
             Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
    Pose2d Speaker = new Pose2d(Units.Meters.of(14), Units.Meters.of(5.77), Rotation2d.fromDegrees(330.0));
    }
    if (ally.get() == Alliance.Blue) {
     Pose2d Speaker = new Pose2d(Units.Meters.of(3), Units.Meters.of(5.77), Rotation2d.fromDegrees(130.0));
    }
}
else {
//no color
}

             });
        }
}
