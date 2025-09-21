public class Body {
    public double m;
    public double[] r;
    public double[] v;
    public double[] a = new double[]{0, 0, 0};
    private static final double G = 4*Math.pow(Math.PI, 2);
        
    public Body(double m, double[] r){
        this.m=m;
        this.r=r;
    }
    
    public double magR(){
        return Math.sqrt(r[0]*r[0]+r[1]*r[1]+r[2]*r[2]);
    }
    public double magV(){
        return Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]);
    }
    public double magA(){
        return Math.sqrt(a[0]*a[0]+a[1]*a[1]+a[2]*a[2]);
    }
    public double[] cm(Body[] bodies){
        double[] cm = new double[] {0, 0, 0,};
        double totalMass=0;
        for (Body p : bodies){
            for (int i=0; i<3; i++){
                cm[i]+=p.m*p.r[i];
            }
            totalMass+=p.m;
        }
        for (int i=0; i<3; i++){
            cm[i] /= totalMass;
        }
        return cm;
    }
    public double v0(Body p){
        
        return Math.sqrt(G*p.m/(Math.sqrt(Math.pow(this.r[0]-p.r[0], 2)+Math.pow(this.r[1]-p.r[1], 2)+Math.pow(this.r[2]-p.r[2], 2))));
    }
    public void a(Body[] bodies){
        for (int i=0; i<3; i++){
            double aComponent = 0;
            for (Body p : bodies){
                if (p == this) continue;
                aComponent-= (G*p.m*(this.r[i]-p.r[i]))/Math.pow(Math.sqrt(Math.pow(this.r[0]-p.r[0], 2)+Math.pow(this.r[1]-p.r[1], 2)+Math.pow(this.r[2]-p.r[2], 2)), 3);
                
            }
            a[i]=aComponent;
        }
    }
    static double E(Body[] bodies){
        double E = 0;
        for (int i=0; i<bodies.length; i++){
        Body p1 = bodies[i];
            E+=0.5*p1.m*(p1.v[0]*p1.v[0]+p1.v[1]*p1.v[1]+p1.v[2]*p1.v[2]);
            for (int j=0; j<i; j++){
                Body p2 = bodies[j];
                //if (p2 == p1) continue;
                E-=1*G*p1.m*p2.m/Math.sqrt(Math.pow(p1.r[0]-p2.r[0], 2)+Math.pow(p1.r[1]-p2.r[1], 2)+Math.pow(p1.r[2]-p2.r[2], 2));
            }
        }
        return E;
    }
}
