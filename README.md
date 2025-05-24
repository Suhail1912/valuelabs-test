Local Setup
# Clone the repo
git clone https://github.com/your-org/your-repo.git
cd your-repo

# Build the project
mvn clean install

# Run the application
java -jar target/tracker-app.jar

                                        ***** API DETAILS *****
**Sample cURL Command**
curl --location 'localhost:8080/tracker/tag-tracking-id?origin_country_id=IN&destination_country_id=NY&weight=1.232&created_at=2025-05-23T10%3A32%3A00&customer_id=ab056b26-c593-4f98-8d18-762a70f499d5&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics'

**Response**
{
    "status": "OK",
    "message": "Success",
    "data": {
        "parcel_info": {
            "origin_country": "IN",
            "destination_country": "NY",
            "weight": 1.232,
            "created_at": "2025-05-23T10:32:00.000+00:00",
            "customer_id": "ab056b26-c593-4f98-8d18-762a70f499d5",
            "customer_name": "RedBox Logistics",
            "customer_slug": "redbox-logistics"
        },
        "tracking_number": "A2D05347C89C9AD9",
        "created_at": "2025-05-24T15:47:47.773Z"
    }
}