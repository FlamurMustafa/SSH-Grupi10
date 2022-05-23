package com.example.ui;

import java.util.Date;

public class Schedule {
    private Integer scheduleId;
    private Integer roomId;
    private String startTime;
    private String endTime;
    private Integer classId;

    public Schedule(Integer scheduleId, Integer roomId, String startTime, String endTime, Integer classId) {
        this.scheduleId = scheduleId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classId = classId;
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
