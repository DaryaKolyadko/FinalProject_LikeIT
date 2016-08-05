package com.kolyadko.likeit.listener;

import com.kolyadko.likeit.util.LocaleUtil;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by DaryaKolyadko on 04.08.2016.
 */
public class LikeItSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LocaleUtil.setLocale(httpSessionEvent.getSession(), LocaleUtil.LocaleType.EN_US);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}