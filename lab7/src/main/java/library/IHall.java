package library;

public interface IHall {
    
    public String getHallName ();
    public void setHallName (String hallName);
    public int getSize ();//размер холла 
    public void setSize(int count);
    public void printNames ();
    public double sumCost ();
    public IBook getBook (int index);
    public IBook [] getArray();
    public void replaceBook(int index,IBook newBook);
    public void addBook (int index, IBook addedBook);
    public void deleteBook (int index);  
    public IBook getBestBook();
    public IHall clone();
}
