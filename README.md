# Simple-Banking-App
simple banking application with jwt authentication and transaction logging
## Table of contents
* [Sample Requests](#sample-requests)

## Sample Requests
using insomina/postman
* POST localhost:8080/authenticate
```
{
	"userName":"Chaluutali",
	"password":"1234"
}
```
* POST localhost:8080/withdraw (use token from authenticate above in the headers of this request)
```
{
	"accountNumber":"0987654321",
	"amount":8000
}
```
* POST localhost:8080/deposit (use token from authenticate above in the headers of this request)
```
{
	"accountNumber":"0987654321",
	"amount":50
}
```
* POST localhost:8080/deposit (use token from authenticate above in the headers of this request)
```
{
	"accountFrom":"0987654322",
	"accountTo":"0987654321",
	"amount": 1000
}
```
