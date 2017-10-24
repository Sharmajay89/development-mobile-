package com.cws.cwsbaseapplication.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cws on 23/10/17.
 */

public class ActionDetails implements Parcelable {
    int id;
    String name;
    String actionName;
    String type;

    protected ActionDetails(Parcel in) {
        id = in.readInt();
        name = in.readString();
        actionName = in.readString();
        type = in.readString();
    }

    public ActionDetails() {
    }

    public ActionDetails(int id, String name, String actionName, String type) {
        this.id = id;
        this.name = name;
        this.actionName = actionName;
        this.type = type;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(actionName);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionDetails> CREATOR = new Creator<ActionDetails>() {
        @Override
        public ActionDetails createFromParcel(Parcel in) {
            return new ActionDetails(in);
        }

        @Override
        public ActionDetails[] newArray(int size) {
            return new ActionDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<ActionDetails> getDashBoardActionList(){
        ArrayList<ActionDetails> actionDetailses = new ArrayList<>();
        actionDetailses.add(new ActionDetails(0,"Action0","Action0Namedsjgkjgashdasjj","type0"));
        actionDetailses.add(new ActionDetails(1,"Action1","Action1Namesadfkoslkgdgkjakjgksjkjgkljgshanv  g gduhgsfdhg","type1"));
        actionDetailses.add(new ActionDetails(2,"Action2","Action2Namesadgjhghhgh","type2"));
        actionDetailses.add(new ActionDetails(3,"Action3","Action3Namesgjagkjkj","type3"));
        actionDetailses.add(new ActionDetails(4,"Action4","Action4NamesgahgjheMDWUAUWEUImfdsusnmgnurueurnyugnrunyuyngyusye","type4"));
        return actionDetailses;
    }
}
