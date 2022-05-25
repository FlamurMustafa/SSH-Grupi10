package com.example.ui;

import java.util.Date;

public class Schedule {
    private Integer scheduleId;
    private Integer roomId;
    private String startTime;
    private String endTime;
    private String className;

    public Schedule(Integer scheduleId, Integer roomId, String startTime, String endTime, String className) {
        this.scheduleId = scheduleId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.className = className;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClassId() {
        return className;
    }

    public void setClassId(String classId) {
        this.className = className;
    }
}
