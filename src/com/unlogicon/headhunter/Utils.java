package com.unlogicon.headhunter;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.util.Log;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unlogicon.headhunter.model.vacancies.Salary;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nik on 03.10.14.
 */
public class Utils {

    public static Map<String,String> getHashMapResource(Activity activity, int hashMapResId, String name) {
        Map<String,String> map = null;
        XmlResourceParser parser = activity.getResources().getXml(hashMapResId);

        String key = null, value = null;

        try {
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("utils", "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals(name)) {
                        boolean isLinked = parser.getAttributeBooleanValue(null, "linked", false);

                        map = isLinked ? new LinkedHashMap<String, String>() : new HashMap<String, String>();
                    } else if (parser.getName().equals("entry")) {
                        key = parser.getAttributeValue(null, "key");

                        if (null == key) {
                            parser.close();
                            return null;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("entry")) {
                        map.put(key, value);
                        key = null;
                        value = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.getText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return map;
    }

    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /** Transform ISO 8601 string to Calendar. */
    public static Date toCalendar(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string;
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        calendar.setTime(date);
        return date;
    }



    public static String readAssets(Activity activity, String name){
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = activity.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return null;
    }



    public static <T> T getObjectFromJson(Class<T> paramClass, String paramString)
    {
        Gson localGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
        try
        {
            Object localObject = localGson.fromJson(paramString, paramClass);
            return (T)localObject;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return null;
    }

    public static String salaryForm(Activity activity,Salary salary) {
        if (salary != null) {

            if (salary.getFrom() != 0 && salary.getTo() != 0) {
                return activity.getString(R.string.salary_from) + " " + salary.getFrom() + " "
                        + activity.getString(R.string.salary_to) + " " + salary.getTo();
            }
            else if (salary.getFrom() != 0 && salary.getTo() == 0){
                return activity.getString(R.string.salary_from) + " " + salary.getFrom();
            }
            else if (salary.getFrom() == 0 && salary.getTo() != 0){
                return activity.getString(R.string.salary_to) + " " + salary.getTo();
            }

        }
            return activity.getString(R.string.salary_none);
    }


}
