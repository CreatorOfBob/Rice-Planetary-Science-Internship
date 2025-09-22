sim = rebound.Simulation()
sim.units = ('AU', 'yr', 'Msun')
sim.integrator = "ias15"

sim.add(m=1, r=0.00465047, x=1/2, y=-math.sqrt(3)/6, z=0, vx=math.pi*3**(1/4), vy=math.pi*3**(3/4), vz=0)
sim.add(m=1, r=0.00465047, x=0, y=math.sqrt(3)/3, z=0, vx=-2*math.pi*3**(1/4), vy=0, vz=0)
sim.add(m=1, r=0.00465047, x=-1/2, y=-math.sqrt(3)/6, z=0, vx=-math.pi*3**(1/4), vy=-math.pi*3**(3/4), vz=0)

E0 = sim.energy()

N = 10000
times = np.zeros(N)
errors = np.zeros(N)

for i in range(N):
    sim.integrate(sim.t + 0.0002)
    times[i] = sim.t
    errors[i] = (sim.energy() - E0) / E0

plt.plot(times, errors)
plt.xlabel("Time [yr]")
plt.ylabel("Relative Energy Error")
plt.title("Energy Conservation Over Time")
plt.grid()
plt.show()
