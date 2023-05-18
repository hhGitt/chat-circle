export interface loginInfoParam {
    account: string;
    password: string;
}


export interface VerificationCodeParam{
    email:string;
    phone_number:string;
}


export interface registerUserParam{
    email: string;
    phone_number: string;
    password: string;
    code: string;
}
