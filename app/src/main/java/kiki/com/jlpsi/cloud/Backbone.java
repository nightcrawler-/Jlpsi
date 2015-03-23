package kiki.com.jlpsi.cloud;

import android.content.Context;

import java.io.IOException;

import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.Computer;
import singularity.com.jlpsi.entities.backboneendpoint.model.Stats;
import singularity.com.jlpsi.entities.backboneendpoint.model.User;
import singularity.com.jlpsi.entities.complaintendpoint.model.Complaint;
import singularity.com.jlpsi.entities.studentendpoint.model.Student;

/**
 * Created by Frederick on 2/19/2015.
 */
public class Backbone extends BackboneBase {
    private static Backbone backbone = null;
    private Context context;

    public Backbone(Context context) {
        this.context = context;
    }

    public static Backbone getInstance(Context context) {
        if (backbone == null)
            backbone = new Backbone(context);
        return backbone;
    }


    public singularity.com.jlpsi.entities.userendpoint.model.User changePassword(singularity.com.jlpsi.entities.userendpoint.model.User user) throws IOException {
        return userEndpoint.updateUser(user).execute();

    }

    public User login(User user) throws IOException {
        return endpoint.login(user).execute();
    }

    public Void deletePupil(String id) throws IOException {
        return pupilEndpoint.removeStudent(id).execute();

    }

    public Student updatePupil(Student student) throws IOException {
        return pupilEndpoint.updateStudent(student).execute();

    }

    public java.util.List<singularity.com.jlpsi.entities.backboneendpoint.model.Computer> listComputers(String schoolId, Boolean unAssigned) throws IOException {
        return endpoint.listComputer().setSchoolId(schoolId).setAssigned(unAssigned).execute().getItems();

    }

    public java.util.List<singularity.com.jlpsi.entities.backboneendpoint.model.Student> listPupils(String schoolId) throws IOException {
        return endpoint.listStudent().setSchoolId(schoolId).execute().getItems();

    }

    public java.util.List<singularity.com.jlpsi.entities.softwareendpoint.model.Software> listSoftware() throws IOException {
        return softwareEndpoint.listSoftware().execute().getItems();
    }

    public Student insertStudent(Student student) throws IOException {
        student.setSchoolId(Utils.getCachedUser(context).getId());
        singularity.com.jlpsi.entities.computerendpoint.model.Computer comp = computersEndpoint.getComputer(student.getCompSerial()).execute();
        comp.setAssigned(true);
        comp.setAssignedTo(student.getName() + "," + student.getRegNo());
        try {
            Student res = pupilEndpoint.insertStudent(student).execute();
            computersEndpoint.updateComputer(comp).execute();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Stats updateStats(int type){
        return backbone.updateStats(type);
    }

    public java.util.List<singularity.com.jlpsi.entities.backboneendpoint.model.Complaint> listComlaints(String schoolId) throws IOException {
        return endpoint.listComplaint().setSchoolId(schoolId).execute().getItems();

    }

    public Complaint insertComplaint(Complaint complaint) throws IOException {
        complaint.setSchoolId(Utils.getCachedUser(context).getId());
        return complaintEndpoint.insertComplaint(complaint).execute();

    }

    public Computer insertComputer(Computer comp) throws IOException {
        comp.setSchoolId(Utils.getCachedUser(context).getId());
        return endpoint.insertComputer(comp).execute();
    }

}
