package frc.robot.subsystems.Intake;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.subsystems.Uptake.UptakeSubsystem;
import frc.robot.subsystems.proximity.proximitysubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class IntakeSubsystem extends SubsystemBase {

    boolean pieceInBoolean;
    private final CANSparkMax centerMotor;
    private final UptakeSubsystem m_uptake = new UptakeSubsystem();
    public final proximitysubsystem m_proximity = new proximitysubsystem();
    public final Timer wait = new Timer();
    boolean stop;
    boolean init; 
    double starttime;
    double timedone;
    boolean started;
    boolean time;
    public boolean auto;
    public boolean uptakeshoot=false;
     double light=0;
   public boolean intake;
   Spark spark = new Spark(0);
    AnalogInput piece = new AnalogInput(1);




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
        intake=false;
        
    }


    public void intake(){
        centerMotor.set(-.75);
    }

    public void stop() {
        centerMotor.set(0);
    }

    public Command stopIntaking(){
        return runOnce(() -> {
           if (auto==false){this.stop();
           this.m_uptake.stop();}
        });
    }
    public Command uptakeshoot(){
        return run(()->{
            this.m_uptake.uptakeshoot();
           this. intake();
            uptakeshoot=true;
        });
    }
    public Command disableUptake() {
        return runOnce(() -> {
          if (auto==false) {this. uptakeshoot=false;
          this.m_uptake.stop();
          this.stop();}
        });
    }
    public Command astartIntaking(){
        return runOnce(() ->{
            intake=true;

        });
    }
    public Command astopIntaking(){
        return runOnce ( () ->{
            intake=false;
         });
        }
    public Command auptakeshoot(){
        return runOnce(()->{
            uptakeshoot=true;
            //this.m_uptake.uptake();
        });
    }
    public Command auptakestopshoot(){
        return runOnce(()->{
            uptakeshoot=false;
        });
    }
    
    public Command startIntaking(){
       
        return run(() -> {
           if (uptakeshoot==true){
          
           }
           else if (m_proximity.pieceInBoolean==true &&this.stop==false){
            this.stop=true;
            this.starttime= wait.getFPGATimestamp();
           }
           else if (m_proximity.pieceInBoolean==true&&stop==true){
            this.stop();
             this.m_uptake.stop();

           }
           else if (m_proximity.pieceInBoolean==false&&stop==false){
            this.intake();
            this.m_uptake.uptake(); 
           }
           if (stop==true){
            if(wait.getFPGATimestamp()>(starttime+3)){
              this.stop =false;
           }
           }

//             else if (stop == true){
//             if(wait.getFPGATimestamp()>(starttime+5)){
//               this.stop =false;
//             }
//           }
//            if (m_proximity.pieceInBoolean==false && stop ==false ){
//             this.intake();
//            this.m_uptake.uptake(); 
//            this.started=false;
// ;
//            }
//            else
//            {
//             this.init =false;
//             this.stop();
//             this.m_uptake.stop();
//             if (started==false){
//                this.started = true;
//                this.starttime=wait.getFPGATimestamp();
//                this.stop = true;
//              }
//            }
        });
    }
    

    @Override
    public void periodic()
    {
        if (piece.getValue()<100) {
            // We are going up and top limit is tripped so stop
            pieceInBoolean = true;
             light =Timer.getFPGATimestamp();
          } 
          else {
            // We are going up but top limit is not tripped so go at commanded speed
            if (light+2<(Timer.getFPGATimestamp())){
                pieceInBoolean = false;
            }
            
          }
        if (m_proximity.pieceInBoolean==true){
            spark.set(.35);
        }
        else if (pieceInBoolean==true){
          spark.set(.35);  
        }
        else{
            spark.set(.75);
        }
        if (auto==true){
       
            if (uptakeshoot==true){
                this.m_uptake.uptake();
            }
            else if (stop == true){
                if(wait.getFPGATimestamp()>(starttime+2)){
                    this.stop =false;
                }
            }
            else if (m_proximity.pieceInBoolean==false && stop ==false ){  //* */
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
           }}
           else if (intake==false && uptakeshoot==false){
            this.stop();
            this.m_uptake.stop();
           }
           else if (uptakeshoot==true){
            this.m_uptake.uptake();
           }
           

        }
        //System.out.println("This the position of the intake: " + getIntakePistonPosition());
        //System.out.println("This is the power of the intake: " + getSpeed());

    }
