package info.blendformat.tools.sdna.api.dao;

import info.blendformat.tools.sdna.api.model.SDNAFile;
import info.blendformat.tools.sdna.api.model.SDNAProject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class SDNAFileDAO {

    @Inject
    private EntityManager em;

    public List<SDNAFile> findByProject(SDNAProject project) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAFile> criteria = cb.createQuery(SDNAFile.class);
        Root<SDNAFile> root = criteria.from(SDNAFile.class);
        criteria.select(root)
                .where(cb.equal(root.get("project"), project))
                .orderBy(cb.asc(root.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public SDNAFile findById(Long id) {
        return em.find(SDNAFile.class, id);
    }

    public SDNAFile findByProjectAndName(SDNAProject project,
                                         String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAFile> criteria = cb.createQuery(SDNAFile.class);
        Root<SDNAFile> root = criteria.from(SDNAFile.class);
        criteria.select(root)
                .where(cb.and(
                        cb.equal(root.get("project"), project),
                        cb.equal(root.get("name"), name)));
        List<SDNAFile> resultList = em.createQuery(criteria).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public SDNAFile persist(SDNAFile file) {
        SDNAFile duplicate = findByProjectAndName(
                file.getProject(),
                file.getName());
        if (null != duplicate) {
            throw new IllegalArgumentException("The name is already taken.");
        }
        em.persist(file);
        return findByProjectAndName(
                file.getProject(),
                file.getName());
    }

    public SDNAFile merge(SDNAFile file) {
        return em.merge(file);
    }
}
