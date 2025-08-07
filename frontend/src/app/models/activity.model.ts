export class Activity {
  id: string;
 constructor(public title: string,
    public  date: string,
    public time: string,
    public duration: string,
    public location: string
 )
  {
    this.id = crypto.randomUUID();
  }
}
