package services.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by listman on 5/4/16.
 */
public class DateInfo {
    public int id;
    public Date dateValue;
    public int dateInteger;
    public int year;
    public int month;
    public int day;
    public int quarter;
    public String quarterName;
    public int dayOfYear;
    public int dayOfWeek;
    public int weekOfYear;
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

    public DateInfo(Calendar originCalendar) {
        Calendar calendar = (Calendar) originCalendar.clone();
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
        this.dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        this.weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        this.dateInteger = Integer.parseInt(sf.format(calendar.getTime()));
        this.id = Integer.parseInt(sf.format(calendar.getTime()));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        this.dateValue = new Date(calendar.getTimeInMillis());
        int realMonth = this.month + 1;

        if (realMonth <= 3) {
            this.quarter = 1;
            this.quarterName = "FIRST_TRIMESTER";
        } else if (realMonth > 3 && realMonth <= 6) {
            this.quarter = 2;
            this.quarterName = "SECOND_QUARTER";
        } else if (realMonth > 6 && realMonth <= 9) {
            this.quarter = 3;
            this.quarterName = "THIRD_TRIMESTER";
        } else {
            this.quarter = 4;
            this.quarterName = "FOURTH_TRIMESTER";
        }

    }
}
