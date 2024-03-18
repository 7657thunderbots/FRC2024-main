package frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;


public class climberSubsystem extends SubsystemBase {
 
        public final CANSparkMax climber1;
        public final CANSparkMax climber2;

        public climberSubsystem(){
        climber1 = new CANSparkMax(23 , CANSparkLowLevel.MotorType.kBrushless);
        climber1.restoreFactoryDefaults();
        climber1.setIdleMode(CANSparkMax.IdleMode.kBrake);
         climber2 = new CANSparkMax(24 , CANSparkLowLevel.MotorType.kBrushless);
        climber2.restoreFactoryDefaults();
        climber2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    
        }
        public void up1(){
            climber1.setVoltage(8);
        }
        public void stop1(){
            climber1.setVoltage(0);
        }
        public void down1(){
            climber1.setVoltage(-8);
        }

         public void up2(){
            climber2.setVoltage(8);
        }
        public void stop2(){
            climber2.setVoltage(0);
        }
        public void down2(){
            climber2.setVoltage(-8);
        }

        
        public Command stop1Command(){
            return runOnce(() -> {
               this.stop1();
            });
        }

         public Command stop2Command(){
            return runOnce(() -> {
               this.stop2();
            });
        }

         public Command up1Command(){
            return runOnce(() -> {
               this.up1();
            });
        }

         public Command down1Command(){
            return runOnce(() -> {
               this.down1();
            });
        }

        public Command up2Command(){
            return runOnce(() -> {
               this.up2();
            });
        }

         public Command down2Command(){
            return runOnce(() -> {
               this.down2();
            });
        }















}
