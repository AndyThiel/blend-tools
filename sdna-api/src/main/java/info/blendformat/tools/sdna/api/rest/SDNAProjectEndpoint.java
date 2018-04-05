package info.blendformat.tools.sdna.api.rest;

import info.blendformat.tools.sdna.api.dao.SDNAProjectDAO;
import info.blendformat.tools.sdna.api.model.SDNAProject;
import org.slf4j.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/projects")
@RequestScoped
@Stateful
public class SDNAProjectEndpoint {

    @Inject
    private Logger log;

    @Inject
    private SDNAProjectDAO projectDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SDNAProject> listAllMembers() {
        List<SDNAProject> resultList = new ArrayList<>();
        resultList.addAll(projectDAO.findAll());
        log.info("Found: " + resultList.size() + " record(s)");
        return resultList;
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public SDNAProject lookupProject(@PathParam("id") long id) {
        SDNAProject project = projectDAO.findById(id);
        return project;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SDNAProject create(SDNAProject project) {
        SDNAProject persist = projectDAO.persist(project);
        return persist;
    }
}
