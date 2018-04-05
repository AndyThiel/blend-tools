package info.blendformat.tools.sdna.api.rest;

import info.blendformat.tools.sdna.api.dao.SDNAFileDAO;
import info.blendformat.tools.sdna.api.dao.SDNAProjectDAO;
import info.blendformat.tools.sdna.api.model.SDNAFile;
import info.blendformat.tools.sdna.api.model.SDNAProject;
import org.slf4j.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/project/{projectId:[0-9][0-9]*}/files")
@RequestScoped
@Stateful
public class SDNAFileEndpoint {

    @Inject
    private Logger log;

    @Inject
    private SDNAProjectDAO projectDAO;

    @Inject
    private SDNAFileDAO fileDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SDNAFile> listAllMembers(
            @PathParam("projectId") long projectId) {

        List<SDNAFile> resultList = new ArrayList<>();
        SDNAProject project = projectDAO.findById(projectId);
        resultList.addAll(fileDAO.findByProject(project));
        log.info("Found: " + resultList.size() + " record(s)");
        return resultList;
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public SDNAFile lookupFile(@PathParam("id") long id) {
        SDNAFile file = fileDAO.findById(id);
        return file;
    }
}
