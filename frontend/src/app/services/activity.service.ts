import { Injectable } from "@angular/core";
import { Activity } from "../models/activity.model";

@Injectable({
    providedIn: "root"
})
export class ActivityService{
    private activities : Activity[] = 
    [new Activity(
        "Visite de la statue de Napoléon",
        "17/08/2025",
        "15h00",
        "20min",
        "Place des jacobins"
    ),
    new Activity(
        "Fourvière",
        "17/08/2025",
        "16h00",
        "40min",
        "Funiculaire F1"
    ),
    new Activity(
        "Shopping",
        "17/08/2025",
        "18h00",
        "50min",
        "CC. de Confluence"
    ),
    
]

    getActivities(): Activity[]{
        return this.activities;
    }
}