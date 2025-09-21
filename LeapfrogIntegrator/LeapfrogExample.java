import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Leapfrog{
    public static void main(String[] args){
        int num=3;
        Body[] bodies = new Body[num];
        
        Body sun = new Body(1, new double[]{0,0,0});
        Body earth = new Body(3.003e-6, new double[]{1,0,0});
        Body moon = new Body(3.694e-8, new double[]{1.002572, 0, 0});
        
        bodies[0]=sun;
        bodies[1]=earth;
        bodies[2]=moon;
        
        sun.v = new double[]{0, 0, 0};
        earth.v = new double[]{0, earth.v0(sun), 0};
        moon.v = new double[]{0, moon.v0(earth)+earth.v0(sun), 0};
        
        for (Body p : bodies){
            p.a(bodies);
        }
        
        double years = 1;
        double dt = 0.000001;
        int steps = (int)(years/dt);        
        
        double E0 = Body.E(bodies);
        
        try {
            PrintWriter writer1 = new PrintWriter(new FileWriter("EarthX.txt"));
            PrintWriter writer2 = new PrintWriter(new FileWriter("deltaE.txt"));        
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
                    writer1.println(step*dt+", "+earth.r[0]+", "+earth.r[1]+", "+moon.r[0]+", "+moon.r[1]);
                    writer2.println(step*dt+", "+(Body.E(bodies)-E0)/E0);
                }
            }
            writer1.close();
            writer2.close();
            System.out.println("Total Energy Error: "+((Body.E(bodies)-E0)/E0));
            System.out.println("Energy error written");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
