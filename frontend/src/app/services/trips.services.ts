import { Injectable } from "@angular/core";
import { Trip } from "../models/trip.model";

@Injectable({
    providedIn: "root"
})
export class TripService{
    private trips : Trip[] = 
    [new Trip(
        "Paris",
        "17/08/2025",
        "19/08/2025",
        "ladministrator"
    ),
    new Trip(
        "Istanbul",
        "21/05/2026",
        "31/05/2026",
        "bigboss"
    ),
    new Trip(
        "Genève",
        "01/01/2027",
        "09/01/2027",
        "pabloescobar"
    )
]

    getTrips(): Trip[]{
        return this.trips;
    }
}