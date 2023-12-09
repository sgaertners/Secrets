package de.sgs.secrets.component;

import de.sgs.secrets.entities.Language;
import de.sgs.secrets.repositories.LanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component("messageSource")
public class DBMessageSource extends AbstractMessageSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBMessageSource.class);
    private final LanguageRepository languageRepository;
    private static final String DEFAULT_LOCALE_CODE = "de";

    @Value("${i18n.logging}")
    private boolean i18nLogging;

    public DBMessageSource(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public MessageFormat resolveCode(String key, Locale locale) {
        MessageFormat messageFormat = null;
        Language message = languageRepository.findByKeyAndLocale(key,locale.getLanguage());
        if (message == null) {
            message = languageRepository.findByKeyAndLocale(key,DEFAULT_LOCALE_CODE);
        }
        if (message != null) {
            messageFormat = new MessageFormat(message.getContent(), locale);
            String messageText = messageFormat.toPattern();
            if (i18nLogging) {
                LOGGER.info("MESSAGE: {}, {}, {}", locale, key, messageText);
            }
        }
        return messageFormat;
    }

}