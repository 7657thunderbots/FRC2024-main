package frc.robot.subsystems.proximity;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;



public class proximitysubsystem extends SubsystemBase{

  public Trigger piecein;
  public Boolean pieceInBoolean=false;
  DigitalInput toplimitSwitch = new DigitalInput(0);


 public proximitysubsystem(){
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


      if (toplimitSwitch.get()) {
        // We are going up and top limit is tripped so stop
       pieceInBoolean = true;
      } else {
        // We are going up but top limit is not tripped so go at commanded speed
        pieceInBoolean = false;
      }


    }


}