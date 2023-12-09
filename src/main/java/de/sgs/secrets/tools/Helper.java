package de.sgs.secrets.tools;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

public class Helper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Helper.class);

    public static String loadTemplate(String templateName) {
        String result = "";
        try (InputStream resource = new ClassPathResource("templates/" + templateName + ".html").getInputStream()) {
            try ( BufferedReader reader = new BufferedReader( new InputStreamReader(resource)) ) {
                result = reader.lines().collect(Collectors.joining("\n"));
            } catch(Exception ex) {
                LOGGER.error("Could not find template resource!", ex);
            }
        } catch(Exception ex) {
            LOGGER.error("Could not load template resource!", ex);
        }
        return result;
    }


    public static String loadFragment(String templateName) {
        String result = "";
        try (InputStream resource = new ClassPathResource("templates/fragments/" + templateName + ".xml").getInputStream()) {
            try ( BufferedReader reader = new BufferedReader( new InputStreamReader(resource)) ) {
                result = reader.lines().collect(Collectors.joining("\n"));
            } catch(Exception ex) {
                LOGGER.error("Could not find fragment resource!", ex);
            }
        } catch(Exception ex) {
            LOGGER.error("Could not load template resource!", ex);
        }
        return result;
    }

    public static String loadResourceTxt(String templateName) {
        String result = "";
        try (InputStream resource = new ClassPathResource(templateName).getInputStream()) {
            try ( BufferedReader reader = new BufferedReader( new InputStreamReader(resource)) ) {
                result = reader.lines().collect(Collectors.joining("\n"));
            } catch(Exception ex) {
                LOGGER.error("Could not find resource!", ex);
            }
        } catch(Exception ex) {
            LOGGER.error("Could not load resource!", ex);
        }
        return result;
    }

    public static InputStream loadYaml(String yamlName) {
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("./" + yamlName + ".yml").getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputStream;
    }

    public static byte[] loadResource(String templateName) {
        byte[] result = {};
        try (InputStream resource = new ClassPathResource(templateName).getInputStream()) {
            result = resource.readAllBytes();
        } catch(Exception ex) {
            LOGGER.error("Could not load resource {}!",templateName, ex);
        }
        return result;
    }

    public static HashMap<String, String> findVariables(String template) {
        HashMap<String, String> hashMap = new HashMap<>();
        String regex = "\\$\\{(.*?)\\}";
        Pattern p = compile(regex);
        Matcher m = p.matcher(template);
        while(m.find()) {
            hashMap.put(m.group(1), "");
        }

        return hashMap;
    }

    public static HashMap<String, String> findMessageKeys(String template) {
        HashMap<String, String> hashMap = new HashMap<>();
        String regex = "\\#\\{(.*?)\\}";
        Pattern p = compile(regex);
        Matcher m = p.matcher(template);
        while(m.find()) {
            hashMap.put(m.group(1), "");
        }

        return hashMap;
    }

    public static String resolve(String template, HashMap<String, String> map) {
        StrSubstitutor sub = new StrSubstitutor(map);
        return sub.replace(template);
    }

}
