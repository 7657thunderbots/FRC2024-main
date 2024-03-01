package frc.robot.subsystems.Intake;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class IntakeSubsystem extends SubsystemBase {

    
    private final CANSparkMax centerMotor;



    public IntakeSubsystem() {
        
        centerMotor = new CANSparkMax(15 , CANSparkLowLevel.MotorType.kBrushless);
        centerMotor.restoreFactoryDefaults();
        centerMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        centerMotor.setInverted(false);
    }

    public void intake(){
        centerMotor.set(-1);
    }

    public void stop() {
        centerMotor.set(0);
    }

    public Command stopIntaking(){
        return runOnce(() -> {
           this.stop();
        });
    }

    public Command startIntaking(){
        return runOnce(() -> {
           this.intake();
        });
    }
    

    @Override
    public void periodic()
    {
        //System.out.println("This the position of the intake: " + getIntakePistonPosition());
        //System.out.println("This is the power of the intake: " + getSpeed());

    }
}