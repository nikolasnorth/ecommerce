# E-Commerce REST API

## Introduction

This project is a REST API for a replica e-commerce store built using the microservice architecture. The backend
consists of five services: Authentication, Account, Payment, Email, and Product. Clients send requests to an API Gateway
which redirects the request to the appropriate service. For protected services, the API Gateway verifies client requests
have proper authorization in the form of JWT access tokens. A service registry is used to store the network locations of
services. The service registry is able to dynamically update the locations of services using an asynchronous
Publish-Subscribe pattern. At every given time interval each service publishes to a message broker their service ID and
network location. The service registry subscribes to these messages and updates its local storage accordingly.

## Account Service

### API

- `GET /accounts/:id`
    - Retrieves an account with the given `id`.
- `GET /accounts?email=`
    - Retrieves an account with the given `email`.
- `POST /accounts`
    - Creates a new account.
- `PUT /account/:id`
    - Updates an account with the given `id`.
- `DELETE /accounts/:id`
    - Deletes an account with the given `id`.

## Authentication Service

### API

- `POST /signup`
    - Registers a new account and generates a new access/refresh token pair.
- `POST /signin`
    - Logs in account and generates a new access/refresh token pair.
- `GET /signout`
    - Logs out account and invalidates access/refresh token pair.
- `POST /token`
    - Generates a new access token, given a valid refresh token.

## Product Service

### API

- `GET /products`
    - Returns a list of all products.
- `GET /products/:id`
    - Returns the product with the given `id`.
- `POST /products`
    - Creates a new product.
- `PUT /products/:id`
    - Updates a product with the given `id`.
- `DELETE /products/:id`
    - Deletes the product with the given `id`.

## Service Registry

### API

- `GET /services/:id`
    - Returns a service network location (URL) for the given ID. If the service does not exist, returns a status
      of `404`.
- `DELETE /services/:id`
    - Deletes a service with the given ID from the database. Returns status code of `200` if successful. If the service
      is not found then returns status code of `404`.
- `POST /services`
  - Stores a new service and network location, or overwrites previous if it exists.
