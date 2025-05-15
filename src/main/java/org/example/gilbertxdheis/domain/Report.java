package org.example.gilbertxdheis.domain;

public class Report {
    private int reportId;
    private int reporterId;
    private int sellerId;
    private String reason;
    private String status;

    public Report(int reportId, int reporterId, int sellerId, String reason, String status) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.sellerId = sellerId;
        this.reason = reason;
        this.status = status;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}