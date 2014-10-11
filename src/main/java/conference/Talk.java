package conference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@Entity
public class Talk {
    @Id
    @GeneratedValue
    private Long talkId;

    private Date when;

    private String title;

    public Talk() {
    }

    public Talk(String title, Date date) {
        this.title = title;
        this.when = date;
    }

    @Override
    public String toString() {
        return title;
    }

    public Date getWhen() {
        return when;
    }
}
