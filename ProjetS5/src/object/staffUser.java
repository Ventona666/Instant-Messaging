package object;

public class staffUser extends User{
    public staffUser(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public void addToGroup(User user, Group group){
        try{
            stubServer.addToGroup(user, group);
            System.err.println(user.toString() + " ajouté à " + group.toString());
        }
        catch (Exception e){
            System.err.println(user.toString() + " n'a pas été ajouté à " + group.toString() + " : " + e);
            e.printStackTrace();
        }
    }

    public void removeFromGroup(User user, Group group){
        try{
            stubServer.removeFromGroup(user, group);
            System.err.println(user.toString() + " supprimé de " + group.toString());
        }
        catch (Exception e){
            System.err.println(user.toString() + " n'a pas été supprimé de " + group.toString() + " : " + e);
            e.printStackTrace();
        }
    }
}
