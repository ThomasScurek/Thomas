package crypto.example.view.views;

import crypto.api.Person;

public class NodeConversion {
  public TreeNode<Person> convertPersonToTreeNode(Person p) {
    TreeNode<Person> rootNode = new TreeNode<>(p);
    rootNode.addChild("Address: " + p.getAddress());
    rootNode.addChild("Age" + p.getAge());
    rootNode.addChild("First Name: " + p.getFirstName());
    rootNode.addChild("LastName: " + p.getLastName());

    TreeNode<String> friendNode = rootNode.addChild("Friends");
    p.getFriends()
        .forEach(
            friend -> {
              friendNode.addChild(friend.getFirstName() + " " + friend.getLastName());
            });
    return rootNode;
  }
}
