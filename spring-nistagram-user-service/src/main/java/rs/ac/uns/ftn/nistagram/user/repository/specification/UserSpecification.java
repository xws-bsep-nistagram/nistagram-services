package rs.ac.uns.ftn.nistagram.user.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserSpecification implements Specification<User> {

    Map<String, String> queryMap;

    public UserSpecification(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = getPredicatesFromQueryParams(root, criteriaBuilder).toArray(new Predicate[0]);
        criteriaQuery.distinct(true);
        return criteriaBuilder.and(predicates);
    }

    private List<Predicate> getPredicatesFromQueryParams(Root<User> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        Join<User, PersonalData> joinPersonalData = root.join("personalData");

        if (queryMap.containsKey("minAge")) {
            String minAge = queryMap.get("minAge");
            LocalDate required = today.minusYears(Integer.parseInt(minAge));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(joinPersonalData.get("dateOfBirth"), required));
        }
        if (queryMap.containsKey("maxAge")) {
            String minAge = queryMap.get("maxAge");
            LocalDate required = today.minusYears(Integer.parseInt(minAge));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(joinPersonalData.get("dateOfBirth"), required));
        }
        if (queryMap.containsKey("gender")) {
            String gender = queryMap.get("gender");
            predicates.add(criteriaBuilder.equal(joinPersonalData.get("gender"), Gender.valueOf(gender)));
        }
        return predicates;
    }

}
