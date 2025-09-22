# Set up the simulation
sim = rebound.Simulation()
sim.units = ('AU', 'yr', 'Msun')
sim.integrator = "ias15"

# Add particles
#sim.add(m=1, r=0.00465047, x=0, y=0, z=0, vx=0, vy=0, vz=0)
sim.add(m=1, r=0.00465047, x=1/2, y=1/2, z=0, vx=-math.pi*2**(3/4), vy=math.pi*2**(3/4), vz=0)
sim.add(m=1, r=0.00465047, x=-1/2, y=1/2, z=0, vx=-math.pi*2**(3/4), vy=-math.pi*2**(3/4), vz=0)
sim.add(m=1, r=0.00465047, x=-1/2, y=-1/2, z=0, vx=math.pi*2**(3/4), vy=-math.pi*2**(3/4), vz=0)
sim.add(m=1, r=0.00465047, x=1/2, y=-1/2, z=0, vx=math.pi*2**(3/4), vy=math.pi*2**(3/4), vz=0)

E0 = sim.energy()

# Simulation parameters
years=1500
dt = 0.01
N = int(years/dt)

times = np.zeros(N)
errors = np.zeros(N)

# Position arrays
x = np.zeros((4, N))
y = np.zeros((4, N))

# Integrate and record data
for i in range(N):
    sim.integrate(sim.t + dt)
    times[i] = sim.t
    errors[i] = (sim.energy() - E0) / E0
    for j in range(4):  # for each particle
        x[j, i] = sim.particles[j].x
        y[j, i] = sim.particles[j].y

# Plot energy error
plt.figure()
plt.plot(times, errors)
plt.xlabel("Time [yr]")
plt.ylabel("Relative Energy Error")
plt.title("Energy Conservation Over Time")
plt.grid()

# Plot trajectories
plt.figure()
for j in range(4):
    plt.plot(x[j], y[j], label=f"Star {j+1}")
plt.xlabel("x [AU]")
plt.ylabel("y [AU]")
plt.title("Star Trajectories")
plt.legend()
plt.axis('equal')
plt.grid()
plt.show()
