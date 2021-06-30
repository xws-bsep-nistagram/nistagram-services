package rs.ac.uns.ftn.nistagram.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.auth.domain.ApiToken;

import java.util.Optional;


@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {

    @Modifying
    @Query(value = "delete from ApiToken API where API.packageName = :packageName")
    void deleteByPackageName(@Param("packageName") String packageName);

    @Query(value = "select API from ApiToken API where API.packageName = :packageName")
    Optional<ApiToken> findByPackageName(@Param("packageName") String packageName);

    @Query(value = "select API from ApiToken API where API.agent = :agent")
    Optional<ApiToken> getByAgent(@Param("agent") String agent);
}
