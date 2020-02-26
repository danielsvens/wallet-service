# wallet-service
simple wallet service

This api uses a h2 database saved on file. resources/data/db.mv.db
The database can be accessed via localhost:8080/h2-console
JDBC url: jdbc:h2:./src/main/resources/data/db

credentials found in application.properties

## Create player with empty wallet.
<blockquote> POST /v1/service/createPlayer </blockquote>

```json
{
    "name": "Daniel"
}
```

## Add funds to wallet.
<blockquote> PUT /v1/service/credit </blockquote>

```json
{
	"transactionId": "da64b73c-f436-4528-a0c5-101db5750355",
	"amount": 10,
	"playerId": 38
}
```

transactionId must be a unique UUID or the transaction will be rejected.

## withdraw funds to wallet.
<blockquote> PUT /v1/service/debit </blockquote>

```json
{
	"transactionId": "da64b73c-f436-4528-a0c5-101db5750355",
	"amount": 10,
	"playerId": 38
}
```

## Get player object and transaction history
<blockquote> GET /v1/service/player/{id} </blockquote>

Example resonse:
```json
{
    "name": "Daniel",
    "playerId": 38,
    "currentBalance": 512,
    "transactionHistory": [
        {
            "id": 65,
            "transactionId": "23f4a052-ac72-46b7-8a88-91adffa7d2fa",
            "amount": 500,
            "type": "CREDIT",
            "date": "2020-02-26T16:35:18.540978",
            "accountId": 39
        },
        {
            "id": 130,
            "transactionId": "ebcc5747-3759-47b3-97bc-4d83946ae5e0",
            "amount": 23,
            "type": "CREDIT",
            "date": "2020-02-26T16:43:17.878655",
            "accountId": 39
        },
        {
            "id": 131,
            "transactionId": "da64b73c-f436-4528-a0c5-101db5750355",
            "amount": -11,
            "type": "DEBIT",
            "date": "2020-02-26T16:43:36.772572",
            "accountId": 39
        }
    ]
}
```