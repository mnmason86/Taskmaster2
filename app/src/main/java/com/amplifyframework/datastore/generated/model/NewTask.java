package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the NewTask type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "NewTasks", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class NewTask implements Model {
  public static final QueryField ID = field("NewTask", "id");
  public static final QueryField NAME = field("NewTask", "name");
  public static final QueryField BODY = field("NewTask", "body");
  public static final QueryField DATE_CREATED = field("NewTask", "dateCreated");
  public static final QueryField STATE = field("NewTask", "state");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime dateCreated;
  private final @ModelField(targetType="TaskStateEnum", isRequired = true) TaskStateEnum state;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getBody() {
      return body;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public TaskStateEnum getState() {
      return state;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private NewTask(String id, String name, String body, Temporal.DateTime dateCreated, TaskStateEnum state) {
    this.id = id;
    this.name = name;
    this.body = body;
    this.dateCreated = dateCreated;
    this.state = state;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      NewTask newTask = (NewTask) obj;
      return ObjectsCompat.equals(getId(), newTask.getId()) &&
              ObjectsCompat.equals(getName(), newTask.getName()) &&
              ObjectsCompat.equals(getBody(), newTask.getBody()) &&
              ObjectsCompat.equals(getDateCreated(), newTask.getDateCreated()) &&
              ObjectsCompat.equals(getState(), newTask.getState()) &&
              ObjectsCompat.equals(getCreatedAt(), newTask.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), newTask.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getBody())
      .append(getDateCreated())
      .append(getState())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("NewTask {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static NewTask justId(String id) {
    return new NewTask(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      body,
      dateCreated,
      state);
  }
  public interface NameStep {
    StateStep name(String name);
  }
  

  public interface StateStep {
    BuildStep state(TaskStateEnum state);
  }
  

  public interface BuildStep {
    NewTask build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep dateCreated(Temporal.DateTime dateCreated);
  }
  

  public static class Builder implements NameStep, StateStep, BuildStep {
    private String id;
    private String name;
    private TaskStateEnum state;
    private String body;
    private Temporal.DateTime dateCreated;
    @Override
     public NewTask build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new NewTask(
          id,
          name,
          body,
          dateCreated,
          state);
    }
    
    @Override
     public StateStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep state(TaskStateEnum state) {
        Objects.requireNonNull(state);
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String body, Temporal.DateTime dateCreated, TaskStateEnum state) {
      super.id(id);
      super.name(name)
        .state(state)
        .body(body)
        .dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder state(TaskStateEnum state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
  }
  
}
