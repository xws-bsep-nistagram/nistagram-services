package rs.ac.uns.ftn.nistagram.campaign.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.Gender;

import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TargetedGroup {

    public Integer minAge;
    public Integer maxAge;
    public Gender gender;

    public Map<String, String> toQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        if (minAge != null) {
            queryMap.put("minAge", minAge.toString());
        }
        if (maxAge != null) {
            queryMap.put("maxAge", maxAge.toString());
        }
        if (gender != null) {
            queryMap.put("gender", gender.toString());
        }
        return queryMap;
    }

    @Override
    public String toString() {
        return "TargetedGroup{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", gender=" + gender +
                '}';
    }
}
