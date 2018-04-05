package info.blendformat.tools.sdna.api.rest;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.regex.Pattern;

@Provider
public class JaxRsCorsFilter implements ContainerResponseFilter {

    private static final Pattern PATTERN_VALIDURL = Pattern.compile(
            "^http(s)?://([^.]*\\.)?blendformat\\.info(:[0-9]*)?/sdna-api/rest/.*$");

    private static final Pattern PATTERN_VALIDORIGIN = Pattern.compile(
            "^http(s)?://([^.]*\\.)?blendformat\\.info(:[0-9]*)?$");

    public static final String HEADERKEY_ORIGIN = "Origin";

    public static final String HEADERKEY_ALLOWORIGIN = "Access-Control-Allow-Origin";
    public static final String HEADERKEY_ALLOWCREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String HEADERKEY_ALLOWHEADERS = "Access-Control-Allow-Headers";
    public static final String HEADERKEY_ALLOWMETHODS = "Access-Control-Allow-Methods";

    public static final String HEADERVALUE_FALSE = "false";
    public static final String HEADERVALUE_ALLOWHEADERS = "origin, content-type, accept, authorization";
    public static final String HEADERVALUE_ALLOWMETHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD";

    @Inject
    private Logger log;

    public void filter(ContainerRequestContext req, ContainerResponseContext res) {

        UriInfo uriInfo = req.getUriInfo();
        String url = uriInfo.getAbsolutePath().toASCIIString();
        if (PATTERN_VALIDURL.matcher(url).matches()) {
            log.debug("Handling URL: " + url);
        } else {
            log.debug("Rejected URL: " + url);
            return;
        }

        String origin = req.getHeaderString(HEADERKEY_ORIGIN);
        if ((null != origin) &&
                PATTERN_VALIDORIGIN.matcher(origin).matches()) {
            log.debug("Accepted Origin: " + url);
        } else {
            log.debug("No Origin set, assuming request is not cross origin.");
            return;
        }

        res.getHeaders().add(HEADERKEY_ALLOWORIGIN, origin);
        res.getHeaders().add(HEADERKEY_ALLOWCREDENTIALS, HEADERVALUE_FALSE);
        res.getHeaders().add(HEADERKEY_ALLOWHEADERS, HEADERVALUE_ALLOWHEADERS);
        res.getHeaders().add(HEADERKEY_ALLOWMETHODS, HEADERVALUE_ALLOWMETHODS);
    }
}
