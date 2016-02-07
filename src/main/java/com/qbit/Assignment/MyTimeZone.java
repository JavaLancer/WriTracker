package com.qbit.Assignment;

import java.util.Date;
import java.util.TimeZone;

public class MyTimeZone extends TimeZone{

    public String toString() {
        int offset = getRawOffset()/1000;
        int hour = offset/3600;
        int minutes = (offset % 3600)/60;
        return String.format("(GMT%+d:%02d) %s", hour, minutes, getID());
    }

  
    public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
        return 0;
    }

 
    public void setRawOffset(int offsetMillis) {

    }

    @Override
    public int getRawOffset() {
        return TimeZone.getTimeZone(getID()).getRawOffset();
    }

    @Override
    public boolean useDaylightTime() {
        return false;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return false;
    }
}
