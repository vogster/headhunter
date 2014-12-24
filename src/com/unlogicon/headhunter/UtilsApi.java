package com.unlogicon.headhunter;

import android.app.Activity;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.model.dictionaries.Dictionaries;
import com.unlogicon.headhunter.model.user.User;
import com.unlogicon.headhunter.model.vacancies.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nik on 04.09.14.
 */
public class UtilsApi {

    public static AjaxCallback getCallBack(Object o,String method, Activity activity) {
        Settings settings = new Settings(activity);
        String url = Constants.BASE_URL + method;
        AQUtility.debug("geturl", url);
        AjaxCallback<String> cb = new AjaxCallback<String>();
        cb.url(url).type(String.class).weakHandler(o, "onResponse").fileCache(false).expire(-1);
        if (settings.getAccessToken() != null)
        cb.header("Authorization", "Bearer " + settings.getAccessToken());
        return cb;
    }

    //TODO багнутый метод
    public static AjaxCallback getCallBackFav(Object o,String method, Activity activity) {
        Settings settings = new Settings(activity);
        String url = Constants.BASE_URL + method;
        AQUtility.debug("geturl", url);
        AjaxCallback<String> cb = new AjaxCallback<String>();
        cb.method(AQuery.METHOD_PUT);
        cb.url(url).type(String.class).weakHandler(o, "onResponse").fileCache(false).expire(-1).param("", "");
        if (settings.getAccessToken() != null)
            cb.header("Authorization", "Bearer " + settings.getAccessToken());
        return cb;
    }

    public static AjaxCallback getCallBackLogin(Object o,String code) {
        String url = Constants.TOKEN_URL;
        AQUtility.debug("geturl", url);
        AjaxCallback<String> cb = new AjaxCallback<String>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", Constants.CLIENT_ID);
        params.put("client_secret", Constants.CLIENT_SECRET);
        params.put("code", code);

        cb.url(url).type(String.class).params(params).weakHandler(o, "onResponse").fileCache(false).expire(-1);
        return cb;
    }

    public static AjaxCallback getCallBackRefresh(Object o,String token) {
        String url = Constants.TOKEN_URL;
        AQUtility.debug("geturl", url);
        AjaxCallback<String> cb = new AjaxCallback<String>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", token);

        cb.url(url).type(String.class).params(params).weakHandler(o, "onResponse").fileCache(false).expire(-1);
        return cb;
    }

