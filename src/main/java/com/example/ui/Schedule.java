package com.example.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        this.startTime = timeStampConvertToTime(startTime);
        this.endTime = timeStampConvertToTime(endTime);
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

    private static SimpleDateFormat timestampformat =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");

    private static SimpleDateFormat sdftimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static SimpleDateFormat getSdftimeformat() {
        return sdftimeformat;
    }
    public static String timeStampConvertToTime(String time) {
        Date date1 = null;

        try {
            date1 = timestampformat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = getSdftimeformat().format(date1);
        return formattedTime;
    }
}
