package frc.robot.subsystems.Piviot;

import java.security.PrivilegedActionException;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkRelativeEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class piviotSubsystem extends SubsystemBase {
    private final CANSparkMax piviot;
    private AbsoluteEncoder piviotencoder;
    private static final SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;
    private static final int kCPR = 3600;
    private double piviotsetpoint;
    private boolean manualmove;
    private boolean setpoints;
    int timer;
    public double hkP = 0.05;
    private final double hkI = 0.;
   private final double hkD = 0.00;
    private final double hiLimit = 0;
    private double EerrorSum = 0;
   private double ElastError=0;
    public CANSparkMax hand;
    public RelativeEncoder hande;
    private double lastTimestamp = 0;
    public final Timer wait = new Timer();
    public piviotSubsystem(){
        piviot = new CANSparkMax(16 , CANSparkLowLevel.MotorType.kBrushless);
        piviot.setIdleMode(CANSparkMax.IdleMode.kCoast);
        piviot.setInverted(false);
        piviotencoder = piviot.getAbsoluteEncoder();
        piviotsetpoint = piviotencoder.getPosition();
        manualmove = false;
        setpoints=false;

    }

    // public void stop() {
    //     piviot.set(0);
    // }

    // public Command stopPiviot(){
    //     return runOnce(() -> {
    //        this.stop();
    //     });
    // }

    public Command piviotAmp(){
        return runOnce(() -> {
           this.piviotsetpoint = 4;
        });
    }
     
    public Command piviotspeakerclose(){
        return runOnce(() -> {
           this.piviotsetpoint = 40.5;
        });
    }   
    //  public Command piviotspeakerfar(){
    //     return runOnce(() -> {
    //        this.piviotsetpoint = 600;
    //     });
    // }

     public Command understage(){
        return runOnce(() -> {
           this.piviotsetpoint = 94.0;
        });
    }

    // public Command manualmove(){

    // }

   

    @Override
    public void periodic()
    {
        piviotencoder = piviot.getAbsoluteEncoder();
        //if(setpoints==true){
        double Eerror = piviotsetpoint-piviotencoder.getPosition();
    double dt = Timer.getFPGATimestamp() - lastTimestamp;

    if (Math.abs(Eerror) < hiLimit) {
      EerrorSum += Eerror * dt;
    }

    double EerrorRate = (Eerror - ElastError) / dt;

    double houtput = hkP * Eerror + hkI * EerrorSum + hkD * EerrorRate;
    if (Math.abs(piviotsetpoint-piviotencoder.getPosition())<2){
        houtput=0;
    }
    if (piviotencoder.getPosition()<7 && houtput<0){
         houtput=0;
     }
    else if(piviotencoder.getPosition()>1000 && houtput>0){
        houtput=0;
     }
    piviot.set(-houtput);

    // update last- variables
     lastTimestamp = Timer.getFPGATimestamp();
    ElastError = Eerror;// }
    
       //piviot.set(Constants.operatorController.getLeftY());
       SmartDashboard.putNumber("Hand",piviotencoder.getPosition());


}
}

    

