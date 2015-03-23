package kiki.com.jlpsi.cloud;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handle uploading images and audio, **compile library to make it easier to do
 * uploads.
 *
 * @author Frederick
 */
@SuppressWarnings("deprecation")
public class UploadHelper {

    public static final String TYPE_IMAGE = "image";

    private static String requestUploadUrl(String id) throws ClientProtocolException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpConnectionParams
                .setConnectionTimeout(httpclient.getParams(), 10000); // Timeout

        HttpGet httpGet = new HttpGet(
                "http://kiki-ke.appspot.com/getUploadUrl?type=image&id="
                        + id);

        HttpResponse response = httpclient.execute(httpGet);
        String content = EntityUtils.toString(response.getEntity());

        Log.i("UPLOAD", "uploadurl - " + content);
        return content;
    }


    public static String uploadPic(String id, String fileName, File pic)
            throws ClientProtocolException, IOException {
        if (!pic.isFile() || !pic.exists())
            return null;
        return doUpload(id, fileName, getBytes(pic));

    }

    /**
     * Does the actual uploading, requires the byte array for whatever data
     *
     * @param id
     * @param fileName
     * @param data
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws java.io.IOException
     */
    private static String doUpload(String id, String fileName,
                                   byte[] data)
            throws ClientProtocolException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        String uploadUrl = requestUploadUrl(id);
        if (!uploadUrl.startsWith("http"))
            return null;

        HttpPost httppost = new HttpPost(uploadUrl);
        ContentBody mimePart = new ByteArrayBody(data, fileName);
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("blobKey", mimePart);

        httppost.setEntity(reqEntity);
        HttpResponse response = httpclient.execute(httppost);
        String resp = EntityUtils.toString(response.getEntity());
        Log.i("UploadHelper", "Resp - " + resp);
        return resp;

    }

    private static byte[] getBytes(File audio) throws IOException {

        InputStream is = new FileInputStream(audio);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024 * 8];
        int bytesRead = 0;

        while ((bytesRead = is.read(b)) != -1) {
            bos.write(b, 0, bytesRead);

        }
        is.close();
        return bos.toByteArray();
    }
}
