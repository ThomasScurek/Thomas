package crypto.example.view.views;

import crypto.api.Person;

public class PersonReference {

  private final long id;

  public PersonReference(Person p) {
    id = p.getId();
  }

  public long getId() {
    return id;
  }

}
