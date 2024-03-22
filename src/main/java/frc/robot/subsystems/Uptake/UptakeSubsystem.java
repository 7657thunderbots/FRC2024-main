package frc.robot.subsystems.Uptake;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class UptakeSubsystem extends SubsystemBase {
    private final CANSparkMax uptake;

    public UptakeSubsystem(){
        uptake = new CANSparkMax(17 , CANSparkLowLevel.MotorType.kBrushless);
        uptake.restoreFactoryDefaults();
        uptake.setIdleMode(CANSparkMax.IdleMode.kCoast);
        uptake.setInverted(false);

    }
    public void uptake(){
        uptake.set(-.4);
    }
    public void uptakeshoot(){
        uptake.set(-1);
    }

    public void stop() {
        uptake.set(0);
    }

    public Command stopUptaking(){
        return runOnce(() -> {
           this.stop();
        });
    }

    public Command startUptaking(){
        return runOnce(() -> {
           this.uptake();
        });
    }

    public Command startshoot(){
        return runOnce(() -> {
           this.uptakeshoot();
        });
    }
   


    @Override
    public void periodic()
    {
        //System.out.println("This the position of the intake: " + getIntakePistonPosition());
        //System.out.println("This is the power of the intake: " + getSpeed());

    }
}

    

