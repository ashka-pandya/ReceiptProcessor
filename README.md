# Receipt Service - Backend Take-Home Exercise

## Overview

This project implements a receipt processing service that allows you to submit receipts and calculate points based on various rules using Java. The service exposes two API endpoints:

1. `POST /receipts/process`: Accepts a receipt in JSON format and returns a unique ID.
2. `GET /receipts/{id}/points`: Accepts the receipt ID and returns the points awarded to the receipt.

## Running the Application

### Prerequisites

Ensure the following are installed on your machine:

- [Docker](https://www.docker.com/)
- [Java](https://www.java.com/)
- [Gradle](https://gradle.org/install/)

### Running with Docker

To run the service using Docker, follow these steps:

1. **Build the Docker image**:
   In the project directory, build the Docker image using the following command:

   ```bash
   docker build -t receipt-service .
   ```
2. **Run the Docker container**:
   After building the image, run the Docker container:

    ```bash
   docker run -p 8080:8080 receipt-service
   ```
   This will start the application on port 8080.

## Testing with Postman

### Testing the POST /receipts/process Endpoint

1. Open Postman and create a new request.
2. Set the method to POST.
3.	Use the following URL for the request: http://localhost:8080/receipts/process.
4.	In the “Headers” tab, set Content-Type to application/json.
5.	In the “Body” tab, select raw and paste the following JSON:

```
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },
    {
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },
    {
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },
    {
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },
    {
      "shortDescription": "Klarbrunn 12-PK 12 FL OZ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}
```
6. Click send
7. You should receive a response with the receipt id, like:

```
{
  "id": "7fb1377b-b223-49d9-a31a-5a02701dd310"
}
```

### Testing the GET /receipts/{id}/points Endpoint

1.	Create a new request in Postman.
2. Set the method to GET.
3. Use the following URL for the request (replace {id} with the ID returned from the previous POST request):

```
http://localhost:8080/receipts/7fb1377b-b223-49d9-a31a-5a02701dd310/points
```

4.	Click Send.
5. You should receive a response with the points, like:

```
{
  "points": 28
}
```