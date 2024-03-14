package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooterSubsystem extends SubsystemBase {
    private final TalonFX leftLeader = new TalonFX(20);
  private final TalonFX leftFollower = new TalonFX(22);//top
  public boolean amping;
   public boolean speakering;
   public boolean spinup;
   public boolean sauto;

  public shooterSubsystem(){
    amping=false;
    speakering=false;
    spinup=false;
   
  }
  public void shoot(){
    leftLeader.setVoltage(6);
    leftFollower.setVoltage(-6);
    }
    public void amp(){
        leftLeader.set(.5);
        leftFollower.set(.5);
    }

    public void stop() {
        leftFollower.set(0);
        leftLeader.set(0);
    }

    public Command astartSpeakerCommand(){
        return runOnce(()->{
        spinup=true;
        });
    }

    public Command astopShooterCommand(){
        return runOnce(()->{
            spinup=false;
        });
    }

    public Command stopShooterCommand(){
        return runOnce(() -> {
           this.stop();
           this.speakering = false;
           this.amping = false;
        });
    }

    public Command startSpeakerCommand(){
        return runOnce(() -> {
        if (!amping)  {
           this.speakering = true;
            this.shoot();
         
        }
        });
    }

    public Command startAmpCommand(){
        return runOnce(()->{
           if(!speakering){ 
           this.amping = true;
            this.amp();
           
        }
        });
    }
    

    @Override
    public void periodic()
    {
        if (spinup==true&&sauto==true){
            this.shoot();
        }
        else if (spinup==false&&sauto==true){
            this.stop();
        }
        //System.out.println("This the position of the intake: " + getIntakePistonPosition());
        //System.out.println("This is the power of the intake: " + getSpeed());

    }
    
}
