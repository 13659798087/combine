package lzgene.newscreening.entiry;

public class ConfigReport {

    private String id;
    private String project_name;
    private String report_name;
    private String report_url;
    private Integer flag;
    private Integer  type;
    private String authorizeName;

    public String getAuthorizeName() {
        return authorizeName;
    }

    public void setAuthorizeName(String authorizeName) {
        this.authorizeName = authorizeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReport_url() {
        return report_url;
    }

    public void setReport_url(String report_url) {
        this.report_url = report_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
