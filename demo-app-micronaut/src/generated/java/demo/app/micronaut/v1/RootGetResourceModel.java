package demo.app.micronaut.v1;

import ch.silviowangler.rest.model.ResourceModel;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    date = "2019-02-11T14:56:02.175Z",
    comments = "Specification filename: root.v1.json",
    value = "ch.silviowangler.restapi"
)
public class RootGetResourceModel implements Serializable, ResourceModel {
  public static final String TYPE = "application/ch.silviowangler.land";

  private String id;

  private String name;

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (! (other instanceof RootGetResourceModel)) return false;
    RootGetResourceModel that = (RootGetResourceModel) other;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}