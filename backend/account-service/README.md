# Account Service

## API

- [x] `GET /accounts/:id`
  - Returns an account with the given id.
  - `id`: Account id.
- [x] `POST /accounts`
  - Creates a new account.
- [ ] `PUT /accounts/:id`
  - Updates an account with the given id.
  - `id`: Account id
- [ ] `PUT /accounts/:id?addRating=`
  - Calculates a new average rating incorporating the given value. Returns the account with the rating updated.
  - `id`: Account id
  - `addRating`: New rating to be incorporated into the account's total average
- [x] `DELETE /accounts/:id`
  - Deletes and returns an account with the given id.
  - `id`: Account id

