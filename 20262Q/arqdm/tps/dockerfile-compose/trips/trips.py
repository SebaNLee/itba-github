import os
import uuid

import redis
from fastapi import FastAPI, Request

app = FastAPI()

r = redis.Redis(
    host=os.environ.get("REDIS_HOST", "redis-trips"),
    port=int(os.environ.get("REDIS_PORT", 6379)),
    decode_responses=True,
)


@app.get("/ping")
def ping():
    return {"message": "Pong!"}


@app.get("/trip/{trip_id}")
def get_trip(trip_id: str):
    trip = r.hgetall(trip_id)
    if not trip:
        return {"trip": None}
    return {"trip": trip}


@app.post("/trip")
async def create_trip(request: Request):
    data = await request.json()
    trip_id = uuid.uuid4().hex
    r.hset(trip_id, mapping={
        "from": data["airport_from"],
        "to": data["airport_to"],
    })
    return {"trip_id": trip_id}
