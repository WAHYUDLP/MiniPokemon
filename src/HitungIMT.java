import java.util.Scanner;
/* @author azahr*/
public class HitungIMT {
    public static void main(String[] args) {
    Scanner in = new Scanner (System.in);
        
        System.out.print("Berat badan (kg) : ");
        int b  = in.nextInt();
        System.out.print("Tinggi badan (m) : ");
        double t= in.nextDouble();
        double imt  = b/t*t;
        if(imt<=18.5){
        System.out.println("IMT = "+imt+"\t\t\t"+"Termasuk kurus");
        }else if(18.5 < imt && imt <= 25){
        System.out.println("IMT = "+imt+"\t\t\t"+"Termasuk normal");
        }else if(25 < imt && imt <= 30){
        System.out.println("IMT = "+imt+"\t\t\t"+"Termasuk gemuk");
        }else if(imt > 30){
        System.out.println("IMT = "+imt+"\t\t\t"+"Termasuk kegemukan");
    }}}
