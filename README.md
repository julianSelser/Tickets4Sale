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

The idea is that each submodule can be worked on and built independently, ideally moved to its own repo in the future. Also, there will be a `Main` for each project

    tickets4sale
    |
    ├── core                    # Core business code
    ├── cli                     # CLI application
    ├── web                     # Web application
    ├── project
    ├── build.sbt
    └── README.md

## Core

In order to fulfill all requirements, I found the best way to model the domain is to place an emphasis on the `Show`, with an interface like:

````
//some code has been ommited
case class Show(title: String, openingDay: String, genre: String, id: Option[Long] = None, ticketsSoldForDay: Option[Int] = None) {
    def availability(queryDate: String, showDate: String): ShowAvailability
    def availabilityWithPrice(queryDate: String, showDate: String): ShowAvailability
}
````

It will be used to read through the list of shows and produce its availabilitie

Also there's 3 peculiarities to note:
 * There's 2 `availability` methods, one includes the price, used for the second use case
 * The optional `id` field will be used later to introduce a database for orders. Being a scala optional parameters, it can be ignored when not needed
 * The `Show` optionally take a number of tickets sold for the `showDate`. While not needed for the 1° and 2° use case, it helps with the bonus

The rest are dumb data holders except for `Inventory` who builds the correct user facing structure. Note everything in `core` is **immutable**. Also all the prices will be modeled as `Int` 


