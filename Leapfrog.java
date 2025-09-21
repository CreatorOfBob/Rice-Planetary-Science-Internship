import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Leapfrog{
    public static void main(String[] args){
        int num=3;
        Body[] bodies = new Body[num];
        
        Body star1 = new Body(1, new double[]{0.5, 0, 0});
        Body star2 = new Body(1, new double[]{0, 0.5*Math.sqrt(3), 0});
        Body star3 = new Body(1, new double[]{-0.5, 0, 0});
        
        bodies[0]=star1;
        bodies[1]=star2;
        bodies[2]=star3;
        
        star1.v = new double[]{0.5*Math.sqrt(3*Math.sqrt(3)+4*Math.PI*Math.PI), 0.5*Math.sqrt(3)*Math.sqrt(3*Math.sqrt(3)+4*Math.PI*Math.PI), 0};
        star2.v = new double[]{-Math.sqrt(3*Math.sqrt(3)+4*Math.PI*Math.PI), 0, 0};
        star3.v = new double[]{-0.5*Math.sqrt(3*Math.sqrt(3)+4*Math.PI*Math.PI), -0.5*Math.sqrt(3)*Math.sqrt(3*Math.sqrt(3)+4*Math.PI*Math.PI), 0};
        
        for (Body p : bodies){
            p.a(bodies);
        }
        
        double years = 0.01;
        double dt = 0.00000001;
        int steps = (int)(years/dt);        
        
        double E0 = Body.E(bodies);
        
        try {
            PrintWriter writer1 = new PrintWriter(new FileWriter("deltaE.txt"));
            PrintWriter writer2 = new PrintWriter(new FileWriter("star1.txt"));
            PrintWriter writer3 = new PrintWriter(new FileWriter("star2.txt"));
            PrintWriter writer4 = new PrintWriter(new FileWriter("star3.txt"));
            for (int step = 0; step < steps; step++) {
                for (Body p : bodies) {
                    for (int i = 0; i < 3; i++) {
                        p.v[i] += 0.5 * dt * p.a[i];
                    }
                }
            
                for (Body p : bodies) {
                    for (int i = 0; i < 3; i++) {
                        p.r[i] += dt * p.v[i];
                    }
                }

                for (Body p : bodies) {
                    p.a(bodies);
                }

                for (Body p : bodies) {
                    for (int i = 0; i < 3; i++) {
                	    p.v[i] += 0.5 * dt * p.a[i];
                    }        
                }
                if (step%100==0){
                    writer1.println(step*dt+", "+(Body.E(bodies)-E0)/E0);
                    writer2.println(step*dt+", "+star1.r[0]+", "+star1.r[1]);
                    writer3.println(step*dt+", "+star2.r[0]+", "+star2.r[1]);
                    writer4.println(step*dt+", "+star3.r[0]+", "+star3.r[1]);
                }
            }
            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();
            System.out.println("Total Energy Error: "+((Body.E(bodies)-E0)/E0));
            System.out.println("Energy error written");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
