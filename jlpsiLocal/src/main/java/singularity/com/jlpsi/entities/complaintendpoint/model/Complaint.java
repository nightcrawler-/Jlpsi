/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-01 at 17:57:57 UTC 
 * Modify at your own risk.
 */

package singularity.com.jlpsi.entities.complaintendpoint.model;

/**
 * Model definition for Complaint.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the complaintendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Complaint extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String compSerial;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String loggedBy;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean responded;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String respondedBy;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String response;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String schoolId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String studentName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long time;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long timeResponded;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCompSerial() {
    return compSerial;
  }

  /**
   * @param compSerial compSerial or {@code null} for none
   */
  public Complaint setCompSerial(java.lang.String compSerial) {
    this.compSerial = compSerial;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Complaint setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Complaint setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLoggedBy() {
    return loggedBy;
  }

  /**
   * @param loggedBy loggedBy or {@code null} for none
   */
  public Complaint setLoggedBy(java.lang.String loggedBy) {
    this.loggedBy = loggedBy;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getResponded() {
    return responded;
  }

  /**
   * @param responded responded or {@code null} for none
   */
  public Complaint setResponded(java.lang.Boolean responded) {
    this.responded = responded;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRespondedBy() {
    return respondedBy;
  }

  /**
   * @param respondedBy respondedBy or {@code null} for none
   */
  public Complaint setRespondedBy(java.lang.String respondedBy) {
    this.respondedBy = respondedBy;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getResponse() {
    return response;
  }

  /**
   * @param response response or {@code null} for none
   */
  public Complaint setResponse(java.lang.String response) {
    this.response = response;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSchoolId() {
    return schoolId;
  }

  /**
   * @param schoolId schoolId or {@code null} for none
   */
  public Complaint setSchoolId(java.lang.String schoolId) {
    this.schoolId = schoolId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * @param status status or {@code null} for none
   */
  public Complaint setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStudentName() {
    return studentName;
  }

  /**
   * @param studentName studentName or {@code null} for none
   */
  public Complaint setStudentName(java.lang.String studentName) {
    this.studentName = studentName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTime() {
    return time;
  }

  /**
   * @param time time or {@code null} for none
   */
  public Complaint setTime(java.lang.Long time) {
    this.time = time;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTimeResponded() {
    return timeResponded;
  }

  /**
   * @param timeResponded timeResponded or {@code null} for none
   */
  public Complaint setTimeResponded(java.lang.Long timeResponded) {
    this.timeResponded = timeResponded;
    return this;
  }

  @Override
  public Complaint set(String fieldName, Object value) {
    return (Complaint) super.set(fieldName, value);
  }

  @Override
  public Complaint clone() {
    return (Complaint) super.clone();
  }

}
