# Service Registry

## API

- [ ] `GET /services/:id`
  - Returns a service network location (URL) for the given ID. If the service does not exist, returns a status of `404`.
- [ ] `DELETE /services/:id`
  - Deletes a service with the given ID from the database. Returns status code of `200` if successful. If the service is not found then returns status code of `404`.
- Additional endpoint to receive events from AWS SNS, parse message and store data inside a database.
