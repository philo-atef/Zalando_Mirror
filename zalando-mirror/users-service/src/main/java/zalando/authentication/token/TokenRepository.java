package zalando.authentication.token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :userID and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokensByUser(Long userID);
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :userID\s
      """)
    List<Token> findAllTokensByUserID(Long userID);
    Optional<Token> findByToken(String token);
}
