import { Component, OnInit } from "@angular/core";
import { Trip } from "../../../models/trip.model";
import { TripService } from "../../../services/trips.services";

@Component ({
    standalone: false,
    selector: 'tryp-upcoming-trips',
    templateUrl: './upcoming-trips.component.html',
    styleUrl: './upcoming-trips.component.scss'
})

export class UpcomingTripsComponent implements OnInit{
    trips!: Trip[];
    constructor(private tripService: TripService){

    }
    ngOnInit(): void{
        this.trips = this.tripService.getTrips();
    }


    trackByTrip(index: number, trip: Trip): string {
        return trip.id;
    }
}
