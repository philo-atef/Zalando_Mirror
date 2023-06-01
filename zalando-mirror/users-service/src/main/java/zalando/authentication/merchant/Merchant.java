package zalando.authentication.merchant;

import zalando.authentication.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table
public class Merchant {
    @Id
    private Long id;
    private String brandName;
    private String hotline;
    private LocalDate dateJoined;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private User userDetails2;

    public User getUserDetails2() {
        return userDetails2;
    }
    public void setUserDetails2(User userDetails) {
        this.userDetails2 = userDetails;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getHotline() {
        return hotline;
    }
    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }
    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }
}
