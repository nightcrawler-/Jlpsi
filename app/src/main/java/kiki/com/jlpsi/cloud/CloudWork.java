package kiki.com.jlpsi.cloud;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import kiki.com.jlpsi.entities.Complaint;
import kiki.com.jlpsi.entities.Computer;
import kiki.com.jlpsi.entities.Student;
import kiki.com.jlpsi.entities.User;


/**
 * Created by Frederick on 2/5/2015.
 */
public class CloudWork {
    private static String USER_AGENT = "";
    private static String BASE_URL = "https://kiki-ke.appspot.com/content";

    public static User login(String password) throws Exception {
        Gson gson = new Gson();
        String result = sendPost("type=login&data=" + password, BASE_URL);
        return gson.fromJson(result, User.class);
    }

    public static List<Complaint> listComplaints() throws Exception {
        String result = sendPost("type=listComplaints", BASE_URL);
        List<Complaint> complaints = new ArrayList<Complaint>();
        Gson gson = new Gson();

        if (result != null && result.length() > 0) {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(result).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                JsonElement obj = array.get(i);
                Complaint complaint = gson.fromJson(obj, Complaint.class);
                complaints.add(complaint);
            }

            return complaints;
        }
        return null;
    }

    public static List<Computer> listComputers(Boolean assigned) throws Exception {


        String result = sendPost("type=listComputers&assigned=" + assigned, BASE_URL);
        List<Computer> computers = new ArrayList<>();
        Gson gson = new Gson();

        if (result != null && result.length() > 0) {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(result).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                JsonElement obj = array.get(i);
                Computer computer = gson.fromJson(obj, Computer.class);
                computers.add(computer);
            }

            return computers;
        }
        return null;
    }

    public static List<Student> listStudents() throws Exception {

        String result = sendPost("type=listStudents", BASE_URL);
        List<Student> students = new ArrayList<Student>();
        Gson gson = new Gson();

        if (result != null && result.length() > 0) {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(result).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                JsonElement obj = array.get(i);
                Student redeemable = gson.fromJson(obj, Student.class);
                students.add(redeemable);
            }

            return students;
        }
        return null;

    }


    public static boolean updateComputer(Computer comp) throws Exception {
        Gson gson = new Gson();
        String data = gson.toJson(comp);
        String result = sendPost("type=updateComputer&data=" + data, BASE_URL);
        if (result != null)
            return true;
        return false;
    }

    public static boolean postComplaint(Complaint complaint) throws Exception {
        Gson gson = new Gson();
        String data = gson.toJson(complaint);
        String result = sendPost("type=postComplaint&data=" + data, BASE_URL);

        if (result != null)
            return true;
        return false;
    }

    public static String updateStudent(Student student) throws Exception {
        Gson gson = new Gson();
        String data = gson.toJson(student);
        String result = sendPost("type=updateStudent&data=" + data, BASE_URL);

//        if (student.getPicUri() != null && student.getPicUri().length() > 0)
//            UploadHelper.uploadPic(student.getRegNo(), student.getRegNo(), new File(student.getPicUri()));

        return result;
    }


    /**
     * POST the JSON payload
     *
     * @param data
     * @return a string representation of the contacts uploaded - to update
     * local cache
     * @throws Exception
     */

    public static String sendPost(String data, String url) throws Exception {
        Log.i("CloudWork", "fetching - " + url + " data - " + data);
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        Log.i("Moth", "responseCode - " + responseCode);

        Log.i("Moth", "response - " + response);
        if (responseCode == 200)
            return response.toString();
        else
            return null;

    }
}
