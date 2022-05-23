package com.example.ui;

import java.util.Date;

public class Schedule {
    private Integer scheduleId;
    private Integer roomId;
    private Date startTime;
    private Date endTime;
    private Integer classId;

    public Schedule(Integer scheduleId, Integer roomId, Date startTime, Date endTime, Integer classId) {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
