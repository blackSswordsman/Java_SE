
package library;

public interface IBook {
    public  String getAuthor();
    public void setAuthor (String author);
    public String getName();
    public void setName (String name);
    public double getCost();
    public void setCost (double cost);
    public int getYear();
    public void setYear (int year);
    public IBook clone();
    }

