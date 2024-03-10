package frc.robot.subsystems.Intake;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.subsystems.Uptake.UptakeSubsystem;
import frc.robot.subsystems.proximity.proximitysubsystem;
import edu.wpi.first.wpilibj.Timer;

public class IntakeSubsystem extends SubsystemBase {

    
    private final CANSparkMax centerMotor;
    private final UptakeSubsystem m_uptake = new UptakeSubsystem();
    private final proximitysubsystem m_proximity = new proximitysubsystem();
    public final Timer wait = new Timer();
    boolean stop;
    boolean init;
    double starttime;
    double timedone;
    boolean started;
    boolean uptakeshoot;




    public IntakeSubsystem() {
        
        centerMotor = new CANSparkMax(15 , CANSparkLowLevel.MotorType.kBrushless);
        centerMotor.restoreFactoryDefaults();
        centerMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        centerMotor.setInverted(false);
        wait.reset();
        init =true;
        stop=false;
        started= false;
        wait.reset();
        wait.start();
    }

    public void intake(){
        centerMotor.set(-.75);
    }

    public void stop() {
        centerMotor.set(0);
    }

    public Command stopIntaking(){
        return runOnce(() -> {
           this.stop();
           this.m_uptake.stop();
        });
    }
    public Command uptakeshoot(){
        return runOnce(()->{
            m_uptake.uptake();
            uptakeshoot=true;
        });
    }
    public Command disableUptake() {
        return runOnce(() -> {
            uptakeshoot=false;
          this.m_uptake.stop();
        });
    }
    public Command startIntaking(){
       
        return run(() -> {
          if (uptakeshoot==true){

          }
            else if (stop == true){
            if(wait.getFPGATimestamp()>(starttime+2)){
              this.stop =false;
            }
          }
           if (m_proximity.pieceInBoolean==false && stop ==false ){
            this.intake();
           this.m_uptake.uptake(); 
           this.started=false;
;
           }
           else
           {
            this.init =false;
            this.stop();
            this.m_uptake.stop();
            if (started==false){
               started = true;
               starttime=wait.getFPGATimestamp();
               stop = true;
            }
           }
        });
    }
    

    @Override
    public void periodic()
    {
        //System.out.println("This the position of the intake: " + getIntakePistonPosition());
        //System.out.println("This is the power of the intake: " + getSpeed());

    }
}