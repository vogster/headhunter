package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nik on 29.09.14.
 */
public class Vacancy implements Parcelable {

    private boolean premium;
    private String description;
    private Schedule schedule;
    private String published_at;
    private boolean accept_handicapped;
    private Address address;
    private String alternate_url;
    private Employment employment;
    private int id;
    private Salary salary;
    private boolean archived;
    private String name;
    private Area area;
    private Contacts contacts;
    private Experience experience;
    private Employer employer;
    private Type type;

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public boolean isAccept_handicapped() {
        return accept_handicapped;
    }

    public void setAccept_handicapped(boolean accept_handicapped) {
        this.accept_handicapped = accept_handicapped;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAlternate_url() {
        return alternate_url;
    }

    public void setAlternate_url(String alternate_url) {
        this.alternate_url = alternate_url;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Contacts getContacts(){
        return contacts;
    }

    public void setContacts(Contacts contacts){
        this.contacts = contacts;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Vacancy(){

    }

    public Vacancy(Parcel in){
        premium = in.readByte() == 1;
        description = in.readString();
        schedule = in.readParcelable(Schedule.class.getClassLoader());
        published_at = in.readString();
        accept_handicapped = in.readByte() == 1;
        address = in.readParcelable(Address.class.getClassLoader());
        alternate_url = in.readString();
        employment = in.readParcelable(Employment.class.getClassLoader());
        id = in.readInt();
        salary = in.readParcelable(Salary.class.getClassLoader());
        archived = in.readByte() == 1;
        name = in.readString();
        area = in.readParcelable(Area.class.getClassLoader());
        contacts = in.readParcelable(Contacts.class.getClassLoader());
        experience = in.readParcelable(Experience.class.getClassLoader());
        employer = in.readParcelable(Employer.class.getClassLoader());
        type = in.readParcelable(Type.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(premium ? 1 : 0));
        dest.writeString(description);
        dest.writeParcelable(schedule, flags);
        dest.writeString(published_at);
        dest.writeByte((byte)(accept_handicapped ? 1 : 0));
        dest.writeParcelable(address, flags);
        dest.writeString(alternate_url);
        dest.writeParcelable(employment, flags);
        dest.writeInt(id);
        dest.writeParcelable(salary, flags);
        dest.writeByte((byte)(archived ? 1 : 0));
        dest.writeString(name);
        dest.writeParcelable(area, flags);
        dest.writeParcelable(contacts, flags);
        dest.writeParcelable(experience, flags);
        dest.writeParcelable(employer, flags);
        dest.writeParcelable(type, flags);
    }
}
