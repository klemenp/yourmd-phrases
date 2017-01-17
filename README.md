# PHRASES REST Microservice

REST micro-service that takes as input a string 
parameter and returns a list of matched phrases from a dictionary list.

Runs on default port `8080`.

# Endpoint

Path: `/matched_phrases`

Query param: `input_text`

Http method: `GET`

# Example usage

Start the service:
```gradle bootRun```

Example request:
```
GET http://127.0.0.1:8080/matched_phrases?input_text=I%20have%20a%20sore%20throat%20and%20headache.
```

```
HTTP/1.1 200 OK
["headache","sore throat"]
```
