# Things I did during the Rice Planetary Science Internship
Import all the libraries in Libraries.py for the Python code to work

## GG Tauri Folder
  Contains the code I used to make my poster. Has the actual integration and the numpy plots
  ### ObjectIntegration.py
    Simulates the four stars in the GG Tauri system and 2000 test particles in the GG Tauri A circumbinary disc for 10 million years
    The test particles are massless particles randomly generated in the observed GG Tauri circumbinary disc
    Records the start positions of all objects and the end positions of surviving objects
    Records the number of objects that survive, incinerated, or ejected
    Ejected particles are particles over 3000 AU away from the center of the system
  ### Plots.py
    Plots the starting positions of all particles and the end positions of all surviving particles
    Plots the number of survived, incinerated, and ejected particles with respect to time

## LeapfrogIntegrator Folder
  Contains the Java code I did to implement the leapfrog algorithm
  Assumes all objects and particles are point masses
  ### Body.java
    Class for all the bodies in the system, such as stars, planets, or other particles
    The constructor takes the mass and the initial starting position of the body
    Includes helper functions
    magR(), magV(), magA()
      Used to calculate the magnitudes of position, velocity, and acceleration
    cm(Body[] bodies)
      Used to calculate the center of mass of a list of bodies
    v0(Body p)
      Used to calculate the initial Keplerian velocity
    a(Body[] bodies)
      Used to update the acceleration vector
    E(Body[] bodies)
      Used to calculate the total energy of the system
  ### Leapfrog.java
    Has an implementation of the leapfrog algorithm with 3 sun-like stars in an equilateral triangle
  ### LeapfrogExample.java
    Has the Sun-Earth-Moon system in my leapfrog code
## Suns in a Geometric Configuration
  I put some Suns in geometric shapes to see how they move
  ### EquilateralTriangle.py
    Equilateral triangle is already solved by Lagrange in 1772 and is shown here
  ### Square.py
    Square is not solved, and the stars move chaotically, pairing off and re-pairing off
