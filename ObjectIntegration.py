!pip install rebound
import rebound
import math
import random as rand
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

sim = rebound.Simulation()
sim.units = ('AU', 'yr', 'Msun')
sim.integrator = "ias15"

sim.add(m=0.78, r=1.5, hash="Aa")
sim.add(m=0.68, a=34, e=0.28, inc=math.radians(143), Omega=math.radians(277), omega=math.radians(91), hash="Ab")
sim.add(m=0.16, a=1400)

simB = rebound.Simulation()
simB.units = ('AU', 'yr', 'Msun')
simB.integrator = "ias15"
simB.add(m=0.12, r=1.45, hash="Ba")
simB.add(m=0.04, r=0.497, a=207, hash="Bb")
simB.move_to_com()

for p in simB.particles:
    sim.add(p + sim.particles[2])
sim.remove(2)
del simB

num_test_particles = 100
for i in range(num_test_particles):
    sim.add(
        a=rand.uniform(180, 260),
        e=rand.uniform(0, 0.01),
        inc=math.radians(rand.uniform(35, 40)),
        Omega=rand.uniform(0, 2*np.pi),
        omega=rand.uniform(0, 2*np.pi),
        f=rand.uniform(0, 2*np.pi),
        m=0,
        primary=sim.particles[0]
    )
E0 = sim.energy()
sim.move_to_com()

num_particles = len(sim.particles)

years = 50000
dt = 5
N = int(years / dt)

times = np.zeros(N)
errors = np.zeros(N)
x = np.zeros((num_particles, 2))
y = np.zeros((num_particles, 2))
numRemainings = np.zeros(N)
numEjected = np.zeros(N)
numIncinerated = np.zeros(N)

stellar_radii = np.array([p.r for p in sim.particles[:4]])
R_max = 2000
for j, p in enumerate(sim.particles):
    x[j, 0] = p.x
    y[j, 0] = p.y

# Integration loop
for i in range(N):
    sim.integrate(sim.t + dt)
    times[i] = sim.t
    errors[i] = (sim.energy() - E0) / E0

    j = 4
    while j < len(sim.particles):
        p = sim.particles[j]

        # Check incineration
        incinerated = False
        for k in range(4):  # loop over Aa, Ab, Ba, Bb
            pk = sim.particles[k]
            d = np.sqrt((p.x - pk.x)**2 + (p.y - pk.y)**2 + (p.z - pk.z)**2)
            if d < stellar_radii[k]:
                sim.remove(j)
                incinerated = True
                numIncinerated[i] += 1
                break
        if incinerated:
            continue
        # Check ejection
        if (p.x**2 + p.y**2 + p.z**2 > R_max**2):
            sim.remove(j)
            numEjected[i] += 1
            continue
        j += 1

    numRemainings[i] = len(sim.particles) - 4  # subtract 4 stars
    numEjected[i]+=numEjected[i-1]
    numIncinerated[i]+=numIncinerated[i-1]

for j in range(min(len(sim.particles), num_particles)):
    x[j, 1] = sim.particles[j].x
    y[j, 1] = sim.particles[j].y

