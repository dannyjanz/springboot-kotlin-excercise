# SpringBoot & Kotlin Rest Service

Springboot + Kotlin, seems to be a natural combination for a 
simple and modern approach to a web service

I choose Kotlin since I've been learning it for a while and found
it to have many advantages over Java and I wanted to see how it works
out in a production-like example. Considering the flawless interoperability
with Java we can easily have a mixed codebase and have all of the Java ecosystem
and its libraries plus the advantages of the Kotlin language at our command


## Task Description

Please implement a service that reads in the provided xml and json files and exposes the information via a RESTful API.

File structure:
- products.xml contains master data for all products.
- prices.json contains price information for all products

The API should be designed in a way, that it allows the following use cases:
1. List all products with their master data
2. Show single product with master data and all available prices
3. Show single product price for one product and specific unit

You can use external libraries.

Out of scope:
- Persistence of data
- Authentication/Authorization

## Instructions

- clone repository into folder of choice
- `cd` into the folder and run `./gradlew bootRun`
- open Browser or use HTTP Client to connect to the routes
    - `/products` for all products and their master data
    - `/products/{id}` for single product with master data and all prices
    - `/products/{id}/prices/{unit}` price for single product and unit


## Implementation

Approached it with a simple adoption of Robert C. Martins _Clean Architecture_. 
The Dependencies point inwards from the Controller
to Service, its Data Access and their Interactors.
It assures easy testability and is as well recommended by the 
official SpringBoot guides. 
We can test the entire stack without starting a server
by injecting mock components at every level of the architecture.
Testing in isolation is important as well as convenient, 
integration tests are expensive and only few should be required.

code is mostly written in a declarative style, Kotlin alllows to
write functions as expressions, without a block body and return statement.
Most things can be written this way unless an imperative style is required 
due to direct manipulation of mutable state or the need for an explicit, early return. 

### Package and Component Overview

```
    src
    |- main
        |- java
            |- de.bringmeister
                |- Application.java
        |- kotlin
            |- de.bringmeister
                |- controller
                    + ProductController.kt
                |- model
                    + PackingUnit.kt
                    + Price.kt
                    + Product.kt
                    + ProductWithPrices.kt
                    + SkuUnitPrice.kt
                |- service
                    + ProductService.kt
                    + ProductServiceInstance.kt
                |- storage
                    + FileContentProvider.kt
                    + ProductsFromXml.kt
                    + ProductStorage.kt
                    + Query.kt
                    + Queryable.kt
                    + SkuUnitPricesFromJson.kt
                    + SkuUnitPriceStorage.kt
                |- util
                    + Result.kt
                    + Types.kt
    
    |- test
         |- kotlin
             |- de.bringmeister
                |- controller
                    + ProductControllerIntegrationTest.kt
                    + ProductControllerUTest
                |- service
                    + ProductServiceUTest
                |- storage
                    + ProductsFromXmlUTest
                    + SkuUnitPricesFromJsonUTest
                + MockData.kt

```

- _Controller_
    - Web Endpoint for Requests
    - should act as not more than configuration basically
    - maps a REST Route to the method of a Service 
    - produces the appropriate response for a Client in requested format

- _Service_
    - Layer of Domain logic / Use Cases 
    - exposes method that produce the required data for all our routes
    - uses Data Access interface to gather required data and combine/filter 
    and apply domain logic (if present) to them as needed
    
- _Storage_
    - Usually calls some sort of data storage Interactor, commonly for access to a data base
    - we simply access the content of a file via simple Interactor reading the file for us
    - __Query__
        - alternative to the usual style of having a DAO with a collection of
        auxiliary methods like `getById`, `getByName`, `getByCountryAndCodeAndCustomerProfile` and so on.
        - instead we save that boilerplate code and expose the typed Query from the DAO instead.
        - just like with regular approach the idea is controll over the data-access without exposing the underlying mechanisms
        - the Query gets translated into whatever mechanism the DAO requires, like a SQL Query
        - in this example we delegate to Kotlins `Sequence` class for lazily evaluated filtering and mapping of the in memory list that was read from file
        - obviously not necessary for the scope of this exercise, more a demonstration of the merits that higher-order functions and lazy evaluation can bring to an architecture 

- Result
    - simple way of encapsulating a result and potential errors or null values in a composable fashion
    - more controllable than throwing exceptions and exposes the intent of 
      possible error at api level.
    - choose names for `map` and `flatMap` to be concise with idiomatic Kotlin (at least the collection library uses `flatMap` for monadic joining)

## TODOs - things I still need to fix

- don't hardcode the file paths but supply them via configuration
- The Integration test should not rely on actual production resources, but to achieve that the prior has to be fulfilled
- include U Tests for the utility constructs
- I don't like the fact that using the Jackson Annotations exposes an implementation detail everywhere through the domain model. 
Jackson related information for deserialization should stay behind the boundaries of its architectural layer.
