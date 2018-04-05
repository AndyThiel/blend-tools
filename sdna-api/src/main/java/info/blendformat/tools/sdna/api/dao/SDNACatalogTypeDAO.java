package info.blendformat.tools.sdna.api.dao;

import info.blendformat.tools.sdna.api.model.SDNACatalogType;
import info.blendformat.tools.sdna.api.model.SDNAFile;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class SDNACatalogTypeDAO {

    @Inject
    private EntityManager em;

    public List<SDNACatalogType> findByFile(SDNAFile file) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNACatalogType> criteria = cb.createQuery(SDNACatalogType.class);
        Root<SDNACatalogType> root = criteria.from(SDNACatalogType.class);
        criteria.select(root)
                .where(cb.equal(root.get("file"), file))
                .orderBy(cb.asc(root.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public SDNACatalogType findById(Long id) {
        return em.find(SDNACatalogType.class, id);
    }

    public SDNACatalogType findByFileAndName(SDNAFile file,
                                             String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNACatalogType> criteria = cb.createQuery(SDNACatalogType.class);
        Root<SDNACatalogType> root = criteria.from(SDNACatalogType.class);
        criteria.select(root)
                .where(cb.and(
                        cb.equal(root.get("file"), file),
                        cb.equal(root.get("name"), name)));
        List<SDNACatalogType> resultList = em.createQuery(criteria).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public SDNACatalogType persist(SDNACatalogType type) {
        SDNACatalogType duplicate = findByFileAndName(
                type.getFile(),
                type.getName());
        if (null != duplicate) {
            throw new IllegalArgumentException("The name is already taken.");
        }
        em.persist(type);
        return findByFileAndName(
                type.getFile(),
                type.getName());
    }

    public SDNACatalogType merge(SDNACatalogType type) {
        return em.merge(type);
    }
}
