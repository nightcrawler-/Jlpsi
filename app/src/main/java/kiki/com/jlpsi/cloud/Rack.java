package kiki.com.jlpsi.cloud;

import java.util.HashMap;
import java.util.List;

import kiki.com.jlpsi.entities.Complaint;
import kiki.com.jlpsi.entities.Computer;
import kiki.com.jlpsi.entities.Student;

/**
 * Created by Frederick on 2/7/2015.
 */
public class Rack {

    private HashMap<String, List<Student>> students;
    private HashMap<String, List<Complaint>> complaints;
    private HashMap<String, List<Computer>> unassignedComputers;
    private HashMap<String, List<Computer>> computers;

    private static Rack rack = null;

    public static Rack getInstance() {
        if (rack == null)
            rack = new Rack();
        return rack;
    }


    public HashMap<String, List<Student>> getStudents() {
        return students;
    }

    public void setStudents(HashMap<String, List<Student>> students) {
        this.students = students;
    }

    public HashMap<String, List<Complaint>> getComplaints() {
        return complaints;
    }

    public void setComplaints(HashMap<String, List<Complaint>> complaints) {
        this.complaints = complaints;
    }

    public HashMap<String, List<Computer>> getUnassignedComputers() {
        return unassignedComputers;
    }

    public void setUnassignedComputers(HashMap<String, List<Computer>> unassignedComputers) {
        this.unassignedComputers = unassignedComputers;
    }

    public HashMap<String, List<Computer>> getComputers() {
        return computers;
    }

    public void setComputers(HashMap<String, List<Computer>> computers) {
        this.computers = computers;
    }
}