    public static Vacancies parseVacancies(final JSONObject vacanciesObject){
        Vacancies vacancies = new Vacancies();
        vacancies.setSalary(parseSalary(vacanciesObject.optJSONObject("salary")));
        vacancies.setArchived(vacanciesObject.optString("archived").equals("true"));
        vacancies.setPremium(vacanciesObject.optString("premium").equals("true"));
        vacancies.setName(vacanciesObject.optString("name"));
        vacancies.setArea(parseArea(vacanciesObject.optJSONObject("area")));
        vacancies.setUrl(vacanciesObject.optString("url"));
        vacancies.setCreated_at(vacanciesObject.optString("created_at"));
        vacancies.setEmployer(parseEmployer(vacanciesObject.optJSONObject("employer")));
        vacancies.setResponse_letter_required(vacanciesObject.optString("response_letter_required").equals("true"));
        vacancies.setPublished_at(vacanciesObject.optString("published_at"));

        if (vacanciesObject.optJSONArray("relations") != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < vacanciesObject.optJSONArray("relations").length(); i++){
                try {
                    arrayList.add(vacanciesObject.optJSONArray("relations").get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            vacancies.setRelations(arrayList);
        }
        vacancies.setAddress(parseAddress(vacanciesObject.optJSONObject("address")));
        vacancies.setAlternate_url(vacanciesObject.optString("alternate_url"));
        vacancies.setType(parseType(vacanciesObject.optJSONObject("type")));
        vacancies.setId(vacanciesObject.optString("id"));
        return vacancies;
    }

    public static Vacancy parseVacancy(final JSONObject vacancyObject){
        Vacancy vacancy = new Vacancy();
        vacancy.setPremium(vacancyObject.optString("premium").equals("true"));
        vacancy.setDescription(vacancyObject.optString("description"));
        vacancy.setSchedule(parseSchedule(vacancyObject.optJSONObject("schedule")));
        vacancy.setPublished_at(vacancyObject.optString("published_at"));
        vacancy.setAccept_handicapped(vacancyObject.optString("accept_handicapped").equals("true"));
        vacancy.setAddress(parseAddress(vacancyObject.optJSONObject("address")));
        vacancy.setAlternate_url(vacancyObject.optString("alternate_url"));
        vacancy.setEmployment(parseEmployment(vacancyObject.optJSONObject("employment")));
        vacancy.setId(vacancyObject.optInt("id"));
        vacancy.setSalary(parseSalary(vacancyObject.optJSONObject("salary")));
        vacancy.setArchived(vacancyObject.optBoolean("archived"));
        vacancy.setName(vacancyObject.optString("name"));
        vacancy.setArea(parseArea(vacancyObject.optJSONObject("area")));
        vacancy.setContacts(parseContacts(vacancyObject.optJSONObject("contacts")));
        vacancy.setExperience(parseExperience(vacancyObject.optJSONObject("experience")));
        vacancy.setEmployer(parseEmployer(vacancyObject.optJSONObject("employer")));
        vacancy.setType(parseType(vacancyObject.optJSONObject("type")));
        return vacancy;
    }

    public static User parseUser(final JSONObject userObject){
        User user = new User();

        user.setLast_name(userObject.optString("last_name"));
        user.setResumes_url(userObject.optString("resumes_url"));
        user.setIs_admin(userObject.optBoolean("is_admin"));
        user.setIs_employer(userObject.optBoolean("is_employer"));
        user.setId(userObject.optString("id"));
        user.setFirst_name(userObject.optString("first_name"));
        user.setIs_in_search(userObject.optBoolean("is_in_search"));
        user.setIs_anonymous(userObject.optBoolean("is_anonymous"));
        user.setNegotiations_url(userObject.optString("negotiations_url"));
        user.setIs_applicant(userObject.optBoolean("is_applicant"));
        user.setEmail(userObject.optString("email"));

        return user;
    }

    public static Contacts parseContacts(final JSONObject contactsObject){
        if (contactsObject != null){
            Contacts contacts = new Contacts();
            contacts.setName(contactsObject.optString("name"));
            contacts.setEmail(contactsObject.optString("email"));

            ArrayList<Phones> list = new ArrayList<Phones>();
            for (int i = 0; i < contactsObject.optJSONArray("phones").length(); i++){
                try {
                    list.add(parsePhones(contactsObject.getJSONArray("phones").getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            contacts.setPhones(list.toArray(new Phones[list.size()]));
            return contacts;
        }
        else {
            return null;
        }
    }

    public static Phones parsePhones(final JSONObject phonesObject){
        Phones phones = new Phones();
        phones.setComment(phonesObject.optString("comment"));
        phones.setCity(phonesObject.optString("city"));
        phones.setNumber(phonesObject.optString("number"));
        phones.setCountry(phonesObject.optString("country"));
        return phones;
    }

    public static Region parseRegion(final JSONObject regionObject){
        Region region = new Region();
        if (regionObject.optJSONArray("areas") != null){
            ArrayList<Region> spec = new ArrayList<Region>();

            for (int i = 0; i < regionObject.optJSONArray("areas").length(); i++) {
                try {
                    spec.add(parseRegion(regionObject.optJSONArray("areas").getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            region.setAreas(spec.toArray(new Region[spec.size()]));
            region.setName(regionObject.optString("name"));
            region.setId(regionObject.optString("id"));
            region.setParent(regionObject.optString("parent_id"));
        }
        return region;
    }

    public static Proff parseProff(final JSONObject proffObject){
        Proff proff = new Proff();

        ArrayList<Specializations> spec = new ArrayList<Specializations>();

            for (int i = 0; i < proffObject.optJSONArray("specializations").length(); i++) {
                try {
                    spec.add(parseSpec(proffObject.optJSONArray("specializations").getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        proff.setSpecializations(spec.toArray(new Specializations[spec.size()]));
        proff.setId(proffObject.optString("id"));
        proff.setName(proffObject.optString("name"));
        return proff;
    }

    public static Specializations parseSpec (final JSONObject specObject){
        Specializations specializations = new Specializations();
        specializations.setId(specObject.optString("id"));
        specializations.setName(specObject.optString("name"));
        return  specializations;
    }

    public static Dictionaries parseDictionaries(final JSONObject dictionariesObject){
        Dictionaries dictionaries = new Dictionaries();
        dictionaries.setId(dictionariesObject.optString("id"));
        dictionaries.setName(dictionariesObject.optString("name"));
        return dictionaries;
    }

    public static Cluster parseCluster(final JSONObject clusterObject){
        Cluster cluster = new Cluster();
        cluster.setFound(clusterObject.optString("found"));
        cluster.setPer_page(clusterObject.optString("per_page"));
        cluster.setAlternate_url(clusterObject.optString("alternate_url"));
        cluster.setPage(clusterObject.optString("page"));
        cluster.setPages(clusterObject.optString("pages"));
        return cluster;
    }

    public static Experience parseExperience(final JSONObject experienceObject){
        if (experienceObject != null){
            Experience experience = new Experience();
            experience.setId(experienceObject.optString("id"));
            experience.setName(experienceObject.optString("name"));
            return experience;
        }
        else {
            return null;
        }
    }

    public static Employment parseEmployment(JSONObject employmentObject){
        if (employmentObject != null){
            Employment employment = new Employment();
            employment.setId(employmentObject.optString("id"));
            employment.setName(employmentObject.optString("name"));
            return employment;
        }
        else {
            return null;
        }
    }

    public static Schedule parseSchedule(final JSONObject scheduleObject){
        if (scheduleObject != null) {
            Schedule schedule = new Schedule();
            schedule.setId(scheduleObject.optString("id"));
            schedule.setName(scheduleObject.optString("name"));
            return schedule;
        }
        else {
            return null;
        }
    }

    public static Type parseType(final JSONObject typeObject){
        if (typeObject != null) {
            Type type = new Type();
            type.setId(typeObject.optString("id"));
            type.setName(typeObject.optString("name"));
            return type;
        }
        else {
            return null;
        }
    }

    public static Employer parseEmployer(final JSONObject employerObject){
        if (employerObject != null) {
            Employer employer = new Employer();
            employer.setLogoUrls(parseLogoUrls(employerObject.optJSONObject("logo_urls")));
            employer.setVacancies_url(employerObject.optString("vacancies_url"));
            employer.setName(employerObject.optString("name"));
            employer.setUrl(employerObject.optString("url"));
            employer.setAlternate_url(employerObject.optString("alternate_url"));
            employer.setSite_url(employerObject.optString("site_url"));
            employer.setType(employerObject.optString("type"));
            employer.setId(employerObject.optString("id"));
            employer.setDescription(employerObject.optString("description"));
            return employer;
        }
        else {
            return null;
        }
    }

    public static LogoUrls parseLogoUrls(final JSONObject logoUrlsObjec){
        if (logoUrlsObjec != null) {
            LogoUrls logoUrls = new LogoUrls();
            logoUrls.setUrl_90(logoUrlsObjec.optString("90"));
            logoUrls.setUrl_240(logoUrlsObjec.optString("240"));
            logoUrls.setUrl_original(logoUrlsObjec.optString("original"));
            return logoUrls;
        }
        else {
            return null;
        }
    }

    public static Area parseArea(final JSONObject areaObject){
        if (areaObject != null) {
            Area area = new Area();
            area.setUrl(areaObject.optString("url"));
            area.setId(areaObject.optInt("id"));
            area.setName(areaObject.optString("name"));
            return area;
        }
        else {
            return null;
        }
    }

    public static Salary parseSalary(final JSONObject salaryObject) {
        if (salaryObject != null) {
            Salary salary = new Salary();
            salary.setTo(salaryObject.optInt("to"));
            salary.setFrom(salaryObject.optInt("from"));
            salary.setCurrency(salaryObject.optString("currency"));
            return salary;
        }
        else {
            return null;
        }
    }

    public static Address parseAddress(final JSONObject addressObject){
        if (addressObject != null) {
            Address address = new Address();
            address.setBuilding(addressObject.optString("building"));
            address.setCity(addressObject.optString("city"));
            address.setStreet(addressObject.optString("street"));
            address.setDescription(addressObject.optString("description"));
            address.setMetro(parseMetro(addressObject.optJSONObject("metro")));
            address.setRaw(addressObject.optString("raw"));
            address.setLat(addressObject.optString("lat"));
            address.setLng(addressObject.optString("lng"));
            return address;
        }
        else {
           return null;
        }
    }

    public static Metro parseMetro(final JSONObject metroObject){
        if (metroObject != null) {
            Metro metro = new Metro();
            metro.setLine_name(metroObject.optString("line_name"));
            metro.setStation_name(metroObject.optString("station_id"));
            metro.setLine_id(metroObject.optString("line_id"));
            metro.setLat(metroObject.optString("lat"));
            metro.setStation_name(metroObject.optString("station_name"));
            metro.setLng(metroObject.optString("lng"));
            return metro;
        }
        else {
            return null;
        }
    }

}
