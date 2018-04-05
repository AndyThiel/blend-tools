package info.blendformat.tools.sdna.api.dao;

import info.blendformat.tools.sdna.api.model.SDNAProject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class SDNAProjectDAO {

    @Inject
    private EntityManager em;

    public List<SDNAProject> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAProject> criteria = cb.createQuery(SDNAProject.class);
        Root<SDNAProject> root = criteria.from(SDNAProject.class);
        criteria.select(root).orderBy(cb.asc(root.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public SDNAProject findById(Long id) {
        return em.find(SDNAProject.class, id);
    }

    public SDNAProject findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SDNAProject> criteria = cb.createQuery(SDNAProject.class);
        Root<SDNAProject> root = criteria.from(SDNAProject.class);
        criteria.select(root).where(cb.equal(root.get("name"), name));
        List<SDNAProject> resultList = em.createQuery(criteria).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public SDNAProject persist(SDNAProject project) {
        SDNAProject duplicate = findByName(project.getName());
        if (null != duplicate) {
            throw new IllegalArgumentException("The name is already taken.");
        }
        em.persist(project);
        return findByName(project.getName());
    }

    public SDNAProject merge(SDNAProject project) {
        return em.merge(project);
    }
}
