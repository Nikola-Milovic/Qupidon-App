package com.nikolam.common.utils;


import android.content.Context;

import androidx.annotation.Nullable;

import java.util.Locale;

public class GetTimePassed {

    private GetTimePassed() {
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time, Context context) {
        String text = "";
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        switch (Locale.getDefault().getDisplayLanguage()) {
            case "srpski":
                text = getSerbianTime(now, time, context);
                break;
            default:
                text = getEnglishTime(now, time, context);
        }
        return text;
    }

    private static String getSerbianTime(long now, long time, Context context) {
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "upravo sada";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "pre minut";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return "pre " + diff / MINUTE_MILLIS + " minuta";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "pre sat vremena";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "pre " + diff / HOUR_MILLIS + " sati";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "juče";
        } else {
            long days = diff / DAY_MILLIS;

            if (days > 365) {
                int t = (int) Math.floor((diff / DAY_MILLIS) / 365);
                String s = "";
                if (t == 1) {
                    s = " godinu";
                } else {
                    s = " godine";
                }

                return "pre " + t + s;
            }

            return "pre " + diff / DAY_MILLIS + " dana";
        }
    }

    private static String getEnglishTime(long now, long time, Context context) {
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            long days = diff / DAY_MILLIS;

            if (days > 365) {
                int t = (int) Math.floor((diff / DAY_MILLIS) / 365);
                String s = "";
                if (t == 1) {
                    s = " year ago";
                } else {
                    s = " years ago";
                }

                return t + s;
            }

            return diff / DAY_MILLIS + " days ago";
        }
    }

}

