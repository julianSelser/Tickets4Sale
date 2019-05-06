# Tickets4Sale

Helps John get a clear grasp on ticket inventory. Especially how many tickets are left and how many tickets have been sold

## Usage

This document assumes you have `sbt` and `scala` installed

### CLI

Run `sbt stage` on the root TODO

### Website

**start locally**: `sbt run` and open a browser in `localhost:9000`

**for production**: `sbt stage` and then TODO


## Structure

There is a root `build.sbt` and a subproject with its own for each different usage of the core business code

The idea is that each submodule can be worked on and built independently, ideally moved to its own repo in the future


    tickets4sale
    |
    ├── cli                     # CLI application
    ├── core                    # Core business code
    ├── web                     # Web application
    ├── project
    ├── build.sbt
    └── README.md

