package crypto.example.view.views;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;

public class StringConversion {
  Directory directoryService = Activator.getDirectoryService();
  private String name;

  public void setString(Person p) {
    p = directoryService.personById(p.getId());
    if (p != null) {
      name = p.getFirstName() + " " + p.getLastName();
    }
  }

  public String getString() {
    return name;
  }
}
