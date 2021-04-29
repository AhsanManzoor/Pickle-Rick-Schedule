package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;

public class Project {
}
package com.picklerick.schedule.rest.api.model;

        import javax.persistence.*;

@Entity
@Table(name = "project")
public class Project {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String title;
    private String description;
    private Long created_by;

    /**
     * Class constructor
     * @author Yomiyou
     */
//    public Project() {}

    /**
     * Class constructor with project attributes
     * @author Yomiyou
     */
    public Project(String title, String description, Long created_by) {
        this.title = title;
        this.description = description;
        this.created_by = created_by;
    }

    /**
     * Generated Get method for project id
     * @author Yomiyou
     * */
    public Long getId() {
        return id;
    }

    /**
     * Generated Get method for title
     * @author Yomiyou
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Generated Set method for title
     * @author Yomiyou
     *
     * @param title the title of the Project
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Generated Get method for description
     * @author Yomiyou
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Generated Set method for description
     * @author Yomiyou
     *
     * @param description Description of Tasks in the respective project
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Generated Get method for created_by
     * @author Yomiyou
     * */
    public Long getCreated_by() {
        return created_by;
    }

    /**
     * Generated Set method for created_by
     * @author Yomiyou
     *
     * @param created_by which sets by linking the user id
     * */
    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }
}
