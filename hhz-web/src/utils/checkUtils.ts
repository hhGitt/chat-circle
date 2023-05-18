export class CheckUtils {
    public static isEmpty(str: string) {
        return str == undefined || str === null || str.trim().length === 0;
    }

    public static isPhone(phone: string) {
        if (this.isEmpty(phone)) return false;
        let reg = /^1[3|4|5|7|8][0-9]{9}/;
        return reg.test(phone);
    }

    public static isEmail(email: string) {
        if (this.isEmpty(email)) return false;
        let reg = /[a-zA-Z0-9\.\_\-\%\+]@[a-zA-Z0-9\.\-]{2,4}.com/;
        return reg.test(email);
    }

    public static isSpecialCharacter(str:string){
        let reg = /[\.\@\#\$\%\&\^\*]/;
        return reg.test(str);
    }
}