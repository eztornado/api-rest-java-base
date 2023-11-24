package com.eztornado.tornadocorebase.repositories.specifications;

import com.eztornado.tornadocorebase.filters.UserFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import com.eztornado.tornadocorebase.models.User;

public class UserSpecification extends SpecificationBase implements Specification<User> {

    private final UserFilter userFilter;
    
    @Value("${tornadocore.softDelete}")
    private boolean softDelete;
    public UserSpecification(UserFilter userFilterDto) {
        this.userFilter = userFilterDto;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        //if(this.softDelete) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNull(root.get("deleted_at")));
        //}

        if (userFilter.getUsername() != null) {
            String likePattern = this.getLikePattern(userFilter.getUsername());
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("username"), likePattern));
        }

        if (userFilter.getEmail() != null) {
            String likePattern = getLikePattern(userFilter.getEmail());
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("email"), likePattern));
        }

        if (userFilter.isActive()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("active"), userFilter.isActive()));
        }

        return predicate;
    }

    @Override
    public Specification<User> and(Specification<User> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<User> or(Specification<User> other) {
        return Specification.super.or(other);
    }
}