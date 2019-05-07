# Tickets4Sale

Helps John get a clear grasp on ticket inventory. Especially how many tickets are left and how many tickets have been sold

## Usage

This document assumes you have `sbt` and `scala` installed

### Running the CLI tool

Run `sbt stage` on the root. Then you can cd into `cli/target/universal/stage/bin/` where you'll find `tickets4sale-cli`

Usage: `tickets4sale-cli <input-csv> <query-date> <show-date>`

You can find a sample `input.csv` in the `core/src/main/resources` folder. The input **should always have headers**

### Running the website

**start locally**: `sbt run` and open a browser in `localhost:9000`

**for production**: 
 * You could execute clone the project on the target server and run `sudo sbt run 80` (port 80) 
 * Or you could `sbt stage` and then move the entire `tickets4sale/web/target/universal/stage` to a java enabled environment and run the scripts in `tickets4sale/web/target/universal/stage/bin` 

# Decisions

## Structure

There is a root `build.sbt` and a subproject with its own for each different usage of the core business code

The idea is that each submodule can be worked on and built independently, ideally moved to its own repo in the future. Also, there is potentially a `Main` for each project

    tickets4sale
    |
    ├── core                    # Core business code
    ├── cli                     # CLI application
    ├── web                     # Web application
    ├── project
    ├── build.sbt
    └── README.md

## Core module

Holds everything *business domain* related, save for a few helpers used cross project. Will be used by the other modules to implement the use cases. The important thing is that we have a reusable core independent of any application

In order to fulfill all requirements, I found the best way to model the domain is to place an emphasis on the `Show`, with an interface like:

````
//some code has been ommited
case class Show(title: String, openingDay: String, genre: String, id: Option[Long] = None, ticketsSoldForDay: Option[Int] = None) {
    def availability(queryDate: String, showDate: String): ShowAvailability
    def availabilityWithPrice(queryDate: String, showDate: String): ShowAvailability
}
````

It will be used to read through the list of shows and produce its availabilities

Also there's 3 peculiarities to note:
 * There's 2 `availability` methods, one includes the price, used for the second use case
 * The optional `id` field will be used later to introduce a database for orders. Being a scala optional parameters, it can be ignored when not needed
 * The `Show` optionally take a number of tickets sold for the `showDate`. While not needed for the 1° and 2° use case, it helps with the bonus

`Inventory` builds the correct user facing structure. Tests in `InventorySpec` were made to match the given excercise specifications exactly. You may recognize the following from the excercise's **page 3**:

```
      Inventory(List(
        Genre(drama, List(ShowAvailability("Everyman", 100, 10, openForSale))),
        Genre(comedy, List(ShowAvailability("Comedy of Errors", 100, 10, openForSale))),
        Genre(musical, List(ShowAvailability("Cats", 50, 5, openForSale)))))
```
  
Note everything in `core` is **immutable**. Also all the prices will be modeled as `Int` as its not the focus of the excercise to correctly handle money (with `BigDecimal`, transactions and all its beauty)

## CLI

It should be as simple as it gets, reading input, validating and then using what was built in `core` for the actual logic. Not really worth testing except for the ShowParser which reads CSV (couldn't find a decent CSV reader)

I'm using spray-json to turn an inventory into json. The result is almost identical to what was asked, save for the fact that I'm using camelCase instead of snake_case for json fields

This takes care of the first use case

## Web

It consists of a frontend and a backend. Solves both second and bonus third use case

### Frontend

Its a simple statically served **React SPA** without **Redux**. Everything is bundled from `/resources` and no build tool is needed (no `npm`, no `webpack`, thanks jesus)

**DISCLAIMER:** As a consequence of not using a build tool, the setup is not suited for production. I couldn't find a decent `sbt` plugin that works

While the excercise said that *the inventory would be set by the website and the user doesnt have to upload* I developed the feature anyways in order to test thoroughly, I think John will like it. **IMPORTANT NOTE**: Use `input.csv` to test for orders as the `original.csv` is set in the past   

### Backend 
