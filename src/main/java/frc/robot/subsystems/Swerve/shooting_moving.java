package frc.robot.subsystems.Swerve;

import java.util.Optional;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooting_moving extends SubsystemBase {
    public final SwerveSubsystem m_drivebase = SwerveSubsystem.getInstance();
   public double rotationalspeedinradian;
    double x_distsncetosp;
    double y_distsncetosp;
    double lasttimestamp;
    double angle_to_speaker;
    double distance_last;
    
    public boolean stop;
    double radians_needed;
    Pose2d Speaker;
    public double gotoangle;
    public shooting_moving(){ 
    }

//      public Command start_aimbot_Command(){
//             return run(() -> {
//              Optional<Alliance> ally = DriverStation.getAlliance();
// if (ally.isPresent()) {
//     if (ally.get() == Alliance.Red) {
//      Speaker = new Pose2d(Units.Meters.of(16.5), Units.Meters.of(5.77), Rotation2d.fromDegrees(330.0));
//     }
//     if (ally.get() == Alliance.Blue) {
//      Speaker = new Pose2d(Units.Meters.of(0), Units.Meters.of(5.77), Rotation2d.fromDegrees(130.0));
//     }
// }
// else {
// //no color
// }
// y_distsncetosp = SwerveSubsystem.getInstance().getypov();
// x_distsncetosp = SwerveSubsystem.getInstance().getxpov();
// double SpeakererX = Speaker.getX();
// double Speakery = Speaker.getY();
// double ysub = y_distsncetosp-Speakery;
// double yabs =Math.abs(ysub);
// double ysquared = yabs * yabs;
// double xsub = x_distsncetosp-SpeakererX;
// double xabs =Math.abs(xsub);
// double xsquared = xabs * xabs;
// double distance_squared = xsquared+ ysquared;
// double distance = Math.sqrt(distance_squared);



//              });
//         }
        @Override
        public void periodic()
        {
            double current_time=Timer.getFPGATimestamp();
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
double timedone= current_time-lasttimestamp;
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
double hieght=1.9; //height of speaker ***IMPORTANT ONLY CHANGE THIS THINGGY1.9
double hypotofshooter = Math.sqrt(((hieght*hieght)+(distance*distance)));
double angle= Math.atan(hieght/distance);
double xvelocity = Math.cos(angle)*9.23562083*2*.75;
double distancecurrent = distance;
double distancechange = distancecurrent-distance_last;
double velocitychangex=(distancechange/timedone);
double totoalvelocityx= xvelocity;
double time = distance/xvelocity;
double yvelocitygravity = 9.8*time;
double yvelocity = Math.sin(angle)*9.23562083*2*.75;
double yaftergravity= yvelocity-yvelocitygravity;
double x= (9.23562083*2*.75*9.23562083*2*.75)-(yaftergravity*yaftergravity);
double xsq= Math.sqrt(x);
double anglefinal= Math.acos(totoalvelocityx/(9.23562083*2*.75));
//double anglefinal = Math.acos((xsq/(9.23562083*2*.75)));
double angledeg= anglefinal;
gotoangle = 90-angledeg;
double angle_to_0 = 90-Math.toDegrees(Math.acos(ysub/distance));
 angle_to_speaker = angle_to_0;
 if (y_distsncetosp<5.552967){
 radians_needed = 360-(m_drivebase.getangle()- angle_to_speaker);
 rotationalspeedinradian =radians_needed*.0075;
 }
 else{
  radians_needed = (m_drivebase.getangle()- angle_to_speaker);
  rotationalspeedinradian =radians_needed*.015;  
 }
double math_sign = Math.abs(radians_needed)+radians_needed;
if(Math.abs(radians_needed)<5){
    rotationalspeedinradian=0;   
}
// else if (math_sign==0){

//     rotationalspeedinradian=-.7;
// }
else{
    //rotationalspeedinradian =radians_needed*.015;
    if(Math.abs(rotationalspeedinradian)<.006){
        rotationalspeedinradian=.006*math_sign;
    }
}

 if (gotoangle<5){
    stop=true;
 }
 else if (gotoangle>90){
    stop=true;
 }
 else{
    stop=false;
 }

        SmartDashboard.putNumber("distance from speaker", distance);
        SmartDashboard.putNumber("anglegoto",gotoangle);
        SmartDashboard.putNumber("angle to 0",angle_to_speaker);
        SmartDashboard.putNumber("x_distsncetosp", y_distsncetosp);
    distance_last=distancecurrent;
    lasttimestamp = current_time;
        }
}