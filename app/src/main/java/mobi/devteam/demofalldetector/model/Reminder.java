package mobi.devteam.demofalldetector.model;

import android.os.Parcel;
import android.os.Parcelable;

import mobi.devteam.demofalldetector.R;
import mobi.devteam.demofalldetector.activity.MyApplication;
import mobi.devteam.demofalldetector.utils.ReminderType;

/**
 * Created by Administrator on 5/21/2017.
 */

public class Reminder implements Parcelable{

    private long id;
    private String name;
    private long start; //time in ms
    private long end;
    private long hour_alarm;
    private int repeat_type;
    private String note;
    private String thumb;

    public Reminder() {
    }

    public Reminder(long id, String name, long start, long end, long hour_alarm, int repeat_type, String note, String thumb) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.hour_alarm = hour_alarm;
        this.repeat_type = repeat_type;
        this.note = note;
        this.thumb = thumb;
    }

    public int get_repeat_type(String text){
        if (text.equals(MyApplication.reminder_types[0])){
            return ReminderType.TYPE_DAILY; //Daily
        }else if (text.equals(MyApplication.reminder_types[1])){
            return ReminderType.TYPE_WEEKLY; //Weekly
        }else if (text.equals(MyApplication.reminder_types[2])){
            return ReminderType.TYPE_MONTHLY; //Monthly
        }else {
            return ReminderType.TYPE_YEARLY; //Yearly
        }

    }

    protected Reminder(Parcel in) {
        id = in.readLong();
        name = in.readString();
        start = in.readLong();
        end = in.readLong();
        hour_alarm = in.readLong();
        repeat_type = in.readInt();
        note = in.readString();
        thumb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(start);
        dest.writeLong(end);
        dest.writeLong(hour_alarm);
        dest.writeInt(repeat_type);
        dest.writeString(note);
        dest.writeString(thumb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getHour_alarm() {
        return hour_alarm;
    }

    public void setHour_alarm(long hour_alarm) {
        this.hour_alarm = hour_alarm;
    }

    public int getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(int repeat_type) {
        this.repeat_type = repeat_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}

