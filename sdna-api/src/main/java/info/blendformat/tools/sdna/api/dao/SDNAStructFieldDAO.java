package info.blendformat.tools.sdna.api.dao;

import info.blendformat.tools.sdna.api.model.SDNACatalogType;
import info.blendformat.tools.sdna.api.model.SDNAStructField;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class SDNAStructFieldDAO implements Serializable {

    @Inject
    private EntityManager em;

    public List<SDNAStructField> findByFile(SDNACatalogType type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAStructField> criteria = cb.createQuery(SDNAStructField.class);
        Root<SDNAStructField> root = criteria.from(SDNAStructField.class);
        criteria.select(root)
                .where(cb.equal(root.get("type"), type))
                .orderBy(cb.asc(root.get("index")));
        return em.createQuery(criteria).getResultList();
    }

    public SDNAStructField findById(Long id) {
        return em.find(SDNAStructField.class, id);
    }

    public SDNAStructField findByTypeAndName(SDNACatalogType type,
                                             String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAStructField> criteria = cb.createQuery(SDNAStructField.class);
        Root<SDNAStructField> root = criteria.from(SDNAStructField.class);
        criteria.select(root)
                .where(cb.and(
                        cb.equal(root.get("type"), type),
                        cb.equal(root.get("name"), name)));
        List<SDNAStructField> resultList = em.createQuery(criteria).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public SDNAStructField persist(SDNAStructField field) {
        SDNAStructField duplicate = findByTypeAndName(
                field.getType(),
                field.getName());
        if (null != duplicate) {
            throw new IllegalArgumentException("The name is already taken.");
        }
        em.persist(field);
        return findByTypeAndName(
                field.getType(),
                field.getName());
    }

    public SDNAStructField merge(SDNAStructField type) {
        return em.merge(type);
    }
}
