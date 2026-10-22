import os

import httpx
from fastapi import FastAPI, Request

app = FastAPI()

TRIPS_URL = os.environ.get("TRIPS_URL", "http://trips:8001")
AIRPORTS_URL = os.environ.get("AIRPORTS_URL", "http://airports:8002")


@app.get("/ping")
def ping():
    return {"message": "Pong!"}


@app.get("/trip-ping")
def trip_ping():
    r = httpx.get(f"{TRIPS_URL}/ping")
    return {"trip": r.json()}


@app.get("/airport-ping")
def airport_ping():
    r = httpx.get(f"{AIRPORTS_URL}/ping")
    return {"airport": r.json()}


@app.get("/trip/{trip_id}")
def get_trip(trip_id: str):
    r = httpx.get(f"{TRIPS_URL}/trip/{trip_id}")
    return r.json()


@app.post("/trip")
async def create_trip(request: Request):
    data = await request.json()
    r = httpx.post(f"{TRIPS_URL}/trip", json=data)
    return r.json()


@app.get("/airport/{airport_id}")
def get_airport(airport_id: str):
    r = httpx.get(f"{AIRPORTS_URL}/airport/{airport_id}")
    return r.json()


@app.post("/airport")
async def create_airport(request: Request):
    data = await request.json()
    r = httpx.post(f"{AIRPORTS_URL}/airport", json=data)
    return r.json()
