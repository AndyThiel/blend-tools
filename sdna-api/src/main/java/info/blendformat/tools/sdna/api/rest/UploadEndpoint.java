package info.blendformat.tools.sdna.api.rest;

import info.blendformat.tools.sdna.api.dao.SDNAProjectDAO;
import info.blendformat.tools.sdna.api.model.SDNAProject;
import info.blendformat.tools.sdna.api.model.UploadInfo;
import info.blendformat.tools.sdna.api.service.FileUploadService;
import info.blendformat.tools.sdna.reader.ReaderConfig;
import info.blendformat.tools.sdna.reader.ReaderConfigDefault;
import info.blendformat.tools.sdna.reader.SDNAFileStreamReader;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/upload/projects/{projectId:[0-9]+}")
@RequestScoped
@Stateful
public class UploadEndpoint {

    @Inject
    private Logger log;

    @Inject
    private SDNAProjectDAO projectDAO;

    @Inject
    private FileUploadService uploadService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public UploadInfo uploadFile(
            @PathParam("projectId") Long projectId,
            MultipartFormDataInput uploadFormData) {

        log.info("Uploading file to project with id: " + projectId);
        SDNAProject project = projectDAO.findById(projectId);

        Map<String, List<InputPart>> uploadForm = uploadFormData.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("sdnaFile");

        if (null == project) {
            log.warn("No project found for id");
            throw new NotFoundException("Project not found");
        } else if (null == inputParts) {
            log.warn("No valid file in form data, sdnaFile input parts are null");
            throw new BadRequestException("No file data provided");
        }

        UploadInfo info = null;
        log.info("The project is: " + project.toString());

        ReaderConfig config = new ReaderConfigDefault();
        SDNAFileStreamReader reader = new SDNAFileStreamReader();
        String filename;
        for (InputPart part : inputParts) {

            MultivaluedMap<String, String> header = part.getHeaders();
            filename = extractFilename(header);
            log.info("Reading input part, filename is: " + filename);

            uploadService.setFilename(filename);
            uploadService.setProject(project);

            try {
                BufferedInputStream inputStream = new BufferedInputStream(
                        part.getBody(InputStream.class, null));

                reader.addSubscriber(uploadService);
                reader.readFile(config, inputStream);
                inputStream.close();
                reader.removeSubscriber(uploadService);

                info = uploadService.getUploadInfo();

            } catch (IOException e) {
                info = new UploadInfo();
                info.setMessage(e.getMessage());
            }
        }

        return info;
    }

    private String extractFilename(MultivaluedMap<String, String> header) {
        String contentDisposition = header.getFirst("Content-Disposition");

        Matcher matcher = Pattern.compile("filename=\"(^[\"]*)\"")
                .matcher(contentDisposition);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "sdna-file.blend";
    }
}
