package kiki.com.jlpsi.entities;

/**
 * Created by Frederick on 2/6/2015.
 */
public class Complaint {
    private String id;
    private String description, loggedBy, compSerial, studentName, status, response, respondedBy;
    private boolean responded;
    private long time, timeResponded;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
    }

    public String getCompSerial() {
        return compSerial;
    }

    public void setCompSerial(String compSerial) {
        this.compSerial = compSerial;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRespondedBy() {
        return respondedBy;
    }

    public void setRespondedBy(String respondedBy) {
        this.respondedBy = respondedBy;
    }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimeResponded() {
        return timeResponded;
    }

    public void setTimeResponded(long timeResponded) {
        this.timeResponded = timeResponded;
    }
}
