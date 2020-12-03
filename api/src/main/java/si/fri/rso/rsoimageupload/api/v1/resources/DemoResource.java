package si.fri.rso.rsoimageupload.api.v1.resources;

import si.fri.rso.rsoimageupload.lib.Info;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static java.util.Arrays.asList;
import java.util.logging.Logger;

import static com.amazonaws.services.elasticbeanstalk.model.ConfigurationOptionValueType.List;


@ApplicationScoped
@Path("/demo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @GET
    @Path("info")
    public Response getInfo() {
        Info info = new Info();
        info.setClani(asList("ak9605"));
        info.setOpis_projekta("Projekt je headless CMS preko katerega definiramo podatkovne modele oz. entitete in oblekte za le te. Vsebine lahko dodajamo in do njih dostopamo preko API-ja ali UI.");
        info.setMikrostoritve(asList("http://52.151.205.45:8080/v1/images"));
        info.setGithub(asList("https://github.com/RSO-AK/image-upload-service"));
        info.setTravis(asList("https://travis-ci.com/github/RSO-AK/image-upload-service"));
        info.setDockerhub(asList("https://hub.docker.com/r/armkom/rso-image-upload-api"));

        return Response.status(Response.Status.OK).entity(info).build();
    }
}