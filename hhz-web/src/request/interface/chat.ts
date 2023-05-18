export enum SendObj {
    room = 1,
    people = 2
}

export interface chatTypeParam {
    page: number;  // 当前页数
    size: number;  // 大小
    roomType: string;  // 房间类型
    roomName: string;
}

export interface chatDateMsg {
    acceptId: string;  // 房间id
    size: number;  // 条数
    date: string;  // 日期
    id: string | null;  // 最前一条的id
}