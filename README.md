# Epsilon-Robust-Guarding

## Table of Contents
- [Overview](#overview)
- [Features](#features)
  - [Interactive GUI](#interactive-gui)
  - [Visibility Visualization](#visibility-visualization)
  - [Guard Selection](#guard-selection)
  - [Witness Set](#witness-set)
  - [Adjustable Epsilon Parameter](#adjustable-epsilon-parameter)
  - [Toggle System](#toggle-system)
  - [Save and Load Polygons](#save-and-load-polygons)
- [Setup and Installation](#setup-and-installation)
  - [Clone the Repository](#clone-the-repository)
  - [Compile](#compile)
  - [Run](#run)
- [How to Use](#how-to-use)
- [File Format](#file-format)
- [Assumptions and Limitations](#assumptions-and-limitations)

## Overview

This project explores a variation of the Art Gallery Problem using a modified visibility model called $\epsilon$-robust visibility.

In the classical problem, guards are placed inside a polygon such that every point is visible. In this project, we focuses on placing a small set of vertex guards in a simple polygon P such that all vertices P are "seen" under a notion of $\epsilon$-robust visibility. A vertex u is said to $\epsilon$-robustly see another vertex v if the segment uv lies entirely within the polygon and no other vertex lies within distance $\epsilon$ to the segment uv.

## Features

### Interactive GUI
* Click to create a polygon vertices in boundary order.
* Automatic polygon rendering.

### Visibility Visualization
* Toggle $\epsilon$-robust visibility lines between vertices.

### Guard Selection
* Greedy algorithm selects guards that maximize coverage.
* Guards are shown in red.

### Witness Set
* Computes independent witness vertices.
* Provides a lower bound on number of guards.
* Witnesses are shown in green.

### Adjustable Epsilon Parameter
* Slider to change $\epsilon$ dynamically.
* Affects visibillity and guard count.

### Toggle System
* Toggle guards, witnesses, and visibility lines independently.

### Save and Load Polygons
* Save polygon to `.txt` files.
* Load polygons from `.txt` files.

## Setup and Installation

### Clone the Repository

Using HTTPS:
```
git clone https://github.com/adrienajiang/Epsilon-Robust-Guarding.git
cd Epsilon-Robust-Guarding
```

Using SSH:
```
git@github.com:adrienajiang/Epsilon-Robust-Guarding.git
cd Epsilon-Robust-Guarding
```

### Compile
```
javac src/*.java
```

### Run
```
java -cp src GuardingGUI
```

## How to Use

### 1. Draw a Polygon
* Click points in order around the polygon boundary.

### 2. Adjust $\epsilon$
* Use the slide on the right panel to change $\epsilon$.

### 3. Toggle Features
* Toggle Guards $\rightarrow$ shows red guard vertices.
* Toggle Witnesses $\rightarrow$ shows green witness vertices.
* Toggle Visibility Lines $\rightarrow$ shows gray visibility edges.

### 4. Save and Load Polygons
* Save current polygon file to a file.
* Load a polygon from a file.

## File Format
Each line in the `.txt` file represents a vertex.
```
x y
```

Example:
```
100 100
400 100
450 250
300 350
150 300
```
Note: Vertices must be listen in boundary order (clockwise or counterclockwise).

## Assumptions and Limitations
* Polygons must be simple.
* Vertices must be input in correct boundary order.
* Greedy algorithm does not guarantee optimality.
