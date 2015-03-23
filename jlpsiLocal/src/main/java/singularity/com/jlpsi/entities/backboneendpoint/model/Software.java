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
 * on 2015-03-01 at 17:50:56 UTC 
 * Modify at your own risk.
 */

package singularity.com.jlpsi.entities.backboneendpoint.model;

/**
 * Model definition for Software.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the backboneendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Software extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String downloadUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer downloads;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String notes;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDownloadUrl() {
    return downloadUrl;
  }

  /**
   * @param downloadUrl downloadUrl or {@code null} for none
   */
  public Software setDownloadUrl(java.lang.String downloadUrl) {
    this.downloadUrl = downloadUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDownloads() {
    return downloads;
  }

  /**
   * @param downloads downloads or {@code null} for none
   */
  public Software setDownloads(java.lang.Integer downloads) {
    this.downloads = downloads;
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
  public Software setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImageUrl() {
    return imageUrl;
  }

  /**
   * @param imageUrl imageUrl or {@code null} for none
   */
  public Software setImageUrl(java.lang.String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public Software setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNotes() {
    return notes;
  }

  /**
   * @param notes notes or {@code null} for none
   */
  public Software setNotes(java.lang.String notes) {
    this.notes = notes;
    return this;
  }

  @Override
  public Software set(String fieldName, Object value) {
    return (Software) super.set(fieldName, value);
  }

  @Override
  public Software clone() {
    return (Software) super.clone();
  }

}
