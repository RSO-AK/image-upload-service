package si.fri.rso.rsoimageupload.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.rsoimageupload.lib.ImageMetadata;
import si.fri.rso.rsoimageupload.models.converters.ImageMetadataConverter;
import si.fri.rso.rsoimageupload.models.entities.ImageMetadataEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class ImageMetadataBean {

    private Logger log = Logger.getLogger(ImageMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    @Timed(name = "getImageMetadata_time")
    @Counted(name = "getImageMetadata_count")
    public List<ImageMetadata> getImageMetadata() {

        TypedQuery<ImageMetadataEntity> query = em.createNamedQuery(
                "ImageMetadataEntity.getAll", ImageMetadataEntity.class);

        List<ImageMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(ImageMetadataConverter::toDto).collect(Collectors.toList());

    }

    @Timed(name = "getImageMetadataFilter_time")
    @Counted(name = "getImageMetadataFilter_count")
    public List<ImageMetadata> getImageMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                                                         .build();

        return JPAUtils.queryEntities(em, ImageMetadataEntity.class, queryParameters).stream()
                       .map(ImageMetadataConverter::toDto).collect(Collectors.toList());
    }

    @Timed(name = "getImageMetadataId_time")
    @Counted(name = "getImageMetadataId_count")
    public ImageMetadata getImageMetadata(Integer id) {

        ImageMetadataEntity imageMetadataEntity = em.find(ImageMetadataEntity.class, id);

        if (imageMetadataEntity == null) {
            throw new NotFoundException();
        }

        ImageMetadata imageMetadata = ImageMetadataConverter.toDto(imageMetadataEntity);

        return imageMetadata;
    }

    @Timed(name = "createImageMetadata_time")
    @Counted(name = "createImageMetadata_count")
    public ImageMetadata createImageMetadata(ImageMetadata imageMetadata) {

        ImageMetadataEntity imageMetadataEntity = ImageMetadataConverter.toEntity(imageMetadata);

        try {
            beginTx();
            em.persist(imageMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (imageMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ImageMetadataConverter.toDto(imageMetadataEntity);
    }

    @Timed(name = "putImageMetadata_time")
    @Counted(name = "putImageMetadata_count")
    public ImageMetadata putImageMetadata(Integer id, ImageMetadata imageMetadata) {

        ImageMetadataEntity c = em.find(ImageMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        ImageMetadataEntity updatedImageMetadataEntity = ImageMetadataConverter.toEntity(imageMetadata);

        try {
            beginTx();
            updatedImageMetadataEntity.setId(c.getId());
            updatedImageMetadataEntity = em.merge(updatedImageMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ImageMetadataConverter.toDto(updatedImageMetadataEntity);
    }

    @Timed(name = "deleteImageMetadata_time")
    @Counted(name = "deleteImageMetadata_time_count")
    public boolean deleteImageMetadata(Integer id) {

        ImageMetadataEntity imageMetadata = em.find(ImageMetadataEntity.class, id);

        if (imageMetadata != null) {
            try {
                beginTx();
                em.remove(imageMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
