package frc.robot.subsystems.proximity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;


public class proximitysubsystem extends SubsystemBase{

  private Rev2mDistanceSensor distOnboard; 
  public Trigger piecein;
  public Boolean pieceInBoolean=false;


 public proximitysubsystem(){
    /**
     * Rev 2m distance sensor can be initialized with the Onboard I2C port
     * or the MXP port. Both can run simultaneously.
     */
    //private distOnboard = new Rev2mDistanceSensor();
    //distMXP = new Rev2mDistanceSensor(Port.kMXP);
    distOnboard = new Rev2mDistanceSensor(Port.kOnboard);
    distOnboard.setAutomaticMode(true);
    piecein = new Trigger(() -> (this.pieceInBoolean));
  }

 
  
    /**
     * Before measurements can be read from the sensor, setAutomaticMode(true)
     * must be called. This starts a background thread which will periodically
     * poll all enabled sensors and store their measured range.
     */
    

    @Override
    public void periodic(){
        //System.out.println("This the position of the intake: " + getIntakePistonPosition());
        //System.out.println("This is the power of the intake: " + getSpeed());

    
    /**
     * Range returned from the distance sensor is valid if isRangeValid()
     * returns true.
     */
    if(distOnboard.isRangeValid()) {
      SmartDashboard.putNumber("Range Onboard", distOnboard.getRange());
      SmartDashboard.putNumber("Timestamp Onboard", distOnboard.getTimestamp());
    }
        boolean isValid = distOnboard.isRangeValid();

    if (distOnboard.getRange() < 3){
      pieceInBoolean = true;
    }
    else if(isValid==false){
      pieceInBoolean = false;
  }
    else {
      pieceInBoolean = false;
    }

    distOnboard.setAutomaticMode(true);
  

 
    /**
     * The background thread may be stopped by calling setAutomaticMode(false).
     * This will command any active sensors to terminate current measurements
     * and the thread will stop.
     */
    //distOnboard.setAutomaticMode(false);
    }

    
}
