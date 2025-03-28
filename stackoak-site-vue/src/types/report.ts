// 类型定义
export enum ReportTargetType {
    ARTICLE = 1,
    COMMENT = 2,
}

export enum ReportReasonType {
    PORN = 1,
    VIOLENCE = 2,
    AD = 3,
    OTHER = 4,
}

export enum ReportStatus {
    PENDING = 0,
    PROCESSED = 1,
    REJECTED = 2,
}

// 举报请求参数
export interface ReportRequest {
    targetType: ReportTargetType;
    targetId: string;
    reasonType?: ReportReasonType;
    reasonText?: string;
}

// 举报响应
export interface ReportResponse {
    reportId: string;
    status: ReportStatus;
    createdAt: string;
}
