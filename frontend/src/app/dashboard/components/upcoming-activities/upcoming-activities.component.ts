import { Component, OnInit } from "@angular/core";
import { Activity } from "../../../models/activity.model";
import { ActivityService } from "../../../services/activity.service";

@Component({
    standalone: false,
    selector: 'tryp-upcoming-activities',
    templateUrl: './upcoming-activities.component.html',
    styleUrl: './upcoming-activities.component.scss'
})

export class UpcomingActivitiesComponent implements OnInit{
    activities!: Activity[];
    constructor(private activityService: ActivityService){

    }
    ngOnInit(): void{
        this.activities = this.activityService.getActivities();
    }


    trackByActivity(index: number, activity: Activity): string {
        return activity.id;
    }
}

