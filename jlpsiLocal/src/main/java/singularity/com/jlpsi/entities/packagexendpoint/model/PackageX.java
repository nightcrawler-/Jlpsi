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
 * on 2015-03-01 at 17:51:50 UTC 
 * Modify at your own risk.
 */

package singularity.com.jlpsi.entities.packagexendpoint.model;

/**
 * Model definition for PackageX.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the packagexendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PackageX extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String code;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String condition;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer qty;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String receivedBy;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String schoolId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long time;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCode() {
    return code;
  }

  /**
   * @param code code or {@code null} for none
   */
  public PackageX setCode(java.lang.String code) {
    this.code = code;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCondition() {
    return condition;
  }

  /**
   * @param condition condition or {@code null} for none
   */
  public PackageX setCondition(java.lang.String condition) {
    this.condition = condition;
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
  public PackageX setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getQty() {
    return qty;
  }

  /**
   * @param qty qty or {@code null} for none
   */
  public PackageX setQty(java.lang.Integer qty) {
    this.qty = qty;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getReceivedBy() {
    return receivedBy;
  }

  /**
   * @param receivedBy receivedBy or {@code null} for none
   */
  public PackageX setReceivedBy(java.lang.String receivedBy) {
    this.receivedBy = receivedBy;
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
  public PackageX setSchoolId(java.lang.String schoolId) {
    this.schoolId = schoolId;
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
  public PackageX setTime(java.lang.Long time) {
    this.time = time;
    return this;
  }

  @Override
  public PackageX set(String fieldName, Object value) {
    return (PackageX) super.set(fieldName, value);
  }

  @Override
  public PackageX clone() {
    return (PackageX) super.clone();
  }

}
