import os
import uuid

import redis
from fastapi import FastAPI, Request

app = FastAPI()

r = redis.Redis(
    host=os.environ.get("REDIS_HOST", "redis-airports"),
    port=int(os.environ.get("REDIS_PORT", 6379)),
    decode_responses=True,
)


@app.get("/ping")
def ping():
    return {"message": "Pong!"}


@app.get("/airport/{airport_id}")
def get_airport(airport_id: str):
    airport = r.get(airport_id)
    if not airport:
        return {"airport": None}
    return {"airport": airport}


@app.post("/airport")
async def create_airport(request: Request):
    data = await request.json()
    airport_id = uuid.uuid4().hex
    r.set(airport_id, data["airport"])
    return {"airport_id": airport_id}
