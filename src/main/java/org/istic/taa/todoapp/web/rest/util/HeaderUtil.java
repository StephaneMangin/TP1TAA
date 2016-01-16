package org.istic.taa.todoapp.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-todoappApp-alert", message);
        headers.add("X-todoappApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityForbidden(String entityName, String param) {
        return createAlert("todoappApp." + entityName + ".forbidden", param);
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("todoappApp." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("todoappApp." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("todoappApp." + entityName + ".deleted", param);
    }

}
