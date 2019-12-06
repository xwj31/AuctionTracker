# AuctionTracker

AuctionTracker is a Spring boot REST API project for demonstrating auctions.

## Running
To start the application:

```bash
mvn spring-boot:run
```

## Testing

To run all tests:

```bash
mvn test
```

## Usage

A Swagger UI is available at:
``` 
http://localhost:8080/swagger-ui.html
```

A Swagger specification is available at: 
```
http://localhost:8080/v2/api-docs
```
## Endpoints

### Make a bid

```
PUT /items/{itemId/accounts/{accountId}
```
#### Response
```200 OK```
```
{
  "amount": 0,
  "bidId": 0,
  "createDate": "2019-12-06T13:59:05.510Z",
  "modifyDate": "2019-12-06T13:59:05.510Z"
}
```

### Get all bids on an item 

```
GET /items/{itemId}/bids
```
#### Response
```200 OK```
```
[
  {
    "amount": 0,
    "bidId": 0,
    "createDate": "2019-12-06T14:01:18.840Z",
    "modifyDate": "2019-12-06T14:01:18.840Z"
  }
]
```

### Get current winning bid for item 

```
GET /items/{itemId}/bids/current-winning-bid
```
#### Response
```200 OK```
```
  {
    "amount": 0,
    "bidId": 0,
    "createDate": "2019-12-06T14:01:18.840Z",
    "modifyDate": "2019-12-06T14:01:18.840Z"
  }
```

### Get all items an account has bid on

```
GET /items/accounts/{accountId}
```
#### Response
```200 OK```
```
  [
    {
      "description": "string",
      "itemId": 0,
      "name": "string"
    }
  ]
```

## License
[MIT](https://choosealicense.com/licenses/mit/)