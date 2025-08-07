export class Trip {
  id: string;
 constructor(public destination: string,
  public  startDate: string,
  public endDate: string,
  public admin: string)
  {
    this.id = crypto.randomUUID();
  }
}
