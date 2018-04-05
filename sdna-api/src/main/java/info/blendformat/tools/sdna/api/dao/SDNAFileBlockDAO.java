package info.blendformat.tools.sdna.api.dao;

import info.blendformat.tools.sdna.api.model.SDNAFile;
import info.blendformat.tools.sdna.api.model.SDNAFileBlock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class SDNAFileBlockDAO {

    @Inject
    private EntityManager em;

    public List<SDNAFileBlock> findByFile(SDNAFile file) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAFileBlock> criteria = cb.createQuery(SDNAFileBlock.class);
        Root<SDNAFileBlock> root = criteria.from(SDNAFileBlock.class);
        criteria.select(root)
                .where(cb.equal(root.get("file"), file))
                .orderBy(cb.asc(root.get("address")));
        return em.createQuery(criteria).getResultList();
    }

    public SDNAFileBlock findById(Long id) {
        return em.find(SDNAFileBlock.class, id);
    }

    public SDNAFileBlock findByFileAndAddress(SDNAFile file,
                                              Long address) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAFileBlock> criteria = cb.createQuery(SDNAFileBlock.class);
        Root<SDNAFileBlock> root = criteria.from(SDNAFileBlock.class);
        criteria.select(root)
                .where(cb.and(
                        cb.equal(root.get("file"), file),
                        cb.equal(root.get("address"), address)));
        List<SDNAFileBlock> resultList = em.createQuery(criteria).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public SDNAFileBlock persist(SDNAFileBlock block) {
        SDNAFileBlock duplicate = findByFileAndAddress(
                block.getFile(),
                block.getAddress());
        if (null != duplicate) {
            throw new IllegalArgumentException("The name is already taken.");
        }
        em.persist(block);
        return findByFileAndAddress(
                block.getFile(),
                block.getAddress());
    }

    public SDNAFileBlock merge(SDNAFileBlock block) {
        return em.merge(block);
    }
}
