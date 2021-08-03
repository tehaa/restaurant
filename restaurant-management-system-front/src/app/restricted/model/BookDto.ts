export class IBookDto {
    tableId: number;
    bookDate: Date;
    username: string;
    numberOfPersons: number;
    tableNumber: number;
    bookId: number;

    constructor() {
        this.tableId = 0;
        this.bookDate = new Date();
        this.username = '';
        this.numberOfPersons = 0;
        this.tableNumber = 0;
        this.bookId = 0;
    }

}