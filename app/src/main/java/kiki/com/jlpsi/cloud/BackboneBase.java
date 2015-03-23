package kiki.com.jlpsi.cloud;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;

import kiki.com.jlpsi.CloudEndpointUtils;
import singularity.com.jlpsi.entities.backboneendpoint.Backboneendpoint;
import singularity.com.jlpsi.entities.complaintendpoint.Complaintendpoint;
import singularity.com.jlpsi.entities.computerendpoint.Computerendpoint;
import singularity.com.jlpsi.entities.softwareendpoint.Softwareendpoint;
import singularity.com.jlpsi.entities.studentendpoint.Studentendpoint;
import singularity.com.jlpsi.entities.userendpoint.Userendpoint;

/**
 * Created by Frederick on 2/19/2015.
 */
public class BackboneBase {


    static Computerendpoint.Builder computerstEndpointBuilder = new Computerendpoint.Builder(
            AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest)
                        throws IOException {
                }
            });

    protected static Computerendpoint computersEndpoint = CloudEndpointUtils
            .updateBuilder(computerstEndpointBuilder).setApplicationName("kiki").build();

    static Softwareendpoint.Builder softwareEndpointBuilder = new Softwareendpoint.Builder(
            AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest)
                        throws IOException {
                }
            });

    protected static Softwareendpoint softwareEndpoint = CloudEndpointUtils
            .updateBuilder(softwareEndpointBuilder).setApplicationName("kiki").build();

    static Complaintendpoint.Builder complaintEndpointBuilder = new Complaintendpoint.Builder(
            AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest)
                        throws IOException {
                }
            });

    protected static Complaintendpoint complaintEndpoint = CloudEndpointUtils
            .updateBuilder(complaintEndpointBuilder).setApplicationName("kiki").build();


    static Studentendpoint.Builder pupilEndpointBuilder = new Studentendpoint.Builder(
            AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest)
                        throws IOException {
                }
            });

    protected static Studentendpoint pupilEndpoint = CloudEndpointUtils
            .updateBuilder(pupilEndpointBuilder).setApplicationName("kiki").build();

    static Userendpoint.Builder userEndpointBuilder = new Userendpoint.Builder(
            AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest)
                        throws IOException {
                }
            });

    protected static Userendpoint userEndpoint = CloudEndpointUtils
            .updateBuilder(userEndpointBuilder).setApplicationName("kiki").build();

    static Backboneendpoint.Builder endpointBuilder = new Backboneendpoint.Builder(
            AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest)
                        throws IOException {
                }
            });

    protected static Backboneendpoint endpoint = CloudEndpointUtils
            .updateBuilder(endpointBuilder).setApplicationName("kiki").build();
}
