export class Invitation {
    id: string;
    constructor (
    public username : string,
    public picture : string,
    public status: string
    ){
    this.id = crypto.randomUUID();
    }

    changeStatus(status: string):void{
        if (status === "accepted" || status === "refused"){
            this.status = status;
        }
  }
}