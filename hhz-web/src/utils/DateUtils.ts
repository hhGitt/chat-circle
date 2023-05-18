export class DateUtils {
    public static formateDate(str: string) {
        let date = new Date(str);
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');;
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    public static formateDateTime(str: string) {
        let date = new Date(str);
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');;
        const day = date.getDate().toString().padStart(2, '0');
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const milliseconds = date.getSeconds().toString().padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}:${milliseconds}`;
    }

    public static formateMonthTime(str: string) {
        let date = new Date(str);
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        return `${hours}:${minutes}`;
    }
}

