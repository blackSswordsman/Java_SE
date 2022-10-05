
package library;

public interface ILibrary {
    public int hallsCount();
    public int booksCount(int hallIndex); //кол-во книг в зале 
    public int sumAllBooks(); //книг всего в библиотеке
    public double sumCost();
    public IHall [] hallsArr();
    public IHall getHall (int number);
    public IBook getBook (int indexLib);
    public IBook [] srtBooks();
    public void printHalls();
    public void replaceHall (int number, IHall repHall);
    public void replaceBook(int indexLib,IBook repBook);
    public void addBookToLib (int indexLib, IBook newBook);
    public void deleteFromLib (int indexLib);
    public IBook getBestBook ();
    public ILibrary clone();
}
