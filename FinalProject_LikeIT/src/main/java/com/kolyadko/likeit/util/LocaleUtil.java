package com.kolyadko.likeit.util;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created by DaryaKolyadko on 04.08.2016.
 */
public class LocaleUtil {
    private static final String ATTR_LOCALE = "locale";

    private static final Logger LOG = LogManager.getLogger(LocaleUtil.class);


    public static void setLocale(HttpSession session, LocaleType type) {
        session.setAttribute(ATTR_LOCALE, new TextMemoryContainer(type.getLocale().toString(),
                MemoryContainerType.LONG_LIVER));
    }

    public static void setLocale(RequestContent content, Locale locale) {
        content.setSessionAttribute(ATTR_LOCALE, new TextMemoryContainer(locale.toString(),
                MemoryContainerType.LONG_LIVER));
    }

    public static void changeLocaleIfNeeded(RequestContent content) {
        String locale = content.getRequestParameter(ATTR_LOCALE);

        if (locale != null) {
            setLocale(content, LocaleFactory.getLocale(locale));
        }
    }

    private static class LocaleFactory {
        public static Locale getLocale(String chosenLocale) {
            LocaleType type = LocaleType.getType(chosenLocale);
            return type.getLocale();
        }
    }

    public enum LocaleType {
        RU_RU(new Locale("ru", "RU")),
        EN_US(new Locale("en", "US"));

        private Locale locale;

        LocaleType(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        static LocaleType getType(String str) {
            try {
                if (str != null) {
                    return valueOf(str.toUpperCase());
                }
            } catch (IllegalArgumentException e) {
                LOG.error("Undefined locale: " + str, e);
            }

            return EN_US;
        }
    }
}